package io.github.saatkamp.TopologyProblemRecognizer.model;

public class ConcreteSolutionImplementation {

    private String algorithm;
    private String serviceEndpoint;

    public ConcreteSolutionImplementation(String algorithm, String serviceEndpoint) {
        this.algorithm = algorithm;
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }
}
