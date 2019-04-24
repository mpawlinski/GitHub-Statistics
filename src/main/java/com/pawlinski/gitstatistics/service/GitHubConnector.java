package com.pawlinski.gitstatistics.service;

import com.pawlinski.gitstatistics.exceptions.ServiceUnavailableException;
import com.pawlinski.gitstatistics.exceptions.UserNotFoundException;
import com.pawlinski.gitstatistics.model.RepositoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GitHubConnector {

    @Autowired
    private RestTemplate githubRestTemplate;

    private Logger log = LoggerFactory.getLogger(GitHubConnector.class);

    public List<RepositoryDTO> getRepositoryForUser(String username){

        ParameterizedTypeReference<List<RepositoryDTO>> parameterizedTypeReference = new ParameterizedTypeReference<List<RepositoryDTO>>(){};
        try {
            ResponseEntity<List<RepositoryDTO>> responseEntity = githubRestTemplate.exchange(
                    getUrlForRepositoryList(username),
                    HttpMethod.GET,
                    null,
                    parameterizedTypeReference);

            if (responseEntity.getBody() == null){
                return new ArrayList<>();
            }
            return responseEntity.getBody();

        } catch (HttpStatusCodeException ex){
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw UserNotFoundException.forName(username);
            }

            log.error("GitHub response with code {} for username {}", ex.getStatusCode(), username, ex);
            throw ServiceUnavailableException.standard();
        }
    }

    public Map<String, Integer> getLanguagesForRepository(String username, String repositoryName){
        return githubRestTemplate.getForObject(getUrlForLanguages(username, repositoryName), Map.class);
    }

    private String getUrlForRepositoryList(String username){
        return String.format("/users/%s/repos", username);
    }

    private String getUrlForLanguages(String username, String repositoryName){
        return String.format("/repos/%s/%s/languages", username, repositoryName);
    }
}
