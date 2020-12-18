package edu.umich.gitmining.githubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class StatsContributors {
    private Long total;
    private Week[] weeks;
    private DetailedPerson author;

    public List<String[]> prepareForCsv(String repoName) {
        List<String[]> tempList = new ArrayList();
        for(int i = 0;i<weeks.length;i++) {
            tempList.add(new String[]{
                    repoName,
                    author==null?"":author.login==null?"":author.login,
                    String.valueOf(total),
                    String.valueOf(weeks[i].getW()),
                    String.valueOf(weeks[i].getA()),
                    String.valueOf(weeks[i].getD()),
                    String.valueOf(weeks[i].getC()),
            });
        }
        return tempList;
    }
}
