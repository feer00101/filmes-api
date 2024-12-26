package br.nilo.screenOn.dto;

import br.nilo.screenOn.model.Categoria;

public record SerieDTO(Long id, String title, Integer totalSeasons, Double rating, Categoria genero, String atores, String poster, String sinopse) {

}
