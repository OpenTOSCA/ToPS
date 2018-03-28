package io.github.saatkamp.TopologyProblemRecognizer;

import org.junit.Test;
import org.springframework.util.Assert;

public class ProblemRecognizerTest {
    @Test
    public void checkConstructor() throws Exception {
        ProblemRecognizer problemRecognizer = new ProblemRecognizer();
        Assert.notNull(problemRecognizer);
    }

    @Test
    public void checkTopology() throws Exception {
        ProblemRecognizer problemRecognizer = new ProblemRecognizer();
        problemRecognizer.checkTopology("topology");
        Assert.notNull(problemRecognizer);
    }

}
