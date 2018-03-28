package io.github.saatkamp.TopologyProblemRecognizer;

import org.jpl7.Query;
import org.jpl7.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemRecognizer {

    private File problemQueries;
    private static final String PATTERN_DIR = "pattern_prologfiles";
    private static final String TOPOLOGY_DIR = "topologies";
    private static Logger logger = LoggerFactory.getLogger(ProblemRecognizer.class);
    private List<Pattern> patternList;

    public ProblemRecognizer() throws IOException {
        this.patternList = PatternFactory.createPatternList(PATTERN_DIR);
        String patternsString = patternList.stream()
                .map(pattern -> pattern.getProblemRule()).collect(Collectors.joining("\n"));
        Files.write(Paths.get(PATTERN_DIR,"patterns.pl"), patternsString.getBytes());

        //init consult prolog filed
        File patternsFolder = new File(PATTERN_DIR);
        File[] listOfPatternFiles = patternsFolder.listFiles();
        for (File pattern : listOfPatternFiles) {
            if(pattern.getName().endsWith(".pl")) {
                String patternLoadQuery = "consult('" + PATTERN_DIR + "/" + pattern.getName() +"')";
                Query queryPatternLoad = new Query(patternLoadQuery);
                logger.info( "File: {} consulting finished with {}", pattern.getName(), queryPatternLoad.hasSolution());
            }
        }
    }

    public List<ProblemOccurrence> checkTopology(String topologyID) throws IOException {
        List<ProblemOccurrence> problemOccurrences = new ArrayList<>();
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
                ProblemOccurrence occurence = new ProblemOccurrence(pattern.getProblem(), pattern.getName(), pattern.getSolutionDescription());
                Map<String, Term>[] ss4 = Query.allSolutions(problemQuery);
                logger.info("all solutions of {}", problemQuery);
                for (int i = 0; i < ss4.length; i++) {
                    Map<String, String> finding = new HashMap<>();
                    for (Map.Entry entry : ss4[i].entrySet()) {
                        finding.put(entry.getKey().toString(), PrologNames.decode(entry.getValue().toString()));
                        logger.info("This is the result for Variable {}: {}", entry.getKey(), PrologNames.decode(entry.getValue().toString()));
                    }
                    occurence.addFinding(finding);
                }
                problemOccurrences.add(occurence);
            } else {
                logger.info("problem {} is not contained in topology", problemQuery);
            }
        }

        return problemOccurrences;
    }


}
