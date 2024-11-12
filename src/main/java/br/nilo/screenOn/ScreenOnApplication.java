package br.nilo.screenOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.nilo.screenOn.principal.Principal;
import br.nilo.screenOn.repository.SerieRepository;

@SpringBootApplication
public class ScreenOnApplication implements CommandLineRunner {

    @Autowired
    private SerieRepository repositorio;

    public static void main(String[] args) {

        SpringApplication.run(ScreenOnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Principal principal = new Principal(repositorio);
        principal.exibeMenu();

    }
}
