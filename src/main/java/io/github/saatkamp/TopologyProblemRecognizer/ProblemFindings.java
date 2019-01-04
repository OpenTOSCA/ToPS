package io.github.saatkamp.TopologyProblemRecognizer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.beans.factory.parsing.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonRootName(value = "problemfindings")
public class ProblemFindings extends ProblemEntity {

    private List<Map<String, String>> findings;

    public ProblemFindings(String problem, String patternName, String solutionDescription) {
        super(problem, patternName, solutionDescription);
        findings = new ArrayList<>();
    }

    @JsonGetter("findings")
    public List<Map<String, String>> getFindings() {
        return findings;
    }

    public void addFinding(Map<String, String> finding) {
        this.findings.add(finding);
    }
}
