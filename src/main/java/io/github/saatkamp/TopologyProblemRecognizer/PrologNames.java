package io.github.saatkamp.TopologyProblemRecognizer;

public class PrologNames {

    private static final String DOT_REPLACEMENT = "a1b2C3";
    private static final String DASH_REPLACEMENT = "d4e5F6";
    private static final String UNDERSCORE_REPLACEMENT = "g7h8I9";

    public static String encode (String name) {
        String encodedName;
        encodedName = name.replaceAll(".", DOT_REPLACEMENT);
        encodedName = name.replaceAll("-", DASH_REPLACEMENT);
        encodedName = name.replaceAll("_", UNDERSCORE_REPLACEMENT);

        return encodedName;
    }

    public static String decode (String name) {
        String decodeName;
        decodeName = name.replaceAll(DOT_REPLACEMENT, ".");
        decodeName = name.replaceAll(DASH_REPLACEMENT, "-");
        decodeName = name.replaceAll(UNDERSCORE_REPLACEMENT, "_");

        return decodeName;
    }
}
