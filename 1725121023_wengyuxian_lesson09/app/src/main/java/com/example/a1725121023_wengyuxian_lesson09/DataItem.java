package com.example.a1725121023_wengyuxian_lesson09;

public class DataItem {
    private String name;
    private boolean favorite;

    public DataItem(String name, boolean favorite) {
        this.name = name;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setName(String name) {
        this.name = name;
    }
}
