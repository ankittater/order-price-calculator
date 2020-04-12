package com.ankit.tavisca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

    private HashMap<String, BeverageDetails> items = new HashMap<>();
    private HashMap<String, Double> ingredients = new HashMap<>();

    private Pattern menuItemPattern =
            Pattern.compile("(.*)(:\\s)(\'.*\')(\\s[a-zA-Z]{1,}:\\s)([0-9]+[.]*[0-9]*)(.*)");

    private Pattern ingredientsPricePattern =
            Pattern.compile("([a-zA-Z]{1,})(:\\s)([0-9]+[.]*[0-9]*)(.*)");

    private Pattern orderPattern =
            Pattern.compile(Pattern.quote("'") + "(.*?)" + Pattern.quote("'"));

    /**
     * This method add menu item with ingredients to collection
     *
     * @param menuDetails string to add
     * @throws BeverageAppException On invalid entry
     */
    public void add(String menuDetails) {
        Matcher matcher = menuItemPattern.matcher(menuDetails);
        if (matcher.matches()) {
            String itemName = matcher.group(1).toLowerCase();
            String[] ingredients = matcher.group(3).trim().replaceAll("'", "").split(",");
            double price = Double.parseDouble(matcher.group(5));
            if (!items.containsKey(itemName)) {
                BeverageDetails beverageDetails = new BeverageDetails(price, Arrays.asList(ingredients));
                items.put(itemName, beverageDetails);
            } else {
                throw new BeverageAppException("items already exist");
            }
        } else {
            throw new BeverageAppException("invalid format");
        }
    }

    /**
     * This method add ingredients to collection along with its price
     *
     * @param ingredientsPriceDetail
     * @throws BeverageAppException On invalid entry
     */
    public void addIngredients(String ingredientsPriceDetail) {
        Matcher matcher = ingredientsPricePattern.matcher(ingredientsPriceDetail);
        if (matcher.matches()) {
            String name = matcher.group(1);
            String price = matcher.group(3);
            ingredients.put(name.toLowerCase(), Double.parseDouble(price));
        } else {
            throw new BeverageAppException("invalid format");
        }
    }

    /**
     * This method calculate the order bill
     *
     * @param orderString
     * @return bill amount
     * @throws BeverageAppException On invalid entry
     */
    public double calculateBill(String orderString) {
        Matcher matcher = orderPattern.matcher(orderString);
        List<OrderItems> orders = new ArrayList<>();
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            String[] orderDetails = matcher.group().replaceAll("'", "").replaceAll("-", "").split(",");
            orders.add(new OrderItems(orderDetails[0], Arrays.copyOfRange(orderDetails, 1, orderDetails.length)));
        }
        if (matchCount > 0) {
            return orders.stream().reduce(0.0, (amount, ob) -> amount + calculateOrderAmount(ob), Double::sum);
        } else {
            throw new BeverageAppException("invalid format");
        }
    }

    /**
     * This method calculate the order bill
     *
     * @param orderItems calculate bill amount of single order items
     * @return bill amount
     * @throws BeverageAppException when menu item not found in memory
     */
    private Double calculateOrderAmount(OrderItems orderItems) {
        double amount;
        BeverageDetails beverageDetails = items.get(orderItems.getName().toLowerCase());
        if (beverageDetails != null) {
            amount = beverageDetails.getPrice();
            if (orderItems.getExclusions().length == beverageDetails.getIngredients().size()) {
                throw new BeverageAppException("order item can not have all the Ingredients as exclusion");
            }
            for (String exclusion : orderItems.getExclusions()) {
                if (ingredients.containsKey(exclusion.toLowerCase())) {
                    amount = amount - ingredients.get(exclusion.toLowerCase());
                }
            }
        } else {
            throw new BeverageAppException("invalid bill, menu item not found in inventory");
        }
        return amount;
    }

}
