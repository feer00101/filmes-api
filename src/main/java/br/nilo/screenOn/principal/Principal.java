package br.nilo.screenOn.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import br.nilo.screenOn.model.DataSeason;
import br.nilo.screenOn.model.DataSeries;
import br.nilo.screenOn.model.Serie;
import br.nilo.screenOn.repository.SerieRepository;
import br.nilo.screenOn.service.ApiConsumer;
import br.nilo.screenOn.service.ConvertData;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=2f79fc6a&";

    private List<DataSeries> dadosSerie = new ArrayList();
    private ApiConsumer apiConsumer = new ApiConsumer();
    private ConvertData conversor = new ConvertData();
    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar Series buscadas
                0 - Sair                                 
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSerisBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DataSeries dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSerie.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DataSeries getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = apiConsumer.gainData(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DataSeries dados = conversor.gainData(json, DataSeries.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        DataSeries dadosSerie = getDadosSerie();
        List<DataSeason> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalSeasons(); i++) {
            var json = apiConsumer.gainData(ENDERECO + dadosSerie.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dadosTemporada = conversor.gainData(json, DataSeason.class);
            temporadas.add(dadosTemporada);
        }

    }

    private void listarSerisBuscadas() {
        List<Serie> series = repositorio.findAll();

        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }
}
