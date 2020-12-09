package edu.umich.gitmining.githubrestapi.service;

import edu.umich.gitmining.githubrestapi.model.Commits;
import edu.umich.gitmining.githubrestapi.model.Contributor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitRestTestService {
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String ACCEPT_HEADER = "application/vnd.github+json";

    @Value("${github.username:username-missing}")
    private String githubUsername;

    @Value("${github.token:token-missing}")
    private String githubToken;

    public void makeTestCall() {
        restTemplate = new RestTemplate();
        //restTemplateBuilder
        //            .basicAuthentication(gitUsername, gitToken)
        //            .build();
        //    }
         headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Accept", ACCEPT_HEADER);
        headers.add("Authorization", "token " + githubToken);
        System.out.println("Username from app.props: " + githubUsername);
//        HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
//        restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

        doContributorRestCall();
        doCommitsRestCall();
    }

    private void doContributorRestCall() {
        String testUrl = "https://api.github.com/repos/signalapp/Signal-Server/contributors";
        ResponseEntity<Contributor[]> response = restTemplate.getForEntity(testUrl, Contributor[].class, headers);
        List<Contributor> contributorList = Arrays.asList(response.getBody());
        System.out.println("\n\nHEADERS\n" + response.getHeaders());
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
