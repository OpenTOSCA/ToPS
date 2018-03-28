package io.github.saatkamp.TopologyProblemRecognizer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonRootName(value = "problemoccurence")
public class ProblemOccurrence {

    private String problem;
    private String patternName;
    private String solutionDescription;
    private List<Map<String, String>> findings;

    public ProblemOccurrence(String problem, String patternName, String solutionDescription) {
        this.problem = problem;
        this.patternName = patternName;
        this.solutionDescription = solutionDescription;
        findings = new ArrayList<>();
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

    @JsonGetter("findings")
    public List<Map<String, String>> getFindings() {
        return findings;
    }

    public void addFinding(Map<String, String> finding) {
        this.findings.add(finding);
    }
}
