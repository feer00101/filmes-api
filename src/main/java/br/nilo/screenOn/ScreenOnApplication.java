package br.nilo.screenOn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.nilo.screenOn.principal.Principal;

@SpringBootApplication
public class ScreenOnApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(ScreenOnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();

        //  season.forEach(System.out::println);
    }
}
