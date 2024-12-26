package br.nilo.screenOn.model;

import java.time.DateTimeException;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodios")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private String title;
    private Integer numeroEpisode;

    private Double rating;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episode() {

    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
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
