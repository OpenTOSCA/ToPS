package io.github.saatkamp.TopologyProblemRecognizer.model;

import java.util.ArrayList;
import java.util.List;

public class ProblemOccurrence extends ProblemEntity {

    private String serviceTemplateNs;
    private String serviceTemplateId;
    private List<ComponentFinding> occurrence;

    public ProblemOccurrence() {

    }

    public ProblemOccurrence(String problem, String patternName, String solutionDescription, String serviceTemplateNs, String serviceTemplateId, List<ComponentFinding> occurrence) {
        super(problem, patternName, solutionDescription);
        this.serviceTemplateNs = serviceTemplateNs;
        this.serviceTemplateId = serviceTemplateId;
        this.occurrence = occurrence;
    }

    public String getServiceTemplateId() {
        return serviceTemplateId;
    }

    public List<ComponentFinding> getOccurrence() {
        return occurrence;
    }

    public String getServiceTemplateNs() {
        return serviceTemplateNs;
    }

    public void setServiceTemplateNs(String serviceTemplateNs) {
        this.serviceTemplateNs = serviceTemplateNs;
    }

    public void setServiceTemplateId(String serviceTemplateId) {
        this.serviceTemplateId = serviceTemplateId;
    }

    public void setOccurrence(List<ComponentFinding> occurrence) {
        this.occurrence = occurrence;
    }
}
