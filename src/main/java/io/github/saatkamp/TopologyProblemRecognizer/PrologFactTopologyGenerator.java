package io.github.saatkamp.TopologyProblemRecognizer;

import org.eclipse.winery.common.ids.definitions.RelationshipTypeId;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.model.tosca.utils.ModelUtilities;
import org.eclipse.winery.repository.client.IWineryRepositoryClient;
import org.eclipse.winery.repository.client.WineryRepositoryClientFactory;

import javax.xml.namespace.QName;
import java.io.*;
import java.util.Map;

public class PrologFactTopologyGenerator {

    private IWineryRepositoryClient repositoryClient = WineryRepositoryClientFactory.getWineryRepositoryClient();

    /**
     * Set the repository URL to the URL of the running winery - should be localhost:8080/winery
     * @param url
     */
    public void setRepositoryClientURL(String url) {
        this.repositoryClient.addRepository(url);
        this.repositoryClient.setPrimaryRepository(url);
        System.out.println("Repository available?" + repositoryClient.primaryRepositoryAvailable());
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
            plContent = plContent
                    + "component(" + nodeTemplate.getId().toLowerCase() + ")." + newline;
        }

        //Transforms Relationship Templates in relation([sourceNodeTemplateID], [targetNodeTemplateID], [relationshipTemplateID]).
        for(TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            TNodeTemplate sourceNodeTemplate
                    = ModelUtilities.getSourceNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate);
            TNodeTemplate targetNodeTemplate
                    = ModelUtilities.getTargetNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate);

            plContent = plContent
                    + "relation(" + sourceNodeTemplate.getId().toLowerCase() +", " + targetNodeTemplate.getId().toLowerCase() + ", " + relationshipTemplate.getId().toLowerCase() + ")." + newline;
        }

        //Transforms Relationship Types contained in the topology in relation_of_type([relationshipTemplateID], [relationshipTypeID]).
        for(TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            RelationshipTypeId relationshipTypeId = new RelationshipTypeId(relationshipTemplate.getType());
            //No Type checking of the Relationship Type - must be one of the normative types
            //TODO: An extenstion to type hierarchies is required to enable a check of supertypes
            plContent = plContent
                    + "relation_of_type(" + relationshipTemplate.getId().toLowerCase() + ", " + relationshipTypeId.getXmlId().toString().toLowerCase() + ")." + newline;
        }

        //Transforms targetLabels contained in the topology in targetLabel([nodeTemplateID], [targetLabelID]).
        for (TNodeTemplate nodeTemplate : topologyTemplate.getNodeTemplates()) {
            String targetLabel = ModelUtilities.getTargetLabel(nodeTemplate).orElse("undefined");
            plContent = plContent
                    + "targetLabel(" + nodeTemplate.getId().toLowerCase() + ", " + targetLabel.toLowerCase() + ")." + newline;
        }

        //Transforms KVProperties of Relationships in property([relationshipTemplateID], [KVPropertyKey], [KVPropertyValue]).
        for (TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            if(relationshipTemplate.getProperties() != null) {
                Map<String, String> kVProperties = relationshipTemplate.getProperties().getKVProperties();
                if(kVProperties != null) {
                    for (Map.Entry<String, String> entry: kVProperties.entrySet()) {
                        if(entry.getValue() != "") {
                            plContent = plContent
                                    + "property(" + relationshipTemplate.getId().toLowerCase() + ", "
                                    + entry.getKey().toLowerCase() + ", " + entry.getValue().toLowerCase() + ")." + newline;
                        }
                    }
                }
            }
        }

        persistPrologFile(plContent, serviceTemplateQName.getLocalPart().toString());
    }

    private void persistPrologFile (String topology, String fileName) throws IOException {
        File file = new File("topologies/" + fileName + ".pl");
        try {
            FileWriter fileWriter = new FileWriter(file);
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(topology);
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not write prolog program to fuke");
            throw new IOException(e);
        }

    }

}
