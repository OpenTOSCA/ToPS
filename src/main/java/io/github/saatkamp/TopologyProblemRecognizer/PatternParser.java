package io.github.saatkamp.TopologyProblemRecognizer;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeIterator;
import com.vladsch.flexmark.parser.Parser;

public class PatternParser {

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
        document = nodeIterator.next().getNext();
        String problem = document.getFirstChild().getChars().toString();
        // Read FenceCodeBlock of the third heading
        document = nodeIterator.next().getNext();
        String problemRule = document.getNext().getFirstChild().getChars().toString();
        // Read Paragraph of the fourth heading
        document = nodeIterator.next().getNext().getNext();
        String solutionDescription = document.getNext().getChars().toString();

        return new Pattern(patternName, problem, problemRule, solutionDescription);
    }
}
