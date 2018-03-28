package io.github.saatkamp.TopologyProblemRecognizer;

import org.junit.Assert;
import org.junit.Test;

public class PatternTest {

    private Pattern pattern = new Pattern("test", "test not running", "test(Component_1, Component_2) :- test.", "DO a better test");

    @Test
    public void getQuery() throws Exception {
        String query = pattern.getQuery();
        Assert.assertEquals("test(Component_1, Component_2).", query);

    }

}
