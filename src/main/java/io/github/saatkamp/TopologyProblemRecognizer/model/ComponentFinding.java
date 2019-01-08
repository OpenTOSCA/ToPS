package io.github.saatkamp.TopologyProblemRecognizer.model;

public class ComponentFinding {

    private String placeholder;
    private String componentId;

    public ComponentFinding() {

    }

    public ComponentFinding(String placeholder, String componentId) {
        this.placeholder = placeholder;
        this.componentId = componentId;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
}
