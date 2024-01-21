package com.gamingreservation;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GamingReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamingReservationSystemApplication.class, args);
		System.out.println("Application started !!!!!!!!!");
	}
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	

}
