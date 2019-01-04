package io.github.saatkamp.TopologyProblemRecognizer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.winery.common.ids.definitions.ServiceTemplateId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.List;

@RestController
public class WebController {

    @GetMapping("/checkProblems")
    public String getRecognizedProblemsInTopology
            (@RequestParam("wineryURL") String wineryURL,
             @RequestParam("serviceTemplateNS") String serviceTemplateNS,
             @RequestParam("serviceTemplateID") String serviceTemplateID) throws IOException {

        long start = System.currentTimeMillis();

        ObjectMapper mapper = new ObjectMapper();
        PrologNames prologNames = new PrologNames();

        PrologFactTopologyGenerator generator = new PrologFactTopologyGenerator(wineryURL, prologNames);
        PrologChecker recognizer = new PrologChecker(prologNames);

        ServiceTemplateId serviceTemplateId = new ServiceTemplateId(serviceTemplateNS, serviceTemplateID, false);
        QName serviceTemplateQName = new QName(serviceTemplateNS, serviceTemplateID);
        generator.generatePrologFileForTopology(serviceTemplateQName);
        List<ProblemFindings> problemFindingsList = recognizer.checkTopology(serviceTemplateID);

        //done
        long runningTime = System.currentTimeMillis();
        long requiredTime = runningTime - start;
        System.out.println("Start Time:" + start);
        System.out.println("End Time:" + runningTime);
        System.out.println("Required Time:" + requiredTime + "ms");

        return mapper.writeValueAsString(problemFindingsList);
    }

    @PostMapping("/findSolutions")
    public String getMatchingSolutionsForProblem (@RequestBody ProblemOccurrence problemOccurrence) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrologNames prologNames = new PrologNames();
        PrologChecker checker = new PrologChecker(prologNames);

        List<Solution> solutions = checker.findSolutions(problemOccurrence.getServiceTemplateID(),
                problemOccurrence.getProblem(), problemOccurrence.getOccurrence());

        return mapper.writeValueAsString(solutions);
    }

}
