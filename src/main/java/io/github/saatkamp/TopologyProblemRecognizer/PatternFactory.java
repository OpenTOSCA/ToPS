package io.github.saatkamp.TopologyProblemRecognizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternFactory {

    private static Logger logger = LoggerFactory.getLogger(ProblemRecognizer.class);

    public static List<Pattern> createPatternList(String directoryPath) {
        PatternParser patternParser = new PatternParser();
        List<Pattern> patternList = new ArrayList<>();
        File patternsFolder = new File(directoryPath);
        if(patternsFolder.isDirectory()) {
            File[] listOfPatternFiles = patternsFolder.listFiles();
            for (File patternMD : listOfPatternFiles) {
                if(patternMD.getName().endsWith(".md")) {

                    try {
                        Pattern pattern = patternParser.parsePatternMD(readFile(patternMD));
                        patternList.add(pattern);
                    } catch (IOException e) {
                        logger.error("Pattern cannot be parsed >{}<", patternMD.getName());
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        return patternList;
    }

    private static String readFile(File file) throws IOException {
        FileInputStream fstream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fstream.read(bytes);
        fstream.close();
        return new String(bytes);
    }
}
