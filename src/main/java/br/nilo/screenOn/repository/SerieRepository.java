package br.nilo.screenOn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.nilo.screenOn.model.Categoria;
import br.nilo.screenOn.model.Episode;
import br.nilo.screenOn.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTitleContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndRatingGreaterThanEqual(String nomeAtor, Double rating);

    List<Serie> findTop5ByOrderByRatingDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();

    List<Serie> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(int totalSeasons, double rating);

    @Query("select s from Serie s WHERE s.totalSeasons <= :totalSeasons AND s.rating >= :rating")
    List<Serie> serieTemporadaPorAvaliacao(int totalSeasons, double rating);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.title ILIKE %:trechoEpisodio%")
    public List<Episode> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
    public List<Episode> topEpisoodiosporSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    public List<Episode> episodiosPorSerieAno(Serie serie, int anoLancamento);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.season = :numero")
    List<Episode> obterTemporadaPorNumero(Long id, Long numero);
}
