package io.github.saatkamp.TopologyProblemRecognizer;

import org.eclipse.winery.common.ids.definitions.NodeTypeId;
import org.eclipse.winery.common.ids.definitions.RelationshipTypeId;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.model.tosca.utils.ModelUtilities;
import org.eclipse.winery.repository.client.IWineryRepositoryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TOSCAModelUtilities;

import javax.xml.namespace.QName;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PrologFactTopologyGenerator {

    private IWineryRepositoryClient repositoryClient;
    private static Logger logger = LoggerFactory.getLogger(PrologFactTopologyGenerator.class);
    private PrologNames prologNames;

    public PrologFactTopologyGenerator(String wineryURL, PrologNames prologNames) {
        TOSCAModelUtilities.setWineryUrl(wineryURL);
        this.prologNames = Objects.requireNonNull(prologNames);
        this.repositoryClient = TOSCAModelUtilities.repositoryClient;
        logger.info("Repository available?", repositoryClient.primaryRepositoryAvailable());
    }

    /**
     * Takes a topology template from the repository and generates a prolog file for this topology based on the metamodel.
     * Because in Prolog Variables starts with a capital letter all ids are transformed to lower case.
     * @param serviceTemplateQName
     * @throws IOException
     */
    public void generatePrologFileForTopology(QName serviceTemplateQName) throws IOException {
        String plContent = "";
        String newline = System.getProperty("line.separator");
        plContent = plContent + newline;

        TTopologyTemplate topologyTemplate = this.repositoryClient.getTopologyTemplate(serviceTemplateQName);

        //Transforms Node Templates in component([nodeTemplateID]).
        for(TNodeTemplate nodeTemplate: topologyTemplate.getNodeTemplates()) {
            String id = prologNames.encode(nodeTemplate.getId());
            plContent = plContent + "component(" + id + ")." + newline;
        }

        //Transforms Node Types contained in the topology in component_of_type([nodeTemplateID], [nodeTypeID]).
        for(TNodeTemplate nodeTemplate: topologyTemplate.getNodeTemplates()) {
            NodeTypeId nodeTypeId = new NodeTypeId(nodeTemplate.getType());
            String componentID = prologNames.encode(nodeTemplate.getId());
            String typeID = prologNames.encode(nodeTypeId.getXmlId().toString());
            //No Type checking of the Node Type - must be one of the normative types
            //TODO: An extenstion to type hierarchies is required to enable a check of supertypes
            plContent = plContent + "component_of_type(" + componentID + ", " + typeID + ")." + newline;
        }

        //Transforms Relationship Templates in relation([sourceNodeTemplateID], [targetNodeTemplateID], [relationshipTemplateID]).
        for(TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            TNodeTemplate sourceNodeTemplate
                    = ModelUtilities.getSourceNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate);
            TNodeTemplate targetNodeTemplate
                    = ModelUtilities.getTargetNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate);
            String sourceID = prologNames.encode(sourceNodeTemplate.getId());
            String targetID = prologNames.encode(targetNodeTemplate.getId());
            String relationID = prologNames.encode(relationshipTemplate.getId());
            plContent = plContent + "relation(" + sourceID +", " + targetID + ", " + relationID + ")." + newline;
        }

        //Transforms Relationship Types contained in the topology in relation_of_type([relationshipTemplateID], [relationshipTypeID]).
        for(TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            RelationshipTypeId relationshipTypeId = new RelationshipTypeId(relationshipTemplate.getType());
            //No Type checking of the Relationship Type - must be one of the normative types
            //TODO: An extenstion to type hierarchies is required to enable a check of supertypes
            String relationID = prologNames.encode(relationshipTemplate.getId());
            String typeID = prologNames.encode(relationshipTypeId.getXmlId().toString());
            plContent = plContent + "relation_of_type(" + relationID + ", " + typeID + ")." + newline;
        }

        //Transforms KVProperties of Relationships in property([relationshipTemplateID], [KVPropertyKey], [KVPropertyValue]).
        for (TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            if(relationshipTemplate.getProperties() != null) {
                Map<String, String> kVProperties = relationshipTemplate.getProperties().getKVProperties();
                if(kVProperties != null) {
                    for (Map.Entry<String, String> entry: kVProperties.entrySet()) {
                        if(entry.getValue() != "") {
                            String relationID = prologNames.encode(relationshipTemplate.getId());
                            String keyID = prologNames.encode(entry.getKey());
                            String valueID = prologNames.encode(entry.getValue());
                            plContent = plContent
                                    + "property(" + relationID + ", " + keyID + ", " + valueID + ")." + newline;
                        }
                    }
                }
            }
        }

        //Transforms KVProperties of Nodes in property([nodeTemplateID], [KVPropertyKey], [KVPropertyValue]).
        for (TNodeTemplate nodeTemplate: topologyTemplate.getNodeTemplates()) {
            if(nodeTemplate.getProperties() != null) {
                Map<String, String> kVProperties = nodeTemplate.getProperties().getKVProperties();
                if(kVProperties != null) {
                    for (Map.Entry<String, String> entry: kVProperties.entrySet()) {
                        if(entry.getValue() != "") {
                            String nodeID = prologNames.encode(nodeTemplate.getId());
                            String keyID = prologNames.encode(entry.getKey());
                            String valueID = prologNames.encode(entry.getValue());
                            plContent = plContent
                                    + "property(" + nodeID + ", " + keyID + ", " + valueID + ")." + newline;
                        }
                    }
                }
            }
        }

        List<TNodeTemplate> nodesWithoutIncomingHostedOnRelationships = TOSCAModelUtilities.getNodeTemplatesWithoutIncomingHostedOnRelationships(topologyTemplate);

        for (TNodeTemplate nodeTemplate : nodesWithoutIncomingHostedOnRelationships) {
            List<TNodeTemplate> hostStack = getHostStack(topologyTemplate, nodeTemplate);
            plContent = plContent + "hosting_stack([" + prologNames.encode(hostStack.get(0).getId());
            hostStack.remove(0);
            for (TNodeTemplate hostingNode : hostStack) {
                plContent = plContent + ", " + prologNames.encode(hostingNode.getId());
            }
            plContent = plContent + "])." + newline;
        }

        persistPrologFile(plContent, serviceTemplateQName.getLocalPart().toString());
    }

    private List<TNodeTemplate> getHostStack (TTopologyTemplate topologyTemplate, TNodeTemplate nodeTemplate) {
        List<TNodeTemplate> hostStack = new ArrayList<>();
        hostStack.add(nodeTemplate);
        List<TNodeTemplate> hostedOnPredecessors = TOSCAModelUtilities.getHostedOnSuccessorsOfNodeTemplate(topologyTemplate, nodeTemplate);
        while(!hostedOnPredecessors.isEmpty()) {
            List<TNodeTemplate> transitiveHosts = new ArrayList<>();
            hostedOnPredecessors.stream().forEach(host -> {
                hostStack.add(host);
                transitiveHosts.addAll(TOSCAModelUtilities.getHostedOnSuccessorsOfNodeTemplate(topologyTemplate, host));
            });
            hostedOnPredecessors.clear();
            hostedOnPredecessors.addAll(transitiveHosts);
        }


        return  hostStack;
    }

    private void persistPrologFile (String topology, String fileName) throws IOException {
        File file = new File("topologies/" + fileName + ".pl");
        try {
            FileWriter fileWriter = new FileWriter(file);
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(topology);
            writer.close();
        } catch (IOException e) {
            throw new IOException("Could not write topology facts", e);
        }

    }

}
