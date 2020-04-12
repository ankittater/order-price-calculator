package com.ankit.tavisca;

import java.util.List;

public class BeverageDetails {

    private Double price;

    private List<String> ingredients;

    public BeverageDetails(Double price, List<String> ingredients) {
        this.price = price;
        this.ingredients = ingredients;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
