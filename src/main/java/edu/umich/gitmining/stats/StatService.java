package edu.umich.gitmining.stats;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
