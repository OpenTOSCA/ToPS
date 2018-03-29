package io.github.saatkamp.TopologyProblemRecognizer;

import org.junit.Test;

import java.io.File;

public class WebControllerTest {


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
