package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedPerson {
    public String login;
    public long id;
    public String node_id;
    public String type;

    public DetailedPerson() {
        // default constructor
    }
}
