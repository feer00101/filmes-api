package br.nilo.screenOn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer totalSeasons;

    private Double rating;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String atores;

    private String poster;

    private String sinopse;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodios = new ArrayList<>();

    public Serie() {

    }

    public Serie(DataSeries dataSeries) {

        this.title = dataSeries.title();
        this.totalSeasons = dataSeries.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(dataSeries.rating())).orElse(0);
        this.genero = Categoria.fromString(dataSeries.genero().split(",")[0].trim());
        this.atores = dataSeries.atores();
        this.poster = dataSeries.poster();
        this.sinopse = dataSeries.sinopse().trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public List<Episode> getEpisodes() {
        return episodios;
    }

    public void setEpisodios(List<Episode> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;

    }

    @Override
    public String toString() {
        return "| Title=" + title + "| TotalSeasons=" + totalSeasons + "| Rating= " + rating + "| Genero= " + genero
                + "Atores= " + atores + "| Poster= " + poster + "| Sinopse= " + sinopse + "Episodios" + episodios + System.lineSeparator();
    }

}
