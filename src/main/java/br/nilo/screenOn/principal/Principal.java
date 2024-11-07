package br.nilo.screenOn.principal;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.nilo.screenOn.model.DataEpisiode;
import br.nilo.screenOn.model.DataSeason;
import br.nilo.screenOn.model.DataSeries;
import br.nilo.screenOn.model.Episode;
import br.nilo.screenOn.service.ApiConsumer;
import br.nilo.screenOn.service.ConvertData;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=2f79fc6a&";

    private ApiConsumer apiConsumer = new ApiConsumer();
    private ConvertData conversor = new ConvertData();

    public void exibeMenu() {

        System.out.println("Digite o nome da Serie para busca");
        var nomeSerie = leitura.nextLine();
        var json = apiConsumer.gainData(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DataSeries data = conversor.gainData(json, DataSeries.class);
        System.out.println(data);

        List<DataSeason> season = new ArrayList<>();

        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = apiConsumer.gainData(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = conversor.gainData(json, DataSeason.class);
            season.add(dataSeason);
        }
        /*
        for (int i = 0; i < data.totalSeasons(); i++) {
            List<DataEpisiode> episodiosTemporada = season.get(i).episodes();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).title());
            }
         */
        season.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));
        List<DataEpisiode> dadosEpisiodes = season.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

        /*         
        System.out.println("Top 5 episodios");
        dadosEpisiodes.stream().
                filter(e -> !e.rating().equalsIgnoreCase("N/A")).
                peek(e -> System.out.println("Primeiro filtro(N/A) " + e)).
                sorted(Comparator.comparing(DataEpisiode::rating).reversed()).
                peek(e -> System.err.println("Ordenação " + e)).
                limit(5).
                peek(e -> System.out.println("Limite " + e)).
                map(e -> e.title().toUpperCase()).
                peek(e -> System.err.println("Mapeamentoo " + e)).
                forEach(System.out::println);
         */
        List<Episode> epList = season.stream()
                .flatMap(t -> t.episodes().stream()
                .map(d -> new Episode(t.season(), d)))
                .collect(Collectors.toList());
        epList.forEach(System.out::println);

        /* System.out.println("Digite um trecho do titulo do episodio");
        var trechoTitle = leitura.nextLine();

        Optional<Episode> episodeSearch = epList.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(trechoTitle.toUpperCase()))
                .findFirst();

 

        if (episodeSearch.isPresent()) {
            System.err.println("Episodio Encontrado");
            System.out.println("Temporada" + episodeSearch.get().getSeason());
        } else {
            System.out.println("Episodio não encontrado");
        }
        
        System.out.println("A partir de qual ano você deseja ver?");
        var ano = leitura.nextInt();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        epList.stream()
                .filter(e -> e.getNumeroEpisode() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(" |Temporada: " + e.getSeason()
                + " |Nome episodio: " + e.getTitle()
                 + " |Data Lancamento: " + e.getDataLancamento().format(dtf)));
         */
        Map<Integer, Double> ratingPerSeason = epList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason, Collectors.averagingDouble(Episode::getRating)));
        System.out.println(ratingPerSeason);

        DoubleSummaryStatistics est = epList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("Media" + est.getAverage() + System.lineSeparator() + "Melhor Episodio:" + est.getMax() + System.lineSeparator() + "Pior episodio: " + est.getMin() + System.lineSeparator() + "Quantidade: " + est.getCount());
    }
}
