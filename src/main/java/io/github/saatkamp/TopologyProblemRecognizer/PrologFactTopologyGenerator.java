package io.github.saatkamp.TopologyProblemRecognizer;

import org.eclipse.winery.common.ids.definitions.RelationshipTypeId;
import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TServiceTemplate;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.model.tosca.utils.ModelUtilities;
import org.eclipse.winery.repository.client.IWineryRepositoryClient;
import org.eclipse.winery.repository.client.WineryRepositoryClientFactory;

import java.io.*;
import java.util.Map;

public class PrologFactTopologyGenerator {

    private IWineryRepositoryClient repositoryClient = WineryRepositoryClientFactory.getWineryRepositoryClient();

    public void setRepositoryClientURL(String url) {
        this.repositoryClient.addRepository(url);
        this.repositoryClient.setPrimaryRepository(url);
        System.out.println("Repository available?" + repositoryClient.primaryRepositoryAvailable());
    }

    public void transform(ServiceTemplateId serviceTemplateId) throws IOException {
        String plContent = "%%This file contains a TOSCA Topology as Prolog facts";
        String newline = System.getProperty("line.separator");
        plContent = plContent + newline;
        /*QName serviceTemplateQName = QName.valueOf(TOSCAServiceTemplateQName);
        ServiceTemplateId serviceTemplateId =
                new ServiceTemplateId(serviceTemplateQName.getNamespaceURI().toString(),
                        serviceTemplateQName.getLocalPart().toString(), false);*/

        TServiceTemplate serviceTemplate = this.repositoryClient.getElement(serviceTemplateId);
        TTopologyTemplate topologyTemplate = serviceTemplate.getTopologyTemplate();

        for(TNodeTemplate nodeTemplate: topologyTemplate.getNodeTemplates()) {
            plContent = plContent
                    + "component(" + nodeTemplate.getId() + ")." + newline;
        }
        for(TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            TNodeTemplate sourceNodeTemplate
                    = ModelUtilities.getSourceNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate);
            TNodeTemplate targetNodeTemplate
                    = ModelUtilities.getTargetNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate);

            plContent = plContent
                    + "relation(" + sourceNodeTemplate.getId() +", " + targetNodeTemplate.getId() + ", " + relationshipTemplate.getId() + ")." + newline;
        }
        for(TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            RelationshipTypeId relationshipTypeId = new RelationshipTypeId(relationshipTemplate.getType());
            //No Type checking of the Relationship Type - must be one of the normative types
            //TODO: An extenstion to type hoerarchies is required to enable a check of supertypes
            plContent = plContent
                    + "relation_of_type(" + relationshipTemplate.getId() + ", " + relationshipTypeId.getXmlId().toString() + ")." + newline;
        }
        for (TNodeTemplate nodeTemplate : topologyTemplate.getNodeTemplates()) {
            String targetLabel = ModelUtilities.getTargetLabel(nodeTemplate).orElse("undefined");
            plContent = plContent
                    + "targetLabel(" + nodeTemplate.getId() + ", " + targetLabel + ")." + newline;
        }
        for (TRelationshipTemplate relationshipTemplate: topologyTemplate.getRelationshipTemplates()) {
            if(relationshipTemplate.getProperties() != null) {
                Map<String, String> kVProperties = relationshipTemplate.getProperties().getKVProperties();
                if(kVProperties != null) {
                    for (Map.Entry<String, String> entry: kVProperties.entrySet()) {
                        plContent = plContent
                                + "property(" + relationshipTemplate.getId() + ", " + entry.getKey() + ", " + entry.getValue() + ")." + newline;
                    }
                }
            }
        }


        persistPrologFile(plContent, serviceTemplate.getId());
    }

    private void persistPrologFile (String topology, String fileName) throws IOException {
        File file = new File(fileName + ".pl");
        try {
            FileWriter fileWriter = new FileWriter(file);
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(topology);
        } catch (IOException e) {
            System.out.println("Could not write prolog program to fuke");
            throw new IOException(e);
        }

    }

}
