package edu.umich.gitmining.githubrestapi.service;

import edu.umich.gitmining.githubrestapi.model.Commits;
import edu.umich.gitmining.githubrestapi.model.Contributor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitRestTestService {
    private RestTemplate restTemplate;
    private final String ACCEPT_HEADER = "application/vnd.github+json";


    public void makeTestCall() {
        restTemplate = new RestTemplate();
        //restTemplateBuilder
        //            .basicAuthentication(gitUsername, gitToken)
        //            .build();
        //    }

        doContributorRestCall();
        doCommitsRestCall();
    }

    private void doContributorRestCall() {
        String testUrl = "https://api.github.com/repos/signalapp/Signal-Server/contributors";
        ResponseEntity<Contributor[]> response = restTemplate.getForEntity(testUrl, Contributor[].class);
        List<Contributor> contributorList = Arrays.asList(response.getBody());
        System.out.println(contributorList.size());
    }

    private void doCommitsRestCall() {
        List<String> urlList = new ArrayList<>();
        urlList.add("https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100&page=1");
        urlList.add("https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100&page=2");

        String testUrl = "https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100";
        ResponseEntity<Commits[]> response = restTemplate.getForEntity(testUrl, Commits[].class);
        Commits[] commitArray = response.getBody();
        List<Commits> commitsList = Arrays.asList(commitArray);
//        System.out.println(commitArray.length + " " + commitsList.size());
//        System.out.println(response.getBody()[0].toString());
    }

}
