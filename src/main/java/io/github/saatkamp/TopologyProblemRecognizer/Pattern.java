package io.github.saatkamp.TopologyProblemRecognizer;

public class Pattern {

    private String name;
    private String problem;
    private String problemRule;
    private String solutionDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblemRule() {
        return problemRule;
    }

    public void setProblemRule(String problemRule) {
        this.problemRule = problemRule;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }
}
