package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Commits {
    public String sha;
    public String node_id;
    public Commit commit;
    public String url;
    public String html_url;
    public String comments_url;
    public DetailedPerson author;
    public DetailedPerson committer;

    public String[] toStringArray(String currentRepo) {
        return new String[]{currentRepo, sha==null?"":sha, author==null?"":author.login==null?"":author.login, committer==null?"":committer.login==null?"":committer.login};
    }

}

