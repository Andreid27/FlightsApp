package org.company.Models;

public class Airplane {
    private int id;
    private String Brand;
    private String Model;
    private int max_capacity;

    public Airplane(int id, String brand, String model, int max_capacity) {
        this.id = id;
        Brand = brand;
        Model = model;
        this.max_capacity = max_capacity;
    }

    public Airplane(String brand, String model) {
        Brand = brand;
        Model = model;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return Brand;
    }

    public String getModel() {
        return Model;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\":" + id +
                ", \"Brand\": \"" + Brand + "\"" +
                ", \"Model\": \"" + Model + "\"" +
                ", \"max_capacity\":" + max_capacity +
                "}";
    }
}
