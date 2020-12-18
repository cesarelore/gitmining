package edu.umich.gitmining.githubrestapi.controller;

import edu.umich.gitmining.githubrestapi.service.GitRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/csv")
public class CsvController {
    @Autowired
    GitRestService gitRestService;

    // http://localhost:8080/csv/createLocal

    @GetMapping("/createLocal")
    public void createLocalCsvFiles() {
        this.gitRestService.createCsv();
    }
}
