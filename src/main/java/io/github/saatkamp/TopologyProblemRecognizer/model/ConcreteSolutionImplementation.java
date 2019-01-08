package io.github.saatkamp.TopologyProblemRecognizer.model;

public class ConcreteSolutionImplementation {

    private String name;
    private String serviceEndpoint;

    public ConcreteSolutionImplementation(String name, String serviceEndpoint) {
        this.name = name;
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getName() {
        return name;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }
}
