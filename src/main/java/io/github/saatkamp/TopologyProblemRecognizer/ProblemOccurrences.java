package io.github.saatkamp.TopologyProblemRecognizer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ProblemOccurrences {

    @JsonProperty
    List<ProblemOccurrence> problemOccurrences = new ArrayList<>();

    public ProblemOccurrences(List<ProblemOccurrence> problemOccurrences) {
        this.problemOccurrences = problemOccurrences;
    }

    public List<ProblemOccurrence> getProblemOccurrences() {
        return problemOccurrences;
    }
}
