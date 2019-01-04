package io.github.saatkamp.TopologyProblemRecognizer;

import org.junit.Test;
import org.springframework.util.Assert;

public class PrologCheckerTest {
    @Test
    public void checkConstructor() throws Exception {
        PrologNames prologNames = new PrologNames();
        PrologChecker prologChecker = new PrologChecker(prologNames);
        Assert.notNull(prologChecker);
    }

    @Test
    public void checkTopology() throws Exception {
        PrologNames prologNames = new PrologNames();
        PrologChecker prologChecker = new PrologChecker(prologNames);
        prologChecker.checkTopology("topology");
        Assert.notNull(prologChecker);
    }

}
