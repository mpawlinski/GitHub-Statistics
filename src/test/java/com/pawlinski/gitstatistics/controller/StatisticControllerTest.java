package com.pawlinski.gitstatistics.controller;

import com.pawlinski.gitstatistics.model.UserStatistic;
import com.pawlinski.gitstatistics.service.UserStatisticService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticControllerTest {

    private static final int REPOSITORIES_COUNT = 3;
    private static final String LANGUAGE_NAME_3 = "python";
    private static final int LANGUAGE_3_BYTES = 9000;
    private final String LANGUAGE_NAME_1 = "java";
    private final String LANGUAGE_NAME_2 = "java";
    private final int LANGUAGE_2_BYTES = 2500;
    private final int LANGUAGE_1_BYTES = 4500;
    private final String TEST_USERNAME = "test";

    @InjectMocks
    StatisticController statisticController;

    @Mock
    UserStatisticService userStatisticService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(statisticController).build();
    }

    @Test
    public void getStatisticsTest() throws Exception {
        UserStatistic userStatistic = new UserStatistic(3);

        Map<String, Integer> map1 = new HashMap<>();
        map1.put(LANGUAGE_NAME_1, LANGUAGE_2_BYTES);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put(LANGUAGE_NAME_2, LANGUAGE_1_BYTES);

        Map<String, Integer> map3 = new HashMap<>();
        map3.put(LANGUAGE_NAME_3, LANGUAGE_3_BYTES);

        userStatistic.updateLanguages(map1);
        userStatistic.updateLanguages(map2);
        userStatistic.updateLanguages(map3);

        when(userStatisticService.getStatisticForUser(TEST_USERNAME)).thenReturn(userStatistic);

        mockMvc.perform(get("/statistics/" + TEST_USERNAME).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repositoriesCount", is(REPOSITORIES_COUNT)))
                .andExpect(jsonPath("$.languages", hasEntry(LANGUAGE_NAME_1, LANGUAGE_2_BYTES + LANGUAGE_1_BYTES)))
                .andExpect(jsonPath("$.languages", hasEntry(LANGUAGE_NAME_3, LANGUAGE_3_BYTES)));
    }
}
