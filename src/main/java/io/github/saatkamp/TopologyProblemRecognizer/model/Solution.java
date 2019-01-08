package io.github.saatkamp.TopologyProblemRecognizer.model;

import java.util.List;
import java.util.Map;

public class Solution extends SolutionEntity{

    private List<String> relatedPatternsList;
    private String selectionCriteriaRule;

    public Solution(String name, String description, List<String> relatedPatternsList, String selectionCriteriaRule, ConcreteSolutionImplementation csi) {
        super(name, description, csi);
        this.relatedPatternsList = relatedPatternsList;
        this.selectionCriteriaRule = selectionCriteriaRule;
    }

    public List<String> getRelatedPatternsList() {
        return relatedPatternsList;
    }

    public String getSelectionCriteriaRule() {
        return selectionCriteriaRule;
    }

    public String getContextSpecificQuery(List<ComponentFinding> replacementList) {
        String query;
        int index = this.selectionCriteriaRule.indexOf(" :-");
        query = this.selectionCriteriaRule.substring(0, index) + ".";

        for (ComponentFinding entry : replacementList) {
           query = query.replaceAll(entry.getPlaceholder(), entry.getComponentId());
        }

        return query;
    }
}
