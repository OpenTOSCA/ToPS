package io.github.saatkamp.TopologyProblemRecognizer.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionInputData extends SolutionEntity {

    private List<ComponentFinding> findings;
    private Map<String, String> algorithmSpecificInformation = new HashMap<>();

    public SolutionInputData() {
    }

    public SolutionInputData(String name, String description, ConcreteSolutionImplementation csi, List<ComponentFinding> findings, Map<String, String> algorithmSpecificInformation) {
        super(name, description, csi);
        this.findings = findings;
        this.algorithmSpecificInformation = algorithmSpecificInformation;
    }

    public List<ComponentFinding> getFindings() {
        return findings;
    }

    public void setFindings(List<ComponentFinding> findings) {
        this.findings = findings;
    }

    public Map<String, String> getAlgorithmSpecificInformation() {
        return algorithmSpecificInformation;
    }

    public void setAlgorithmSpecificInformation(Map<String, String> algorithmSpecificInformation) {
        this.algorithmSpecificInformation = algorithmSpecificInformation;
    }

    public void addAlgorithmSpecificInformation(String key, String value) {
        this.algorithmSpecificInformation.put(key, value);
    }

}
