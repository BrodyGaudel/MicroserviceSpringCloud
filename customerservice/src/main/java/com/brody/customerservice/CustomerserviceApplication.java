package com.brody.customerservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.brody.customerservice.entities.Customer;
import com.brody.customerservice.repository.CustomerRepository;

@SpringBootApplication
public class CustomerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerserviceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(
			CustomerRepository customerRepository, 
			RepositoryRestConfiguration repositoryRestConfiguration) {
		
		repositoryRestConfiguration.exposeIdsFor(Customer.class);
		return args -> {
			customerRepository.save(new Customer(null, "Brody", "contact@brody.com"));
			customerRepository.save(new Customer(null, "Gaudel", "gaudel@brody.com"));
			customerRepository.save(new Customer(null, "BrodyGaudel", "brodygaudel@brody.com"));
			customerRepository.findAll().forEach( c -> {
				System.out.println(c.toString());
			});
		};
		
	}

}
