package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private long network_count;
    private long subscribers_count;

    public String[] toStringArray() {
        return new String[]{String.valueOf(id), name, String.valueOf(size), String.valueOf(watchers_count), String.valueOf(network_count), String.valueOf(subscribers_count)};
    }

    public String getName() {
        return this.name;
    }

    public String getWatchers_count() {
        return String.valueOf(this.watchers_count);
    }
}
