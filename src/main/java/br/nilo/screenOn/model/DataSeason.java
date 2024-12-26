package br.nilo.screenOn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(@JsonAlias("Season")
        Integer numero,
        @JsonAlias("Episodes")
        List<DataEpisiode> episodes) {

}
