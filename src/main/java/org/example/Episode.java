package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {
    private int id;
    private String name;
    private String air_date;
    private String episode;
    @JsonProperty("characters")
    private List<String> characterUrls;
    private String url;
    private String created;

    public List<String> getCharacterUrls() {
        return characterUrls;
    }

    public String getName() {
        return name;
    }
}
