package io.github.saatkamp.TopologyProblemRecognizer;

import org.eclipse.winery.repository.client.IWineryRepositoryClient;
import org.eclipse.winery.repository.client.WineryRepositoryClientFactory;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class WebControllerTest {

    @Test
    public void doSomething() {
        IWineryRepositoryClient repositoryClient = WineryRepositoryClientFactory.getWineryRepositoryClient();
        repositoryClient.setPrimaryRepository("http://localhost:8080/winery");
        //String patterns = "consult('C:\\\\Users\\\\saatkake\\\\Documents\\\\Veröffentlichungen\\\\2018_SummerSOC\\\\prolog_files\\\\first_try.pl')";
        String patterns = "consult('pattern_prologfiles/first_try.pl')";
        Query q1 = new Query(patterns);
        System.out.println( "consult " + (q1.hasSolution()));
        //String topology = "consult('C:\\\\Users\\\\saatkake\\\\Documents\\\\Veröffentlichungen\\\\2018_SummerSOC\\\\prolog_files\\\\topology.pl')";
        String topology = "consult('topologies/Problem_Recognition_Example_Unencrypted_Communication.pl')";
        Query q2 = new Query(topology);
        System.out.println( "consult " + (q2.hasSolution()));
        Variable X = new Variable();
        Variable Y = new Variable();
        String query = "unencrypted_public_communication(X, Y)";
        System.out.println( "query open " + Query.oneSolution(query).get("X"));
        Map<String, Term>[] ss4 = Query.allSolutions(query);
        System.out.println("all solutions of " + query);
        for (int i = 0; i < ss4.length; i++) {
            System.out.println("X = " + ss4[i].get("X"));
        }

    }

    @Test
    public void getFiles(){
        File folder = new File("topologies");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}
