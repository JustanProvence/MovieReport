package com.jp;

import java.util.List;

public class Movie {

    private String name;
    private int year;
    private List<String> locationFromRoot;

    public Movie(String name, int year, List<String> locationFromRoot) {
        this.name = name;
        this.year = year;
        this.locationFromRoot = locationFromRoot;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<String> getLocationFromRoot() {
        return locationFromRoot;
    }

    @Override
    public String toString() {
        return "Report{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", locationFromRoot='" + locationFromRoot + '\'' +
                '}';
    }
}
