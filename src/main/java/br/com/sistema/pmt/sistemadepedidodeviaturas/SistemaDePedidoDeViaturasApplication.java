package br.com.sistema.pmt.sistemadepedidodeviaturas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackages = "br.com.sistema.pmt.model")
@ComponentScan(basePackages = "br.com.sistema.pmt.*")
@EnableJpaRepositories(basePackages = "br.com.sistema.pmt.repository")
public class SistemaDePedidoDeViaturasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaDePedidoDeViaturasApplication.class, args);
        System.out.print(new BCryptPasswordEncoder().encode("4321"));
    }

}
