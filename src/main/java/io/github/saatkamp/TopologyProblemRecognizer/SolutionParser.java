package io.github.saatkamp.TopologyProblemRecognizer;

import java.util.ArrayList;
import java.util.List;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeIterator;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import io.github.saatkamp.TopologyProblemRecognizer.model.ConcreteSolutionImplementation;
import io.github.saatkamp.TopologyProblemRecognizer.model.Solution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionParser {

    private static Logger logger = LoggerFactory.getLogger(SolutionParser.class);
    Parser parser;

    public SolutionParser() {
        this.parser = Parser.builder().build();
    }

    public Solution parseSolutionMD (String solutionMD) {
        Node document = this.parser.parse(solutionMD);

        NodeIterator nodeIterator = new NodeIterator(document.getFirstChild());
        // Read Heading text
        document = nodeIterator.next();
        String solutionName = document.getFirstChild().getChars().toString();
        // Read Paragraph text of the second heading
        document = document.getNext().getNext();
        String description = document.getChars().toString();
        // Read bulletList of the third heading
        document = document.getNext().getNext();
        List<String> relatedPatternList = new ArrayList<>();
        ReversiblePeekingIterable<Node> relatedPatternIterator = document.getChildren();
        relatedPatternIterator.forEach(s -> {
            Link pattern = (Link) s.getFirstChild().getFirstChild();
            String patternName = pattern.getText().toString();
            relatedPatternList.add(patternName);
        });

        // Read FenceCodeBlock of the third heading
        document = document.getNext().getNext();
        String selectionCriteria = document.getFirstChild().getChars().toString();
        //Read Paragrpah of the fifth heading
        document = document.getNext().getNext();
        Link mdLink = (Link) document.getFirstChild();
        ConcreteSolutionImplementation csi = new ConcreteSolutionImplementation(mdLink.getText().toString(), mdLink.getUrl().toString());

        return new Solution(solutionName, description, relatedPatternList, selectionCriteria, csi);
    }
}
