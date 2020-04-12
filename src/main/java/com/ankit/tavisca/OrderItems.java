package com.ankit.tavisca;

public class OrderItems {

    private String name;
    private String[] exclusions;

    public OrderItems(String name, String[] exclusions) {
        this.name = name;
        this.exclusions = exclusions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getExclusions() {
        return exclusions;
    }

    public void setExclusions(String[] exclusions) {
        this.exclusions = exclusions;
    }
}
