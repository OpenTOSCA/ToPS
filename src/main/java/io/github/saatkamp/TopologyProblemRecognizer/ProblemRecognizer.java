package io.github.saatkamp.TopologyProblemRecognizer;

import org.jpl7.Query;
import org.jpl7.Term;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ProblemRecognizer {

    private File problemQueries;

    public ProblemRecognizer() {
        File patternsFolder = new File("pattern_prologfiles");
        File[] listOfPatternFiles = patternsFolder.listFiles();
        for (File pattern : listOfPatternFiles) {
            if(pattern.getName().endsWith(".pl")) {
                System.out.println("File " + pattern.getName());
                String patternLoadQuery = "consult('pattern_prologfiles/" + pattern.getName() +"')";
                Query queryPatternLoad = new Query(patternLoadQuery);
                System.out.println( "consult " + (queryPatternLoad.hasSolution()));
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
            System.out.println( "consult " + (queryTopologyLoad.hasSolution()));
        } else {
            throw new IOException("Topology is not available");
        }

        if (problemQueries != null) {
            BufferedReader reader = new BufferedReader(new FileReader(problemQueries));
            String problemQuery = reader.readLine();

            while(problemQuery != null) {
                Map<String, Term>[] ss4 = Query.allSolutions(problemQuery);
                System.out.println("all solutions of " + problemQuery);
                for (int i = 0; i < ss4.length; i++) {
                    System.out.println("X = " + ss4[i].get("X"));
                    System.out.println("X = " + ss4[i].get("Y"));
                }
                problemQuery = reader.readLine();
            }

        }

    }


}
