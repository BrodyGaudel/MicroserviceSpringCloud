package com.brody.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.brody.inventoryservice.entities.Product;
import com.brody.inventoryservice.repository.ProductRepository;

@SpringBootApplication
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(
			ProductRepository productRepository,
			RepositoryRestConfiguration repositoryRestConfiguration) {
		
		repositoryRestConfiguration.exposeIdsFor(Product.class);
		return args -> {
			productRepository.save(new Product(null, "Voiture", 120000.0, 123));
			productRepository.save(new Product(null, "Maison S+3", 170000.0, 456));
			productRepository.save(new Product(null, "Smarthphone", 985.0, 789));
			productRepository.findAll().forEach( p -> {
				System.out.println(p.toString());
			});
		};
	}

}
