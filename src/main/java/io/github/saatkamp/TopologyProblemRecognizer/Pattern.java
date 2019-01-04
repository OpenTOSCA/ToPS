package io.github.saatkamp.TopologyProblemRecognizer;

import java.util.List;
import java.util.Objects;

public class Pattern {

    private String name;
    private String problem;
    private String problemRule;
    private String solutionDescription;

    public Pattern(String name, String problem, String problemRule, String solutionDescription) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(problem);
        Objects.requireNonNull(problemRule);
        Objects.requireNonNull(solutionDescription);
        this.name = name;
        this.problem = problem;
        this.problemRule = problemRule;
        this.solutionDescription = solutionDescription;
    }

    public String getName() {
        return name;
    }

    public String getProblem() {
        return problem;
    }

    public String getProblemRule() {
        return problemRule;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public String getQuery() {
        String query;
        int index = this.problemRule.indexOf(" :-");
        query = this.problemRule.substring(0, index) + ".";
        return query;
    }
}
