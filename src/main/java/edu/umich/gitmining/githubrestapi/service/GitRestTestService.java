package edu.umich.gitmining.githubrestapi.service;

import edu.umich.gitmining.githubrestapi.model.Commits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class GitRestTestService {
    private RestTemplate restTemplate;

    public void makeTestCall() {
        restTemplate = new RestTemplate();
        String testUrl = "https://api.github.com/repos/signalapp/Signal-Server/commits?per_page=100";
        ResponseEntity<Commits[]> response = restTemplate.getForEntity(testUrl, Commits[].class);
        Commits[] commitArray = response.getBody();
        List<Commits> commitsList = Arrays.asList(commitArray);
//        System.out.println(commitArray.length + " " + commitsList.size());
//        System.out.println(response.getBody()[0].toString());

    }
}
