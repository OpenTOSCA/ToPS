package io.github.saatkamp.TopologyProblemRecognizer;

import org.junit.Test;
import org.springframework.util.Assert;

public class ProblemRecognizerTest {
    @Test
    public void checkConstructor() throws Exception {
        PrologNames prologNames = new PrologNames();
        ProblemRecognizer problemRecognizer = new ProblemRecognizer(prologNames);
        Assert.notNull(problemRecognizer);
    }

    @Test
    public void checkTopology() throws Exception {
        PrologNames prologNames = new PrologNames();
        ProblemRecognizer problemRecognizer = new ProblemRecognizer(prologNames);
        problemRecognizer.checkTopology("topology");
        Assert.notNull(problemRecognizer);
    }

}
