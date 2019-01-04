package io.github.saatkamp.TopologyProblemRecognizer;

import java.util.ArrayList;
import java.util.List;

import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeIterator;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.sequence.BasedSequence;
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
        // Read FenceCodeBlock of the third heading
        document = document.getNext().getNext();
        String problemRule = document.getFirstChild().getChars().toString();
        // Read Paragraph of the fourth heading
        document = document.getNext().getNext();
        String solutionDescription = document.getChars().toString();

        return new Pattern(patternName, problem, problemRule, solutionDescription);
    }
}
