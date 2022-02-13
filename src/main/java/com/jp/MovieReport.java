package com.jp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;

import javax.swing.*;

public class MovieReport {

    private Status status;
    private JFrame parentFrame;

    public MovieReport(Status status, JFrame parentFrame) {
        this.status = status;
        this.parentFrame = parentFrame;
    }

    public List<Movie> runReport(File parent) {
        status.setTextStatus("Starting Search...");
        List<Movie> movies = new ArrayList<>();
        if(parent.isDirectory()) {
            assessChildren(parent, parent, movies);
        }
        status.setTotalNumberOfMovies(movies.size());
        status.setTextStatus("Found " + movies.size() + " movies");
        return movies;
    }

    public boolean createCSV(File location, List<Movie> movies) {
        status.setTextStatus("Creating CSV.");
        List<String> header = parseHeader(movies);
        status.setTextStatus("Header " + header.toString());

        List<List<String>> values = new ArrayList<>();

        status.setTextStatus("Creating csv lines");
        int count = 0;
        int total = movies.size();
        for(Movie movie : movies) {
           List<String> value = new ArrayList<>();
           value.add(movie.getName());
           value.add(Integer.toString(movie.getYear()));
           value.addAll(movie.getLocationFromRoot());
           values.add(value);
           count++;
           status.setTextStatus("Created " + count + " of " + total);
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
        status.setTextStatus("creating file " + location.getAbsolutePath() + " " + location.getParentFile().canWrite());

        for(List<String> value : values) {
            status.setTextStatus(value.toString());
        }

        FileWriter out = new FileWriter(location);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
            printer.printRecord(header);
            int count = 0;
            int total = values.size();
            status.setTextStatus("Printing " + total + " movies.");
            for(List<String> value : values) {
                printer.printRecord(value);
                count++;
                status.setTextStatus("Printed " + count + " of " + total);
            }
            status.setTextStatus("Done");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, ex.getMessage());
            return false;
        }
        return true;
    }



}
