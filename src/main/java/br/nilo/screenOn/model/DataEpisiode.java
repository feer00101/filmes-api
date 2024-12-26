package br.nilo.screenOn.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisiode(
        @JsonAlias("Title")
        String title,
        @JsonAlias("Episode")
        Integer numero,
        @JsonAlias("Released")
        String dateLaunch,
        @JsonAlias("imdbRating")
        String rating) {

}
