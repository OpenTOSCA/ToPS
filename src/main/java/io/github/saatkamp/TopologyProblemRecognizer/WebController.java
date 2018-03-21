package io.github.saatkamp.TopologyProblemRecognizer;


import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.namespace.QName;
import java.io.IOException;

@RestController
public class WebController {

    @GetMapping("/recognize")
    public String getRecognizedProblemsInTopology
            (@RequestParam("wineryURL") String wineryURL,
             @RequestParam("serviceTemplateNS") String serviceTemplateNS,
             @RequestParam("serviceTemplateID") String serviceTemplateID) {

        PrologFactTopologyGenerator generator = new PrologFactTopologyGenerator();
        generator.setRepositoryClientURL(wineryURL);
        ProblemRecognizer recognizer = new ProblemRecognizer();
        try {
            ServiceTemplateId serviceTemplateId = new ServiceTemplateId(serviceTemplateNS, serviceTemplateID, false);
            QName serviceTemplateQName = new QName(serviceTemplateNS, serviceTemplateID);
            generator.generatePrologFileForTopology(serviceTemplateQName);
            recognizer.checkTopology(serviceTemplateID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Hello you too!!!!!";
    }

    @GetMapping
    public String doSomething() {

        return "Hello you too!!!!!";
    }

}
