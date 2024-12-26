package br.nilo.screenOn.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.nilo.screenOn.dto.EpisodeDTO;
import br.nilo.screenOn.dto.SerieDTO;
import br.nilo.screenOn.model.Categoria;
import br.nilo.screenOn.model.Serie;
import br.nilo.screenOn.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repository.findAll());

    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repository.findTop5ByOrderByRatingDesc());

    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRating(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitle(), s.getTotalSeasons(), s.getRating(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }

        return null;
    }

    public List<EpisodeDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodes().stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getNumeroEpisode(), e.getTitle()))
                    .collect(Collectors.toList());
        }

        return null;
    }

    public List<EpisodeDTO> obterTemporadaPorNumero(Long id, Long numero) {
        return repository.obterTemporadaPorNumero(id, numero)
                .stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getNumeroEpisode(), e.getTitle()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriePorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDados(repository.findByGenero(categoria));
    }
}
