package io.github.saatkamp.TopologyProblemRecognizer;


import org.eclipse.winery.repository.client.IWineryRepositoryClient;
import org.eclipse.winery.repository.client.WineryRepositoryClientFactory;
import org.jpl7.fli.Prolog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping
    public String doSomething() {
        IWineryRepositoryClient repositoryClient = WineryRepositoryClientFactory.getWineryRepositoryClient();
        repositoryClient.setPrimaryRepository("http://localhost:8080/winery");
        System.out.println(Prolog.initialise());
        //String patterns = "['C:\\\\Users\\\\saatkake\\\\Documents\\\\Veröffentlichungen\\\\2018_SummerSOC\\\\prolog_files\\\\topology'].";
        //Query q1 = new Query(patterns);
        //System.out.println( "consult " + (q1.hasSolution()));
        return "Hello you too!!!!!";
    }

}
