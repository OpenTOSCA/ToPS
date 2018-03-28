package io.github.saatkamp.TopologyProblemRecognizer;

import java.util.HashMap;
import java.util.Map;

public class PrologNames {

    private static final String DOT_REPLACEMENT = "a1b2C3";
    private static final String DASH_REPLACEMENT = "d4e5F6";
    private static final String UNDERSCORE_REPLACEMENT = "g7h8I9";
    private Map<String, String> nameMatchingMap = new HashMap<>();

    public String encode (String name) {
        String encodedName = name;
        String temp = name;
        encodedName = temp.replace(".", DOT_REPLACEMENT);
        temp = encodedName;
        encodedName = temp.replace("-", DASH_REPLACEMENT);
        temp = encodedName;
        encodedName = encodedName.replace("_", UNDERSCORE_REPLACEMENT);
        encodedName = encodedName.toLowerCase();
        nameMatchingMap.put(encodedName, name);

        return encodedName;
    }

    public String decode (String name) {
        return nameMatchingMap.get(name);
    }
}
