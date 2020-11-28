package edu.umich.gitmining.stats;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Stats {
    String authorName;
    Integer commitCount;
    Date firstCommit;
    Date lastCommit;
}
