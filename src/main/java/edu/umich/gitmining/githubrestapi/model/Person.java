package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Person {
    public String name;
    public String email;
    public String date;

    public Person() {
        // default constructor
    }
}
