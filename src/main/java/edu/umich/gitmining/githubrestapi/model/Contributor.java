package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contributor {
    public String login;
    public long id;
    public String node_id;
    public String type;
    public long contributions;

    public String[] toStringArray() {
        return new String[] {login, String.valueOf(contributions)};
    }
}
