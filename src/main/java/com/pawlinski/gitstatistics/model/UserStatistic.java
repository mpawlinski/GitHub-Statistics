package com.pawlinski.gitstatistics.model;

import java.util.HashMap;
import java.util.Map;

public class UserStatistic {

    private Integer repositoriesCount;
    private Map<String, Integer> languages;

    public UserStatistic(Integer repositoriesCount) {
        this.repositoriesCount = repositoriesCount;
        this.languages = new HashMap<>();
    }

    public Integer getRepositoriesCount() {
        return repositoriesCount;
    }

    public Map<String, Integer> getLanguages() {
        return languages;
    }

    public void updateLanguages(Map<String, Integer> update){
        if (update == null){
            return;
        }
        for (Map.Entry<String, Integer> entry : update.entrySet()){
            String updateKey = entry.getKey();
            if (languages.containsKey(updateKey)){
                languages.put(updateKey, languages.get(updateKey) + entry.getValue());
            } else {
                languages.put(updateKey, entry.getValue());
            }
        }
    }
}
