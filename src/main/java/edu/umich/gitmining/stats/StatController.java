package edu.umich.gitmining.stats;

import java.util.List;

import edu.umich.gitmining.githubrestapi.service.GitRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stats")
public class StatController {
    private StatService statService;

    @Autowired
    GitRestService gitRestService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/{owner}/{repo}")
    public List<Stats> getStats(@PathVariable String owner, @PathVariable String repo) {
        List<Repo> repoData = this.statService.findCommits(owner, repo);

        return this.statService.getStats(repoData);
    }
}
