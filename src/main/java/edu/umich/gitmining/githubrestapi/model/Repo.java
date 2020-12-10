package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Repo {
    private long id;
    private String node_id;
    private String name;
    private String full_name;
    private DetailedPerson owner;
    private String description;
    private String url;
    private String collaborators_url;
    private long size;
    private long stargazers_count;
    private long watchers_count;

    public String getName() {
        return name;
    }

    public long getStargazers_count() {
        return stargazers_count;
    }

    public long getWatchers_count() {
        return watchers_count;
    }

    public DetailedPerson getOwner() {
        return owner;
    }

    public long getId() {
        return id;
    }
}
