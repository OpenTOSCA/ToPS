package io.github.saatkamp.TopologyProblemRecognizer;

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
        //ONLY FOR TESTING START
        Map<String, String> replacementList = new HashMap<>();
        replacementList.put("Component_2", "test2");
        replacementList.put("Component_1", "test");
        for (Solution solution: solutionList) {
            String query = solution.getContextSpecificQuery(replacementList);
            logger.info("Das ist die Query für" + solution.getName() + ": " + query);
        }

    }

    public void initializePrologFiles(String directoryName) {
        File directory = new File(directoryName);
        File[] listOfPrologFiles = directory.listFiles();
        for (File prologFile : listOfPrologFiles) {
            if(prologFile.getName().endsWith(".pl")) {
                String initQueryExpr = "consult('" + directoryName + "/" + prologFile.getName() +"')";
                Query query = new Query(initQueryExpr);
                logger.info( "File: {} consulting finished with {}", prologFile.getName(), query.hasSolution());
            }
        }
    }

    public List<ProblemFindings> checkTopology(String topologyID) throws IOException {

        //write prolog file containing all patterns
        String patternsString = patternList.stream()
                .map(pattern -> pattern.getProblemRule()).collect(Collectors.joining("\n"));
        Files.write(Paths.get(PATTERN_DIR,"patterns.pl"), patternsString.getBytes());

        //init consult prolog files
        initializePrologFiles(PATTERN_DIR);

        List<ProblemFindings> problemFindings = new ArrayList<>();
        File topology = new File(TOPOLOGY_DIR + "/" + topologyID + ".pl");
        if (topology.exists()) {
            String topologyLoadQuery = "consult('topologies/" + topology.getName() +"')";
            Query queryTopologyLoad = new Query(topologyLoadQuery);
            logger.info( "File: {} consulting finished with {}",topology.getName(), (queryTopologyLoad.hasSolution()));
        } else {
            throw new IOException("Topology is not available");
        }

        for(Pattern pattern : patternList) {
            String problemQuery = pattern.getQuery();
            if(Query.hasSolution(problemQuery)) {
                ProblemFindings findings = new ProblemFindings(pattern.getProblem(), pattern.getName(), pattern.getSolutionDescription());
                Map<String, Term>[] ss4 = Query.allSolutions(problemQuery);
                logger.info("all solutions of {}", problemQuery);
                for (int i = 0; i < ss4.length; i++) {
                    Map<String, String> finding = new HashMap<>();
                    for (Map.Entry entry : ss4[i].entrySet()) {
                        finding.put(entry.getKey().toString(), prologNames.decode(entry.getValue().toString()));
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
        String topologyLoadQuery = "unload_file('topologies/" + topology.getName() +"')";
        Query queryTopologyLoad = new Query(topologyLoadQuery);
        logger.info( "File: {} unloading finished with {}",topology.getName(), (queryTopologyLoad.hasSolution()));

        return problemFindings;
    }

    public List<Solution> findSolutions (String serviceTemplateID, String problemName, Map<String, String> occurrence) throws IOException {

        //write prolog file containing all solutions for this problem
        String solutionsString = solutionList.stream()
                .filter(s -> s.getRelatedPatternsList().contains(problemName))
                .map(solution -> solution.getSelectionCriteriaRule()).collect(Collectors.joining("\n"));
        Files.write(Paths.get(SOLUTION_DIR,problemName + "_solutions.pl"), solutionsString.getBytes());

        //init consult prolog files
        initializePrologFiles(SOLUTION_DIR);

        List<Solution> solutions = new ArrayList<>();

        //TODO: load topology and check for solutions

        return solutions;
    }


}
