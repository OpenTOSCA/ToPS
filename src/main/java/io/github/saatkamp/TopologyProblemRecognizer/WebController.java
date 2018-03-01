package io.github.saatkamp.TopologyProblemRecognizer;


import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        try {
            ServiceTemplateId serviceTemplateId = new ServiceTemplateId(serviceTemplateNS, serviceTemplateID, true);
            generator.transform(serviceTemplateId);
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
