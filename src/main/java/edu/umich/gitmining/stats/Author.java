package edu.umich.gitmining.stats;

import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Author {
    private String name;
    private String email;
    private Date date;
}
