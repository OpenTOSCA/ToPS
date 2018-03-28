package io.github.saatkamp.TopologyProblemRecognizer;

public class PrologNames {

    private static final String DOT_REPLACEMENT = "a1b2C3";
    private static final String DASH_REPLACEMENT = "d4e5F6";
    private static final String UNDERSCORE_REPLACEMENT = "g7h8I9";

    public static String encode (String name) {
        String encodedName = name;
        String temp = name;
        encodedName = temp.replace(".", DOT_REPLACEMENT);
        temp = encodedName;
        encodedName = temp.replace("-", DASH_REPLACEMENT);
        temp = encodedName;
        encodedName = encodedName.replace("_", UNDERSCORE_REPLACEMENT);

        return encodedName;
    }

    public static String decode (String name) {
        String decodeName = name;
        String temp = name;
        decodeName = temp.replace(DOT_REPLACEMENT, ".");
        temp = decodeName;
        decodeName = temp.replace(DASH_REPLACEMENT, "-");
        temp = decodeName;
        decodeName = temp.replace(UNDERSCORE_REPLACEMENT, "_");

        return decodeName;
    }
}
