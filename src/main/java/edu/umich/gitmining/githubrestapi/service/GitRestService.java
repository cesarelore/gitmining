package edu.umich.gitmining.githubrestapi.service;

import com.opencsv.CSVWriter;
import edu.umich.gitmining.githubrestapi.model.Commits;
import edu.umich.gitmining.githubrestapi.model.Contributor;
import edu.umich.gitmining.githubrestapi.model.Repo;
import edu.umich.gitmining.githubrestapi.model.StatsContributors;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitRestService {
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

    public void createCsv() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("User-Agent", "Mozilla/5.0 Firefox/26.0");
        entity = new HttpEntity<>(headers);

        String testUrl;
        testUrl = "https://api.github.com/repos/" + GITHUB_ORG + "/" + GITHUB_REPO;

        List<String> urlListFinance = new ArrayList<>();
        urlListFinance.add("https://api.github.com/repos/plotly/dash");
        urlListFinance.add("https://api.github.com/repos/vnpy/vnpy");
        urlListFinance.add("https://api.github.com/repos/waditu/tushare");
        urlListFinance.add("https://api.github.com/repos/firefly-iii/firefly-iii");
        urlListFinance.add("https://api.github.com/repos/wilsonfreitas/awesome-quant");
        urlListFinance.add("https://api.github.com/repos/signalapp/Signal-Server");

        List<String> urlList = new ArrayList<>();
        urlList.add("https://api.github.com/repos/microsoft/vscode");
        urlList.add("https://api.github.com/repos/microsoftdocs/azure-docs");
        urlList.add("https://api.github.com/repos/flutter/flutter");
        urlList.add("https://api.github.com/repos/tensorflow/tensorflow");
        urlList.add("https://api.github.com/repos/facebook/react-native");
        urlList.add("https://api.github.com/repos/kubernetes/kubernetes");
        urlList.add("https://api.github.com/repos/definitelytyped/definitelytyped");
        urlList.add("https://api.github.com/repos/ansible/ansible");
        urlList.add("https://api.github.com/repos/home-assistant/home-assistant");


        List<String[]> repoListForcsv = new ArrayList<>();
        repoListForcsv.add(new String[]{"id", "name", "size", "watcher_count", "network_count", "subscribers_count"});
        Repo r;

        List<String[]> contributorListForcsv = new ArrayList<>();
        contributorListForcsv.add(new String[]{"repo", "login", "contribution_count"});
        List<Contributor> contributorList;

        List<String[]> commitsListForcsv = new ArrayList<>();
        commitsListForcsv.add(new String[]{"repo", "sha", "author", "committer"});
        List<Commits> commitsList;

        List<String[]> statsContribListForcsv = new ArrayList();
        statsContribListForcsv.add(new String[]{"repo", "author", "total", "week_Unix_Time", "additions", "deletions", "commits"});
        List<StatsContributors> statsContributorsList;

        for (String url : urlList) {
            r = doRepoRestCall(url);
            repoListForcsv.add(r.toStringArray());

            contributorList = doContributorRestCall(url);
            for(Contributor c : contributorList) {
                contributorListForcsv.add(c.toStringArray(r.getName()));
            }
            commitsList = doCommitsRestCall(url);
            for (Commits commits : commitsList) {
                commitsListForcsv.add(commits.toStringArray(r.getName()));
            }
//            statsContributorsList = doStatsContributorsRestCall(url);
//            for (StatsContributors sc : statsContributorsList) {
//                statsContribListForcsv.addAll(sc.prepareForCsv(r.getName()));
//            }

            System.out.println();

        }
        writeToCsv("repo.csv", repoListForcsv);
        writeToCsv("contributor.csv", contributorListForcsv);
        writeToCsv("commits.csv", commitsListForcsv);
//        writeToCsv("statsContributors.csv", statsContribListForcsv);
        System.out.println("\n\nDONE");
    }

    private Repo doRepoRestCall(String baseUrl) {
        String localTestUrl = baseUrl;
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<Repo> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Repo.class);
        Repo repoResult = response.getBody();
        System.out.println("Repo: " + repoResult.getName() + ". Watchers: " + repoResult.getWatchers_count());
        return repoResult;
    }
    private List<Contributor> doContributorRestCall(String baseUrl) {
        String localTestUrl = baseUrl + "/contributors?per_page=100";
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<Contributor[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Contributor[].class);
        List<Contributor> contributorList = Arrays.asList(response.getBody());
//        System.out.println("\n\nHEADERS\n" + response.getHeaders());
        System.out.println("Contributors Size: " + contributorList.size());
        System.out.println("Contributors first element: " + contributorList.get(0));
        return contributorList;
    }

    private List<Commits> doCommitsRestCall(String baseUrl) {
        String localTestUrl = baseUrl + "/commits?per_page=100";
        System.out.println("URL: " + localTestUrl);

        ResponseEntity<Commits[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, Commits[].class); //must use restTemplate.exchange not restTemplate.getForEntity
        List<Commits> commitsList = Arrays.asList(response.getBody());
        System.out.println("Commits length: " + commitsList.size());
        System.out.println("Commits first element: " + commitsList.get(0).toString());

        return commitsList;
    }

    private List<StatsContributors> doStatsContributorsRestCall(String baseUrl) {
        String localTestUrl = baseUrl + "/stats/contributors";
        System.out.println("URL: " + localTestUrl);
        ResponseEntity<StatsContributors[]> response = restTemplate.exchange(localTestUrl, HttpMethod.GET, entity, StatsContributors[].class);
        List<StatsContributors> statsContributorsList = Arrays.asList(response.getBody());
        System.out.println("StatsContrib length: " + statsContributorsList.size());
        System.out.println("StatsContrib first element: Total=" + statsContributorsList.get(0).getTotal() + ". Weeks Array length:" + statsContributorsList.get(0).getWeeks().length);
        return statsContributorsList;
    }

    private void writeToCsv(String filename, List<String[]> it) {
        String directory = "C:\\Users\\admin\\Documents\\UofMDearborn\\CIS580-202009Fall-DataAnalyticsinSoftwareEngineering\\ProjectStep03\\";
        File f = new File(directory + filename);
        f.delete();
        FileWriter out;
        try {
            out = new FileWriter(directory + filename);
            CSVWriter writer = new CSVWriter(out);
            writer.writeAll(it);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}