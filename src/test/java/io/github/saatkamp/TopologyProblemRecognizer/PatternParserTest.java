package io.github.saatkamp.TopologyProblemRecognizer;

import org.junit.Assert;
import org.junit.Test;

public class PatternParserTest {
    @Test
    public void parsePatternMD() throws Exception {
        PatternParser parser = new PatternParser();
        Pattern pattern = parser.parsePatternMD("# Secure Channel\n" +
                "\n" +
                "## Problem\n" +
                "Insecure Communication Channel\n" +
                "\n" +
                "## Problem Rule\n" +
                "\n" +
                "```prolog\n" +
                "insecure_public_communication(Component_1, Component_2) :-\n" +
                "\tproperty(Relation_ID, sensitivedata, true),\n" +
                "\trelation_of_type(Relation_ID, connectsto),\n" +
                "\trelation(Component_1, Component_2, Relation_ID),\n" +
                "\tcomponents_in_different_locations(Component_1, Component_2),\n" +
                "\tnot(property(Relation_ID, security, true)).\n" +
                "```\n" +
                "\n" +
                "## Solution Description\n" +
                "Create secure channels for sensitive data that obscure\n" +
                "the data in transit. Exchange information between\n" +
                "client and server to allow them to set up encrypted\n" +
                "communication between themselves. [...]\n");
        Assert.assertNotNull(pattern);
    }

}
