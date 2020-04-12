package com.ankit.tavisca;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MenuTest {

    @Test(expected = BeverageAppException.class)
    public void addInvalidMenuItem() {
        Menu menu = new Menu();
        menu.add("Coffee: 'Coffee,milk,sugar,water Price: 5$");
    }

    @Test(expected = BeverageAppException.class)
    public void addInvalidIngredients() {
        Menu menu = new Menu();
        menu.addIngredients("milk 1 $");
    }

    @Test(expected = BeverageAppException.class)
    public void invalidOrderString() {
        Menu menu = new Menu();
        menu.calculateBill("Chai,-sugar");
    }

    @Test()
    public void addMenuItem() {
        Menu menu = new Menu();
        menu.add("Coffee: 'Coffee,milk,sugar,water' Price: 5$");

    }


    @Test
    public void addIngredients() {
        Menu menu = new Menu();
        menu.addIngredients("milk: 1 $");
    }

    @Test
    public void calculateBill() {
        Menu menu = new Menu();

        //Add Items
        menu.add("Coffee: 'Coffee,milk,sugar,water' Price: 5$");
        menu.add("Chai: 'Tea, milk, sugar, water' Price: 4$");
        menu.add("Banana Smoothie: 'banana, milk, sugar, water' Price: 6$");
        menu.add("Strawberry Shake: 'Strawberries, sugar, milk, water' Price: 7$");
        menu.add("Mojito: 'Lemon, sugar, water, soda, mint' Price: 7.5$");

        // Add Ingredients
        menu.addIngredients("milk: 1 $");
        menu.addIngredients("sugar: 0.5 $");
        menu.addIngredients("Soda: 0.5 $");
        menu.addIngredients("mint: 0.5 $");
        menu.addIngredients("water: 0.5 $");

        assertEquals(3.5, menu.calculateBill("'Chai,-sugar'"), 0);
        assertEquals(3.5, menu.calculateBill("'Chai,-sugar'"), 0);
    }
}