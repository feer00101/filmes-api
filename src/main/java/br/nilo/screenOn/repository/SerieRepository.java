package br.nilo.screenOn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.nilo.screenOn.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

}
