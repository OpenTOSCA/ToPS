package io.github.saatkamp.TopologyProblemRecognizer.model;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeIterator;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatternParser {

    private static Logger logger = LoggerFactory.getLogger(PatternParser.class);
    Parser parser;

    public PatternParser() {
        this.parser = Parser.builder().build();
    }

    public Pattern parsePatternMD (String patternMD) {
        Node document = this.parser.parse(patternMD);

        NodeIterator nodeIterator = new NodeIterator(document.getFirstChild());
        // Read Heading text
        document = nodeIterator.next();
        String patternName = document.getFirstChild().getChars().toString();
        // Read Paragraph text of the second heading
        document = document.getNext().getNext();
        String problem = document.getFirstChild().getChars().toString();
        boolean iterator = true;
        while(iterator) {
            if(document.getNext().getFirstChild() != null && !document.getNext().getFirstChild().getChars().toString().contains("Problem Rule")) {
                problem = problem + " " + document.getNext().getFirstChild().getChars().toString();
                document = document.getNext();
            } else {
                iterator = false;
            }

        }
        // Read FenceCodeBlock of the third heading
        document = document.getNext().getNext();
        String problemRule = document.getFirstChild().getChars().toString();
        // Read Paragraph of the fourth heading
        document = document.getNext().getNext();
        String solutionDescription = document.getChars().toString();

        return new Pattern(patternName, problem, problemRule, solutionDescription);
    }
}
