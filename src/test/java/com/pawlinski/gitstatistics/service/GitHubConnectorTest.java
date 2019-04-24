package com.pawlinski.gitstatistics.service;

import com.pawlinski.gitstatistics.exceptions.ServiceUnavailableException;
import com.pawlinski.gitstatistics.exceptions.UserNotFoundException;
import com.pawlinski.gitstatistics.model.RepositoryDTO;
import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GitHubConnectorTest {

    @TestConfiguration
    static class GitHubConnectorTestConfiguration{

        @Bean
        public GitHubConnector gitHubConnector(){
            return new GitHubConnector();
        }
    }

    @Autowired
    private GitHubConnector gitHubConnector;

    @MockBean
    private RestTemplate githubRestTemplate;

    @Test(expected = UserNotFoundException.class)
    public void testRepositoryThrowExceptionWhenRestTemplateThrowNotFound() {

        when(githubRestTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        String randomUsername = RandomString.make();
        gitHubConnector.getRepositoryForUser(randomUsername);
    }

    @Test(expected = ServiceUnavailableException.class)
    public void testRepositoryThrowExceptionWhenRestTemplateThrowException() {

        when(githubRestTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY));

        String randomUsername = RandomString.make();
        gitHubConnector.getRepositoryForUser(randomUsername);
    }

    @Test
    public void testGetRepositoryForUserReturnCorrectData() {

        List<RepositoryDTO> mockList = prepareMockRepositoryDTOList();
        ResponseEntity responseEntity = new ResponseEntity(mockList, HttpStatus.OK);

        when(githubRestTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        String randomUsername = RandomString.make();
        List<RepositoryDTO> response = gitHubConnector.getRepositoryForUser(randomUsername);
        assertEquals(mockList, response);
    }

    private List<RepositoryDTO> prepareMockRepositoryDTOList() {

        RepositoryDTO mock1 = new RepositoryDTO("test");
        RepositoryDTO mock2 = new RepositoryDTO("test2");
        RepositoryDTO mock3 = new RepositoryDTO("test3");

        return Arrays.asList(mock1, mock2, mock3);
    }
}