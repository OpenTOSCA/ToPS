package io.github.saatkamp.TopologyProblemRecognizer;

import io.github.saatkamp.TopologyProblemRecognizer.model.ComponentFinding;
import io.github.saatkamp.TopologyProblemRecognizer.model.Pattern;
import io.github.saatkamp.TopologyProblemRecognizer.model.PatternFactory;
import io.github.saatkamp.TopologyProblemRecognizer.model.ProblemFindings;
import io.github.saatkamp.TopologyProblemRecognizer.model.Solution;
import io.github.saatkamp.TopologyProblemRecognizer.model.SolutionInputData;
import org.jpl7.Query;
import org.jpl7.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PrologChecker {

    private static final String PATTERN_DIR = "pattern_prologfiles";
    private static final String SOLUTION_DIR = "solution_prologfiles";
    private static final String TOPOLOGY_DIR = "topologies";
    private static Logger logger = LoggerFactory.getLogger(PrologChecker.class);
    private List<Pattern> patternList;
    private List<Solution> solutionList;
    private PrologNames prologNames;

    public PrologChecker(PrologNames prologNames) throws IOException {
        this.prologNames = Objects.requireNonNull(prologNames);
        this.patternList = PatternFactory.createPatternList(PATTERN_DIR);
        this.solutionList = PatternFactory.createSolutionList(SOLUTION_DIR);
    }

    public void initializePrologFiles(String directoryName) {
        File directory = new File(directoryName);
        File[] listOfPrologFiles = directory.listFiles();
        for (File prologFile : listOfPrologFiles) {
            if (prologFile.getName().endsWith(".pl")) {
                String initQueryExpr = "consult('" + directoryName + "/" + prologFile.getName() + "')";
                Query query = new Query(initQueryExpr);
                logger.info("File: {} consulting finished with {}", prologFile.getName(), query.hasSolution());
            }
        }
    }

    public void loadServiceTemplateFacts(String serviceTemplateId) throws IOException {
        File topology = new File(TOPOLOGY_DIR + "/" + serviceTemplateId + ".pl");
        if (topology.exists()) {
            String topologyLoadQuery = "consult('topologies/" + topology.getName() + "')";
            Query queryTopologyLoad = new Query(topologyLoadQuery);
            logger.info("File: {} consulting finished with {}", topology.getName(), (queryTopologyLoad.hasSolution()));
        } else {
            throw new IOException("Topology is not available");
        }
    }

    public List<ProblemFindings> checkTopology(String topologyID) throws IOException {
        //write prolog file containing all patterns
        String patternsString = patternList.stream()
                .map(pattern -> pattern.getProblemRule()).collect(Collectors.joining("\n"));
        Files.write(Paths.get(PATTERN_DIR, "patterns.pl"), patternsString.getBytes());
        //init consult prolog files
        initializePrologFiles(PATTERN_DIR);

        List<ProblemFindings> problemFindings = new ArrayList<>();
        loadServiceTemplateFacts(topologyID);

        for (Pattern pattern : patternList) {
            String problemQuery = pattern.getQuery();
            if (Query.hasSolution(problemQuery)) {
                ProblemFindings findings = new ProblemFindings(pattern.getProblem(), pattern.getName(), pattern.getSolutionDescription());
                Map<String, Term>[] ss4 = Query.allSolutions(problemQuery);
                logger.info("all solutions of {}", problemQuery);
                for (int i = 0; i < ss4.length; i++) {
                    List<ComponentFinding> finding = new ArrayList<>();
                    for (Map.Entry entry : ss4[i].entrySet()) {
                        ComponentFinding componentFinding = new ComponentFinding(entry.getKey().toString(), prologNames.decode(entry.getValue().toString()));
                        finding.add(componentFinding);
                        logger.info("This is the result for Variable {}: {}", entry.getKey(), prologNames.decode(entry.getValue().toString()));
                    }
                    findings.addFinding(finding);
                }
                problemFindings.add(findings);
            } else {
                logger.info("problem {} is not contained in topology", problemQuery);
            }
        }
        //remove the topology from the Prolog database - otherwise all contained topologies are checked but only the current topology should be checked
        String topologyLoadQuery = "unload_file('topologies/" + topologyID + "')";
        Query queryTopologyLoad = new Query(topologyLoadQuery);
        logger.info("File: {} unloading finished with {}", topologyID, (queryTopologyLoad.hasSolution()));

        return problemFindings;
    }

    /**
     * Returns a list of SolutionInputData for a given Service Template and detected Problem between a set of Components
     * @param serviceTemplateID
     * @param patternName
     * @param occurrence
     * @return
     * @throws IOException
     */

    public List<SolutionInputData> findSolutions(String serviceTemplateID, String patternName, List<ComponentFinding> occurrence) throws IOException {

        List<Solution> relatedSolutions = solutionList.stream().filter(s -> s.getRelatedPatternsList().contains(patternName)).collect(Collectors.toList());
        //write prolog file containing all solutions for this problem
        String solutionsString = relatedSolutions.stream().map(solution -> solution.getSelectionCriteriaRule()).collect(Collectors.joining("\n"));
        Files.write(Paths.get(SOLUTION_DIR, patternName + "_solutions.pl"), solutionsString.getBytes());

        //init consult prolog files
        initializePrologFiles(SOLUTION_DIR);
        loadServiceTemplateFacts(serviceTemplateID);

        List<SolutionInputData> solutions = new ArrayList<>();

        for (Solution solution : relatedSolutions) {
            occurrence.stream().forEach(finding -> {
                String encodedComponentId = prologNames.encode(finding.getComponentId());
                finding.setComponentId(encodedComponentId);
            });
            String solutionQuery = solution.getContextSpecificQuery(occurrence);
            if (Query.hasSolution(solutionQuery)) {
                SolutionInputData solutionInputData = new SolutionInputData();
                solutionInputData.setName(solution.getName());
                solutionInputData.setDescription(solution.getDescription());
                solutionInputData.setCsi(solution.getCsi());
                occurrence.stream().forEach(finding -> {
                    String encodedComponentId = prologNames.decode(finding.getComponentId());
                    finding.setComponentId(encodedComponentId);
                });
                solutionInputData.setFindings(occurrence);

                Map<String, Term>[] ss4 = Query.allSolutions(solutionQuery);
                logger.info("all solutions of {}", solutionQuery);

                if(!ss4[0].isEmpty()) {
                    Map<String, String> algorithmSpecificInformation = new HashMap<>();
                    for (Map.Entry entry : ss4[0].entrySet()) {
                        algorithmSpecificInformation.put(entry.getKey().toString(), prologNames.decode(entry.getValue().toString()));
                        logger.info("This is the result for Variable {}: {}", entry.getKey(), prologNames.decode(entry.getValue().toString()));
                    }
                    solutionInputData.setAlgorithmSpecificInformation(algorithmSpecificInformation);
                }
                solutions.add(solutionInputData);

            } else {
                logger.info("problem {} is not contained in topology", solutionQuery);
            }
        }

        return solutions;
    }
}
