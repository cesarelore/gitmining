package edu.umich.gitmining.githubrestapi.service;

import edu.umich.gitmining.githubrestapi.model.Commits;
import edu.umich.gitmining.githubrestapi.model.Contributor;
import edu.umich.gitmining.githubrestapi.model.Repo;
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
    private final String GITHUB_ORG = "signalapp";
    private final String GITHUB_REPO = "Signal-Server";
    private String testUrl;

    @Value("${github.username:username-missing}")
    private String githubUsername;

    @Value("${github.token:token-missing}")
    private String githubToken;

    public void makeTestCall() {
        testUrl = "https://api.github.com/repos/" + GITHUB_ORG + "/" + GITHUB_REPO;

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("User-Agent", "Mozilla/5.0 Firefox/26.0");
        entity = new HttpEntity<>(headers);
        doContributorRestCall();
        doCommitsRestCall();
        doRepoCall();

        // TODO: use list instead of single URL
        List<String> urlList = new ArrayList<>();
        urlList.add("https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100&page=1");
        urlList.add("https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100&page=2");

    }

    private Repo doRepoRestCall() {
        String localTestUrl = testUrl;
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<Repo> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Repo.class);
        Repo repoResult = response.getBody();
        System.out.println("Repo: " + repoResult.getName());
        return repoResult;
    }
    private List<Contributor> doContributorRestCall() {
        String localTestUrl = testUrl + "/contributors";
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<Contributor[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Contributor[].class);
        List<Contributor> contributorList = Arrays.asList(response.getBody());
//        System.out.println("\n\nHEADERS\n" + response.getHeaders());
        System.out.println("Contributors Size: " + contributorList.size());
        return contributorList;
    }

    private List<Commits> doCommitsRestCall() {
        String localTestUrl = testUrl + "/commits?per_page=100";
        System.out.println("URL: " + localTestUrl);

        ResponseEntity<Commits[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Commits[].class); //must use restTemplate.exchange not restTemplate.getForEntity
        List<Commits> commitsList = Arrays.asList(response.getBody());
        System.out.println("Commits length: " + commitsList.size());
        return commitsList;
    }
}
