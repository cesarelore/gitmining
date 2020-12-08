package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    public Person author;
    public Person committer;
    public String message;
    public Map<String, String> tree;
    public String url;
    public long comment_count;
    public Object verification;

    public Commit() {
        // default constructor
    }

}
