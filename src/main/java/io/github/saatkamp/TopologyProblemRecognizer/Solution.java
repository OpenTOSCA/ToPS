package io.github.saatkamp.TopologyProblemRecognizer;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Solution {

    private String name;
    private String description;
    private List<String> relatedPatternsList;
    private String selectionCriteriaRule;
    private ConcreteSolutionImplementation csi;

    public Solution(String name, String description, List<String> relatedPatternsList, String selectionCriteriaRule, ConcreteSolutionImplementation csi) {
        this.name = name;
        this.relatedPatternsList = relatedPatternsList;
        this.description = description;
        this.selectionCriteriaRule = selectionCriteriaRule;
        this.csi = csi;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRelatedPatternsList() {
        return relatedPatternsList;
    }

    public String getSelectionCriteriaRule() {
        return selectionCriteriaRule;
    }

    public ConcreteSolutionImplementation getConcreteSolutionImplementation() {
        return csi;
    }

    public String getContextSpecificQuery(Map<String, String> replacementList) {
        String query;
        int index = this.selectionCriteriaRule.indexOf(" :-");
        query = this.selectionCriteriaRule.substring(0, index) + ".";

        for (Map.Entry<String, String> entry : replacementList.entrySet()) {
           query = query.replaceAll(entry.getKey(), entry.getValue());
        }

        return query;
    }
}
