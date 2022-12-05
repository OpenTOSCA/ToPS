package io.github.saatkamp.TopologyProblemRecognizer.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentFinding that = (ComponentFinding) o;
        return placeholder.equals(that.placeholder) && componentId.equals(that.componentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeholder, componentId);
    }
}
