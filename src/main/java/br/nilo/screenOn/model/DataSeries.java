package br.nilo.screenOn.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(
        @JsonAlias("Title")
        String title,
        @JsonAlias("totalSeasons")
        Integer totalSeasons,
        @JsonAlias("imdbRating")
        String rating,
        @JsonAlias("Genre")
        String genero,
        @JsonAlias("Actors")
        String atores,
        @JsonAlias("Poster")
        String poster,
        @JsonAlias("Plot")
        String sinopse) {

}
