package com.backend.agendamentotarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgendamentoTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendamentoTarefasApplication.class, args);
	}

}
