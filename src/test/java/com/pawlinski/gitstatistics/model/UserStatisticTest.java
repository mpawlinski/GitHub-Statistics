package com.pawlinski.gitstatistics.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UserStatisticTest {

    private final String languageName1 = "java";
    private final String languageName2 = "java";
    private final String languageName3 = "python";
    private final int languageName1Bytes = 2500;
    private final int languageName2Bytes = 4500;
    private final int languageName3Bytes = 3000;
    private final int repositoriesCount = 3;

    Map<String, Integer> map1;
    Map<String, Integer> map2;
    Map<String, Integer> map3;

    UserStatistic userStatistic;

    @Before
    public void setUp() throws Exception {
        map1 = new HashMap<>();
        map1.put(languageName1, languageName1Bytes);

        map2 = new HashMap<>();
        map2.put(languageName2, languageName2Bytes);

        map3 = new HashMap<>();
        map3.put(languageName3, languageName3Bytes);

        userStatistic = new UserStatistic(repositoriesCount);
        userStatistic.updateLanguages(map1);
        userStatistic.updateLanguages(map2);
        userStatistic.updateLanguages(map3);
    }

    @Test
    public void testUpdateLanguages() throws Exception {

        assertEquals(Integer.valueOf(3), userStatistic.getRepositoriesCount());
        assertEquals(Integer.valueOf(languageName1Bytes + languageName2Bytes), userStatistic.getLanguages().get(languageName1));
//        assertEquals(Integer.valueOf(7000), userStatistic.getLanguages().get(languageName2));
        assertEquals(Integer.valueOf(3000), userStatistic.getLanguages().get(languageName3));
    }

}
