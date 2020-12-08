package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commits {
    public String sha;
    public String node_id;
    public Commit commit;
    public String url;
    public String html_url;
    public String comments_url;
    public DetailedPerson author;
    public DetailedPerson committer;


    public Commits() {
        // default constructor
    }
    public String toString()
    {
        return sha + "\n " + url + "\n";
    }
}
