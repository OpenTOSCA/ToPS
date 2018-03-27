package io.github.saatkamp.TopologyProblemRecognizer;

import org.jpl7.Query;
import org.jpl7.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ProblemRecognizer {

    private File problemQueries;
    private static Logger logger = LoggerFactory.getLogger(ProblemRecognizer.class);

    public ProblemRecognizer() {
        File patternsFolder = new File("pattern_prologfiles");
        File[] listOfPatternFiles = patternsFolder.listFiles();
        for (File pattern : listOfPatternFiles) {
            if(pattern.getName().endsWith(".pl")) {
                String patternLoadQuery = "consult('pattern_prologfiles/" + pattern.getName() +"')";
                Query queryPatternLoad = new Query(patternLoadQuery);
                logger.info( "File: {} consulting finished with {}", pattern.getName(), queryPatternLoad.hasSolution());
            }
            if (pattern.getName().endsWith(".txt")) {
                problemQueries = pattern;
            }
        }
    }

    public void checkTopology(String topologyID) throws IOException {
        File topology = new File("topologies/" + topologyID + ".pl");
        if (topology.exists()) {
            String topologyLoadQuery = "consult('topologies/" + topology.getName() +"')";
            Query queryTopologyLoad = new Query(topologyLoadQuery);
            logger.info( "File: {} consulting finished with {}",topology.getName(), (queryTopologyLoad.hasSolution()));
        } else {
            throw new IOException("Topology is not available");
        }

        if (problemQueries != null) {
            BufferedReader reader = new BufferedReader(new FileReader(problemQueries));
            String problemQuery = reader.readLine();

            while(problemQuery != null) {
                if(Query.hasSolution(problemQuery)) {
                    Map<String, Term>[] ss4 = Query.allSolutions(problemQuery);
                    logger.info("all solutions of {}", problemQuery);
                    for (int i = 0; i < ss4.length; i++) {
                        logger.info("X = {}" + ss4[i].get("X"));
                        logger.info("Y = {}" + ss4[i].get("Y"));
                    }
                } else {
                   logger.info("problem {} is not contained in topology", problemQuery);
                }
                problemQuery = reader.readLine();
            }

        }

    }


}
