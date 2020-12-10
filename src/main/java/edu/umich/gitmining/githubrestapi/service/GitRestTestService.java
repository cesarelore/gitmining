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

        String testUrl;
        testUrl = "https://api.github.com/repos/" + GITHUB_ORG + "/" + GITHUB_REPO;

        // TODO: use list instead of single URL
        List<String> urlList = new ArrayList<>();
        urlList.add("https://api.github.com/repos/plotly/dash");
        urlList.add("https://api.github.com/repos/vpny/vpny");
        urlList.add("https://api.github.com/repos/waditu/tushare");
        urlList.add("https://api.github.com/repos/firefly-iii/firefly-iii ");
        urlList.add("https://api.github.com/repos/wilsonfreitas/awesome-quant");

        Repo r;
        List<Contributor> contributorList;
        List<Commits> commitsList;
        for (String url : urlList) {
            contributorList = doContributorRestCall(url);
            commitsList = doCommitsRestCall(url);
            r = doRepoRestCall(url);

            // Do something with the results here.
        }

//        doContributorRestCall(testUrl);
//        doCommitsRestCall(testUrl);
//        doRepoRestCall(testUrl);

    }

    private Repo doRepoRestCall(String baseUrl) {
        String localTestUrl = baseUrl;
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<Repo> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Repo.class);
        Repo repoResult = response.getBody();
        System.out.println("Repo: " + repoResult.getName());
        return repoResult;
    }
    private List<Contributor> doContributorRestCall(String baseUrl) {
        String localTestUrl = baseUrl + "/contributors";
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<Contributor[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Contributor[].class);
        List<Contributor> contributorList = Arrays.asList(response.getBody());
//        System.out.println("\n\nHEADERS\n" + response.getHeaders());
        System.out.println("Contributors Size: " + contributorList.size());
        return contributorList;
    }

    private List<Commits> doCommitsRestCall(String baseUrl) {
        String localTestUrl = baseUrl + "/commits?per_page=100";
        System.out.println("URL: " + localTestUrl);

        ResponseEntity<Commits[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Commits[].class); //must use restTemplate.exchange not restTemplate.getForEntity
        List<Commits> commitsList = Arrays.asList(response.getBody());
        System.out.println("Commits length: " + commitsList.size());
        return commitsList;
    }
}
