package br.nilo.screenOn.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episode {

    private Integer season;
    private String title;
    private Integer numeroEpisode;

    private Double rating;
    private LocalDate dataLancamento;

    public Episode(Integer numeroSeason, DataEpisiode dataEpisiode) {
        this.season = numeroSeason;
        this.title = dataEpisiode.title();
        this.numeroEpisode = dataEpisiode.numero();
        try {
            this.rating = Double.valueOf(dataEpisiode.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dataEpisiode.dateLaunch());
        } catch (DateTimeException e) {
            this.dataLancamento = null;
        }

    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumeroEpisode() {
        return numeroEpisode;
    }

    public void setNumeroEpisode(Integer numeroEpisode) {
        this.numeroEpisode = numeroEpisode;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return " |Season " + season + "| Title= " + title + "| NumeroEpisode= " + numeroEpisode + "| Rating= " + rating + "| DataLancamento= " + dataLancamento + System.lineSeparator();
    }

}
