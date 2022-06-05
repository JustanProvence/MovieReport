package com.jp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;

public class MovieReport {

    private Status status;

    public MovieReport(Status status) {
        this.status = status;
    }

    public List<Movie> runReport(File parent) {
        List<Movie> movies = new ArrayList<>();
        if(parent.isDirectory()) {
            assessChildren(parent, parent, movies);
        }
        status.setTotalNumberOfMovies(movies.size());
        return movies;
    }

    public boolean createCSV(File location, List<Movie> movies) {
        List<String> header = parseHeader(movies);
        List<List<String>> values = new ArrayList<>();

        for(Movie movie : movies) {
           List<String> value = new ArrayList<>();
           value.add(movie.getName());
           value.add(Integer.toString(movie.getYear()));
           value.addAll(movie.getLocationFromRoot());
           values.add(value);
        }

        try {
            File newFile = new File(location.getParent(), "movies.csv");
           return createCSVFile(newFile, header, values);
        } catch (IOException e) {
            return false;
        }
    }

    private void assessChildren(File root, File directory, List<Movie> movies) {
        File[] dirs = directory.listFiles();
        if(dirs == null) {
            return;
        }
        for(File dir : dirs) {
            if(dir.isDirectory()) {
                if(isMovie(dir)) {
                    movies.add(Movies.parseReport(root, dir));
                }
                assessChildren(root, dir, movies);
            }
        }
    }

    private boolean isMovie(File file) {
        String name = file.getName();
        return name.contains("(") && name.contains(")");
    }

    private List<String> parseHeader(List<Movie> movies) {
        List<String> header = new ArrayList<>();
        header.add("Name");
        header.add("Year");
        header.add("Root");

        int longest = 0;
        for(Movie movie : movies) {
            if(longest < movie.getLocationFromRoot().size()) {
                longest = movie.getLocationFromRoot().size();
            }
        }

        for(int i = 1; i<longest; i++) {
            header.add(String.format("Path%s", i));
        }

        return header;
    }

    private boolean createCSVFile(File location, List<String> header, List<List<String>> values) throws IOException {
        FileWriter out = new FileWriter(location);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
            printer.printRecord(header);
            int count = 0;
            for(List<String> value : values) {
                printer.printRecord(value);
                count++;
                status.setProgress(count);
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }



}
