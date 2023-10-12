package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty("results")
    private List<Episode> episodes;

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
