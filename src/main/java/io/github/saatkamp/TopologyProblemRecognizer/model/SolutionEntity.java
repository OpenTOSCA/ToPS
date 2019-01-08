package io.github.saatkamp.TopologyProblemRecognizer.model;

public abstract class SolutionEntity {

    private String name;
    private String description;
    private ConcreteSolutionImplementation csi;

    public SolutionEntity() {

    }

    public SolutionEntity(String name, String description, ConcreteSolutionImplementation csi) {
        this.name = name;
        this.description = description;
        this.csi = csi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ConcreteSolutionImplementation getCsi() {
        return csi;
    }

    public void setCsi(ConcreteSolutionImplementation csi) {
        this.csi = csi;
    }
}
