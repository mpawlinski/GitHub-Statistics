package com.pawlinski.gitstatistics.controller;

import com.pawlinski.gitstatistics.model.UserStatistic;
import com.pawlinski.gitstatistics.service.UserStatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    private final UserStatisticService userStatisticService;

    public StatisticController(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    @GetMapping("/statistics/{user}")
    public UserStatistic getStatistics(@PathVariable("user") String user){
        return userStatisticService.getStatisticForUser(user);
    }
}
