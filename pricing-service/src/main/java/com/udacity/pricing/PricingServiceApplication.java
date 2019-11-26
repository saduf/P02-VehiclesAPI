package com.udacity.pricing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/**
 * Creates a Spring Boot Application to run the Pricing Service.
 * TODO: Convert the application from a REST API to a microservice.
 */

@SpringBootApplication
@EnableEurekaClient
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(PriceRepository priceRepository){
        return args -> {
            // read JSON and load json
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Price>> typeReference = new TypeReference<List<Price>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/prices.json");
            try {
                List<Price> prices = mapper.readValue(inputStream,typeReference);
                priceRepository.saveAll(prices);
                System.out.println("Prices Saved!");
            } catch (IOException e){
                System.out.println("Unable to save prices: " + e.getMessage());
            }
        };
    }
}
