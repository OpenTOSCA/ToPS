package io.github.saatkamp.TopologyProblemRecognizer.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public abstract class ProblemEntity {

    private String problem;
    private String patternName;
    private String solutionDescription;

    public ProblemEntity(){

    }

    public ProblemEntity(String problem, String patternName, String solutionDescription) {
        this.problem = problem;
        this.patternName = patternName;
        this.solutionDescription = solutionDescription;
    }

    @JsonGetter("problem")
    public String getProblem() {
        return this.problem;
    }

    @JsonGetter("pattern")
    public String getPatternName() {
        return this.patternName;
    }

    @JsonGetter("description")
    public String getSolutionDescription() {
        return this.solutionDescription;
    }
}
