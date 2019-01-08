package io.github.saatkamp.TopologyProblemRecognizer.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Finishings;

@JsonRootName(value = "problemfindings")
public class ProblemFindings extends ProblemEntity {

    private List<List<ComponentFinding>> findings;

    public ProblemFindings(String problem, String patternName, String solutionDescription) {
        super(problem, patternName, solutionDescription);
        findings = new ArrayList<>();
    }

    @JsonGetter("findings")
    public List<List<ComponentFinding>> getFindings() {
        return findings;
    }

    public void addFinding(List<ComponentFinding> finding) {
        this.findings.add(finding);
    }
}
