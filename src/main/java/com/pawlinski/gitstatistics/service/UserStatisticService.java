package com.pawlinski.gitstatistics.service;

import com.pawlinski.gitstatistics.model.RepositoryDTO;
import com.pawlinski.gitstatistics.model.UserStatistic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticService {

    private final GitHubConnector gitHubConnector;

    public UserStatisticService(GitHubConnector gitHubConnector) {
        this.gitHubConnector = gitHubConnector;
    }

    public UserStatistic getStatisticForUser(String username){

        List<RepositoryDTO> repositoryDTOS = gitHubConnector.getRepositoryForUser(username);
        UserStatistic userStatistic = new UserStatistic(repositoryDTOS.size());
        for (RepositoryDTO repositoryDTO : repositoryDTOS){
            userStatistic.updateLanguages(gitHubConnector.getLanguagesForRepository(username, repositoryDTO.getName()));
        }

        return userStatistic;
    }
}
