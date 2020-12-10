package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Commit {
    public Person author;
    public Person committer;
    public String message;
    public Map<String, String> tree;
    public String url;
    public long comment_count;
    public Object verification;
}
