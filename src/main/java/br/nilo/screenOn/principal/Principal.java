package br.nilo.screenOn.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.nilo.screenOn.model.Categoria;
import br.nilo.screenOn.model.DataSeason;
import br.nilo.screenOn.model.DataSeries;
import br.nilo.screenOn.model.Episode;
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
    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBuscada;

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
                4 - Buscar Serie por titulo
                5 - Buscar por ator
                6 - Top 5 series
                7 - Buscar séries por categoria
                8 - Filtrar séries 
                9 - Busca por trecho
                10 - Top 10 episodios por serie
                11 - Busca Episodios por Data
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
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscaSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    searchSerieForCategory();
                    break;
                case 8:
                    filterSerieForSeasonAndRating();
                    break;
                case 9:
                    buscarEpisodioPortrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodioDepoisDeUmaData();
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
        System.out.println("Escolha uma Serie pelo nome: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serie = repositorio.findByTitleContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DataSeason> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalSeasons(); i++) {
                var json = apiConsumer.gainData(ENDERECO + serieEncontrada.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeason dadosTemporada = conversor.gainData(json, DataSeason.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episode> episodios = temporadas.stream()
                    .flatMap(d -> d.episodes().stream()
                    .map(e -> new Episode(d.numero(), e))).collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("");
        }
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

    private void buscaSeriePorTitulo() {
        System.out.println("Escolha uma Serie pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBuscada = repositorio.findByTitleContainingIgnoreCase(nomeSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("Dados da Serie" + serieBuscada.get());
        } else {
            System.out.println("Serie não encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Qual o nome do ator?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de qual valor?");
        var avaliaçao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndRatingGreaterThanEqual(nomeAtor, avaliaçao);
        System.out.println("Series em que " + nomeAtor + " atuou");
        seriesEncontradas.forEach(s -> System.err.println(s.getTitle() + " avaliaçao " + s.getRating()));

    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repositorio.findTop5ByOrderByRatingDesc();
        serieTop.forEach(s -> System.out.println(s.getTitle() + " avaliaçao " + s.getRating()));
    }

    private void searchSerieForCategory() {
        System.out.println("Qual categoria deseja buscar? ");
        var tipoCategoria = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(tipoCategoria);
        List<Serie> seriePorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Serie da Categoria: " + tipoCategoria);
        seriePorCategoria.forEach(System.out::println);
    }

    public void filterSerieForSeasonAndRating() {

        System.out.println("Filtrar Series ate quantas temporadas?");
        var totalSeasons = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliacao a partir de que valor?");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> filtroSeries = repositorio.serieTemporadaPorAvaliacao(totalSeasons, avaliacao);
        System.out.println("***Series Filtradas***");
        filtroSeries.forEach(s -> System.out.println(s.getTitle() + "avaliacao " + s.getRating()));

    }

    private void buscarEpisodioPortrecho() {
        System.out.println("Qual o nome do Episodio para Busca:");
        var trechoEpisodio = leitura.nextLine();
        List<Episode> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s Temporadas %s - Episodio: %s - %s\n",
                e.getSerie().getTitle(), e.getSeason(), e.getNumeroEpisode(), e.getTitle()));
    }

    private void topEpisodiosPorSerie() {
        buscaSeriePorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episode> topEpisodios = repositorio.topEpisoodiosporSerie(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada %s - Episodio %s - %s Avaliacao %s\n",
                    e.getSerie().getTitle(), e.getSeason(), e.getNumeroEpisode(), e.getTitle(), e.getRating()));
        }
    }

    private void buscarEpisodioDepoisDeUmaData() {
        buscaSeriePorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            System.out.println("Digite o ano limite de lancamento: ");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();
            List<Episode> episodioAno = repositorio.episodiosPorSerieAno(serie, anoLancamento);
            episodioAno.forEach(System.out::println);
        }
    }

}
