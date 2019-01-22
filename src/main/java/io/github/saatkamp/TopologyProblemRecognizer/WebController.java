package io.github.saatkamp.TopologyProblemRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.saatkamp.TopologyProblemRecognizer.model.ComponentFinding;
import io.github.saatkamp.TopologyProblemRecognizer.model.ProblemFindings;
import io.github.saatkamp.TopologyProblemRecognizer.model.ProblemOccurrence;
import io.github.saatkamp.TopologyProblemRecognizer.model.SolutionInputData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WebController {

    private PrologNames prologNames = new PrologNames();

    @GetMapping("/checkProblems")
    public List<ProblemFindings> getRecognizedProblemsInTopology
            (@RequestParam("wineryURL") String wineryURL,
             @RequestParam("serviceTemplateNS") String serviceTemplateNS,
             @RequestParam("serviceTemplateID") String serviceTemplateID) throws IOException {

        long start = System.currentTimeMillis();

        PrologFactTopologyGenerator generator = new PrologFactTopologyGenerator(wineryURL, prologNames);
        PrologChecker recognizer = new PrologChecker(prologNames);

        QName serviceTemplateQName = new QName(serviceTemplateNS, serviceTemplateID);
        generator.generatePrologFileForTopology(serviceTemplateQName);
        List<ProblemFindings> problemFindingsList = recognizer.checkTopology(serviceTemplateID);

        //done
        long runningTime = System.currentTimeMillis();
        long requiredTime = runningTime - start;
        System.out.println("Start Time:" + start);
        System.out.println("End Time:" + runningTime);
        System.out.println("Required Time:" + requiredTime + "ms");

        return problemFindingsList;
    }

    @GetMapping("/testdata")
    public ProblemOccurrence getTestData() throws JsonProcessingException {
        List<ComponentFinding> testData = new ArrayList<>();
        ComponentFinding finding1 = new ComponentFinding("Component1", "PHPApp");
        ComponentFinding finding2 = new ComponentFinding("Component2", "JavaAPP");
        testData.add(finding1);
        testData.add(finding2);

        ProblemOccurrence occurrence = new ProblemOccurrence("test", "test",
                "test", "test00", "test", testData);

        return occurrence;
    }

    @PostMapping("/findSolutions")
    public List<SolutionInputData> getMatchingSolutionsForProblem(@RequestBody ProblemOccurrence problemOccurrence) throws IOException {
        PrologChecker checker = new PrologChecker(prologNames);

        List<SolutionInputData> solutions = checker.findSolutions(problemOccurrence.getServiceTemplateId(),
                problemOccurrence.getPatternName(), problemOccurrence.getOccurrence());

        return solutions;
    }
}
