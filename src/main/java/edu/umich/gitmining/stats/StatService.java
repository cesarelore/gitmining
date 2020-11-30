package edu.umich.gitmining.stats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class StatService {

    private final RestTemplate restTemplate;

    public StatService(RestTemplateBuilder restTemplateBuilder, @Value("${github.username:defaultUsername}") String gitUsername, @Value("${github.token:defaultToken}") String gitToken ) {
        System.out.println("Github username for Rest Template: " + gitUsername);
        this.restTemplate = restTemplateBuilder
            .basicAuthentication(gitUsername, gitToken)
            .build();
    }

    public List<Repo> findCommits(String owner, String repo) {
        List<Repo> commits = new ArrayList<Repo>();
        int i=1;
        
        try {
            ResponseEntity<Repo []> response = restTemplate
                .getForEntity("https://api.github.com/repos/" + owner + "/" + repo + "/commits?per_page=100&page=" + i, Repo[].class);

            while (response.hasBody()) {
                commits.addAll(List.of(response.getBody()));
                i++;
                response = restTemplate
                    .getForEntity("https://api.github.com/repos/" + owner + "/" + repo + "/commits?per_page=100&page=" + i, Repo[].class);
            }
        }
        catch(Exception e) {}
                                
        return commits;
    }

    public List<Stats> getStats(List<Repo> repoData) {
        List<Commit> commits = repoData.stream().map(Repo::getCommit).collect(Collectors.toList());
        List<Author> authors = commits.stream().map(Commit::getAuthor).collect(Collectors.toList());

        Map<String, List<Author>> groupedCommits = authors.stream().collect(Collectors.groupingBy(Author::getName));
        
        List<Stats> stats = new ArrayList<Stats>();

        groupedCommits.forEach((k,v) -> stats.add(Stats.builder()
            .authorName(k)
            .commitCount(v.size())
            .firstCommit(v.stream().min(Comparator.comparing(Author::getDate)).get().getDate())
            .lastCommit(v.stream().max(Comparator.comparing(Author::getDate)).get().getDate())
            .build()));

        return stats;
    }
}
