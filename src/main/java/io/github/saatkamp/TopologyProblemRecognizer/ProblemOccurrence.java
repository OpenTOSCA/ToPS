package io.github.saatkamp.TopologyProblemRecognizer;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "problemoccurrence")
public class ProblemOccurrence extends ProblemEntity {

    private String serviceTemplateID;
    private Map<String, String> occurrence;

    public ProblemOccurrence(String problem, String patternName, String solutionDescription, String serviceTemplateID, Map<String, String> occurrence) {
        super(problem, patternName, solutionDescription);
        this.serviceTemplateID = serviceTemplateID;
        this.occurrence = occurrence;
    }

    public ProblemOccurrence(String problem, String patternName, String solutionDescription, String serviceTemplateID) {
        super(problem, patternName, solutionDescription);
        this.serviceTemplateID = serviceTemplateID;
        this.occurrence = new HashMap<>();
    }

    @JsonGetter("servicetemplateID")
    public String getServiceTemplateID() {
        return serviceTemplateID;
    }

    @JsonGetter("occurrence")
    public Map<String, String> getOccurrence() {
        return occurrence;
    }
}
