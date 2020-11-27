package edu.umich.gitmining.stats;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatService {

    public List<Repo> findCommits(String owner, String repo) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Repo []> response = restTemplate.getForEntity(
					"https://api.github.com/repos/" + owner + "/" + repo + "/commits", Repo[].class);
            Repo[] repos = response.getBody();
            
            return List.of(repos);
    }
}
