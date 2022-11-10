package com.ob.ejercicios123.controllers;

import com.ob.ejercicios123.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Laptop[]> respose =
            testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, respose.getStatusCode());

        List<Laptop> laptops = Arrays.asList(respose.getBody());
        System.out.println(laptops.size());
    }

    @Test
    void findById() {
        ResponseEntity<Laptop> respose =
                testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, respose.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);             //Indicamos que vamos a enviar un json.
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));   //Indicamos que vamos a recibir un json

        String json = """
                {
                    "processor": "Ryzen 9 5900",
                    "ram": 16
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);   //Armamos la consulta

        ResponseEntity<Laptop> response =
                testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals(1L, result.getId());
    }

    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);             //Indicamos que vamos a enviar un json.
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));   //Indicamos que vamos a recibir un json

        String json = """
                {
                    "id": 1,
                    "processor": "Ryzen 9 5900",
                    "ram": 16
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);   //Armamos la consulta

        ResponseEntity<Laptop> response =
                testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, request, Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<Laptop> response =
                testRestTemplate.exchange("/api/laptops/1", HttpMethod.DELETE,request,Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteAll() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<Laptop> response =
                testRestTemplate.exchange("/api/laptops", HttpMethod.DELETE,request,Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}