package com.ob.ejercicios123.controllers;

import com.ob.ejercicios123.entities.Laptop;
import com.ob.ejercicios123.repositories.LaptopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    private final LaptopRepository repository;

    public LaptopController(LaptopRepository repository) {
        this.repository = repository;
    }

    /**
     * Buscar todas las laptops en base de datos.
     * @return List of Laptops
     */
    @GetMapping("/api/laptops")
    public  List<Laptop> findAll() {
        return repository.findAll();
    }

    /**
     * Buscar una laptop en base de datos por su id
     * @param id
     * @return Laptop
     */
    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findById(@PathVariable Long id) {
        Optional<Laptop> laptopOpt = repository.findById(id);
        return laptopOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crear una nueva laptop en base de datos
     * @param laptop
     * @return Laptop created
     */
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop) {
        if(laptop.getId() != null)
            return ResponseEntity.badRequest().build();
        Laptop result = repository.save(laptop);
        return ResponseEntity.ok(result);
    }

    /**
     *Actualizar una laptop que ya existe en base de datos
     * @param laptop
     * @return Laptop actualized
     */
    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){

        if (laptop.getId() == null)                         //Si no tiene id quiere decir que es una creacion
            return ResponseEntity.badRequest().build();
        if(!repository.existsById(laptop.getId()))          //Si la laptop no existe no es una actualizacion
            return ResponseEntity.notFound().build();

        Laptop result = repository.save(laptop);
        return ResponseEntity.ok(result);
    }

    /**
     * Borrar una laptop que ya existe en base de datos
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        if(!repository.existsById(id))
            return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Borrar todos los registros de laptops en la base de datos.
     * @return ResponseEntity
     */
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}