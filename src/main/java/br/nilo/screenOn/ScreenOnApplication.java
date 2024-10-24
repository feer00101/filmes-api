package br.nilo.screenOn;

import br.nilo.screenOn.model.DataSeries;
import br.nilo.screenOn.service.ApiConsumer;
import br.nilo.screenOn.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenOnApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(ScreenOnApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiConsumer = new ApiConsumer();
		var json = apiConsumer.gainData("https://www.omdbapi.com/?t=gilmore+girls&Seasons&&apikey=2f79fc6a&");
		//System.out.println(json);
		ConvertData conversor = new ConvertData();
		DataSeries data = conversor.gainData(json, DataSeries.class);
	System.out.println(data);
	}
}
