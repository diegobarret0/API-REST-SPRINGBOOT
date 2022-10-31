package com.ob.ejercicios123;

/**
 * Swagger http://localhost:8080/swagger-ui/index.html#/
 */

import com.ob.ejercicios123.entities.Laptop;
import com.ob.ejercicios123.repositories.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Ejercicios123Application {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(Ejercicios123Application.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		repository.save(new Laptop("Ryzen 5 3600", 16));
		repository.save(new Laptop("Intel I5 8500", 8));

		System.out.println(repository.findAll());
	}

}
