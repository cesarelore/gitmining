package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
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

    public String toString() {
        return "Commit(URL: " + url + ", author: " + author.name +", committer: " + committer.name==null?"":committer.name + ").";
    }
}
