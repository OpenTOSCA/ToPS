package utils;

import org.eclipse.winery.common.ids.definitions.RelationshipTypeId;
import org.eclipse.winery.model.tosca.TNodeTemplate;
import org.eclipse.winery.model.tosca.TRelationshipTemplate;
import org.eclipse.winery.model.tosca.TRelationshipType;
import org.eclipse.winery.model.tosca.TTopologyTemplate;
import org.eclipse.winery.model.tosca.utils.ModelUtilities;
import org.eclipse.winery.repository.client.IWineryRepositoryClient;
import org.eclipse.winery.repository.client.WineryRepositoryClientFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TOSCAModelUtilities {

    public TOSCAModelUtilities(String wineryURL) {
        this.repositoryClient.addRepository(wineryURL);
        this.repositoryClient.setPrimaryRepository(wineryURL);
        System.out.println("Repository available?" + repositoryClient.primaryRepositoryAvailable());
    }
    private IWineryRepositoryClient repositoryClient = WineryRepositoryClientFactory.getWineryRepositoryClient();


    public List<TNodeTemplate> getNodeTemplatesWithoutIncomingHostedOnRelationships(TTopologyTemplate topologyTemplate) {

        return topologyTemplate.getNodeTemplates()
                .stream()
                .filter(nt -> getHostedOnPredecessorsOfNodeTemplate(topologyTemplate, nt).isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Find all predecessors of a node template. the predecessor is the target of a hostedOn relationship to the
     * nodeTemplate
     *
     * @param nodeTemplate for which all predecessors should be found
     * @return list of predecessors
     */
    public List<TNodeTemplate> getHostedOnPredecessorsOfNodeTemplate(TTopologyTemplate topologyTemplate, TNodeTemplate nodeTemplate) {
        List<TNodeTemplate> predecessorNodeTemplates = new ArrayList<>();
        predecessorNodeTemplates.clear();
        List<TRelationshipTemplate> incomingRelationships = ModelUtilities.getIncomingRelationshipTemplates(topologyTemplate, nodeTemplate);
        for (TRelationshipTemplate relationshipTemplate : incomingRelationships) {
            if ((getBasisRelationshipType(relationshipTemplate.getType()).getValidTarget() != null &&
                    getBasisRelationshipType(relationshipTemplate.getType()).getValidTarget().getTypeRef().getLocalPart().equalsIgnoreCase("Container"))
                    || (getBasisRelationshipType(relationshipTemplate.getType()).getName().equalsIgnoreCase("hostedOn"))) {
                predecessorNodeTemplates.add(ModelUtilities.getSourceNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate));
            }
        }
        return predecessorNodeTemplates;
    }

    /**
     * Find all successors of a node template. the successor is the target of a hostedOn relationship to the
     * nodeTemplate
     *
     * @param nodeTemplate for which all successors should be found
     * @return list of successors (node templates)
     */
    public List<TNodeTemplate> getHostedOnSuccessorsOfNodeTemplate(TTopologyTemplate topologyTemplate, TNodeTemplate nodeTemplate) {
        List<TNodeTemplate> successorNodeTemplates = new ArrayList<>();
        for (TRelationshipTemplate relationshipTemplate : ModelUtilities.getOutgoingRelationshipTemplates(topologyTemplate, nodeTemplate)) {
            if ((getBasisRelationshipType(relationshipTemplate.getType()).getValidTarget() != null &&
                    getBasisRelationshipType(relationshipTemplate.getType()).getValidTarget().getTypeRef().getLocalPart().equalsIgnoreCase("Container"))
                    || (getBasisRelationshipType(relationshipTemplate.getType()).getName().equalsIgnoreCase("hostedOn"))) {
                successorNodeTemplates.add(ModelUtilities.getTargetNodeTemplateOfRelationshipTemplate(topologyTemplate, relationshipTemplate));
            }
        }
        return successorNodeTemplates;
    }



    public TRelationshipType getBasisRelationshipType(QName relationshipTypeQName) {
        RelationshipTypeId parentRelationshipTypeId = new RelationshipTypeId(relationshipTypeQName);
        TRelationshipType parentRelationshipType = this.repositoryClient.getType(relationshipTypeQName, TRelationshipType.class);
        TRelationshipType basisRelationshipType = parentRelationshipType;

        while (parentRelationshipType != null) {
            basisRelationshipType = parentRelationshipType;

            if (parentRelationshipType.getDerivedFrom() != null) {
                relationshipTypeQName = parentRelationshipType.getDerivedFrom().getTypeRef();
                parentRelationshipTypeId = new RelationshipTypeId(relationshipTypeQName);
                parentRelationshipType = this.repositoryClient.getType(relationshipTypeQName, TRelationshipType.class);
            } else {
                parentRelationshipType = null;
            }
        }
        return basisRelationshipType;
    }
}
