package edu.umich.gitmining.githubrestapi.service;

import edu.umich.gitmining.githubrestapi.model.Commits;
import edu.umich.gitmining.githubrestapi.model.Contributor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private HttpEntity entity;
    private final String ACCEPT_HEADER = "application/vnd.github+json";

    @Value("${github.username:username-missing}")
    private String githubUsername;

    @Value("${github.token:token-missing}")
    private String githubToken;

    public void makeTestCall() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("User-Agent", "Mozilla/5.0 Firefox/26.0");
        entity = new HttpEntity<>(headers);
        doContributorRestCall();
        doCommitsRestCall();
    }

    private void doContributorRestCall() {
        String testUrl = "https://api.github.com/repos/signalapp/Signal-Server/stats/contributors";
        ResponseEntity<Contributor[]> response = restTemplate.exchange(testUrl, HttpMethod.GET, entity, Contributor[].class);
        List<Contributor> contributorList = Arrays.asList(response.getBody());
//        System.out.println("\n\nHEADERS\n" + response.getHeaders());
        System.out.println("Contributors Size: " + contributorList.size());
    }

    private void doCommitsRestCall() {
        List<String> urlList = new ArrayList<>();
        urlList.add("https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100&page=1");
        urlList.add("https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100&page=2");

        String testUrl = "https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100";
        ResponseEntity<Commits[]> response = restTemplate.exchange(testUrl, HttpMethod.GET, entity, Commits[].class); //must use restTemplate.exchange not restTemplate.getForEntity
        Commits[] commitArray = response.getBody();
        List<Commits> commitsList = Arrays.asList(commitArray);
//        System.out.println("\n\nHEADERS\n" + response.getHeaders());
        System.out.println("Commits length: " + commitArray.length + " " + commitsList.size());
//        System.out.println(response.getBody()[0].toString());
    }

}
