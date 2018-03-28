package io.github.saatkamp.TopologyProblemRecognizer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.List;

@RestController
public class WebController {

    @GetMapping("/recognize")
    public String getRecognizedProblemsInTopology
            (@RequestParam("wineryURL") String wineryURL,
             @RequestParam("serviceTemplateNS") String serviceTemplateNS,
             @RequestParam("serviceTemplateID") String serviceTemplateID) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrologNames prologNames = new PrologNames();

        PrologFactTopologyGenerator generator = new PrologFactTopologyGenerator(wineryURL, prologNames);
        ProblemRecognizer recognizer = new ProblemRecognizer(prologNames);

        ServiceTemplateId serviceTemplateId = new ServiceTemplateId(serviceTemplateNS, serviceTemplateID, false);
        QName serviceTemplateQName = new QName(serviceTemplateNS, serviceTemplateID);
        generator.generatePrologFileForTopology(serviceTemplateQName);
        List<ProblemOccurrence> problemOccurrenceList = recognizer.checkTopology(serviceTemplateID);
        ProblemOccurrences occurrences = new ProblemOccurrences(problemOccurrenceList);

        return mapper.writeValueAsString(occurrences);
    }

}
