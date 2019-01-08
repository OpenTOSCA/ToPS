package io.github.saatkamp.TopologyProblemRecognizer;

import java.util.ArrayList;
import java.util.List;

import io.github.saatkamp.TopologyProblemRecognizer.model.Pattern;
import io.github.saatkamp.TopologyProblemRecognizer.model.Solution;
import org.junit.Assert;
import org.junit.Test;

public class PatternTest {

    private Solution solution = new Solution("solution1", "This is a sample solution", "rule", "link");
    private List<Solution> solutions = new ArrayList<>();
    private Pattern pattern = new Pattern("test", "test not running", "test(Component_1, Component_2) :- test.", "DO a better test", solutions);

    @Test
    public void getQuery() throws Exception {
        String query = pattern.getQuery();
        Assert.assertEquals("test(Component_1, Component_2).", query);

    }

}
