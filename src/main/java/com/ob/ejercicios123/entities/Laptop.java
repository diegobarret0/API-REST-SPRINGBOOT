package com.ob.ejercicios123.entities;

import javax.persistence.*;

@Entity
@Table(name = "Laptops")
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String processor;
    private int ram;

    public Laptop() {
    }

    public Laptop(String processor, int ram) {
        this.processor = processor;
        this.ram = ram;
    }

    public Long getId() {
        return this.id;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", processor='" + processor + '\'' +
                ", ram=" + ram +
                '}';
    }
}
