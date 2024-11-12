package br.nilo.screenOn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import br.nilo.screenOn.service.ConsultaGpt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private Integer totalSeasons;

    private Double rating;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String atores;

    private String poster;

    private String sinopse;

    @Transient
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

    @Override
    public String toString() {
        return "| Title=" + title + "| TotalSeasons=" + totalSeasons + "| Rating= " + rating + "| Genero= " + genero
                + "Atores= " + atores + "| Poster= " + poster + "| Sinopse= " + sinopse + System.lineSeparator();
    }

}
