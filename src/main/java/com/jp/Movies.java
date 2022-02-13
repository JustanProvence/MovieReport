package com.jp;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Movies {

    public static Movie parseReport(File root, File movieDir) {
       return new Movie(parseName(movieDir), parseYear(movieDir), parseLocation(root, movieDir));
    }

    private static String parseName(File file) {
         return file.getName().substring(0, file.getName().indexOf("(")-1);
    }

    private static int parseYear(File file) {
        String yearStr = file.getName().substring(file.getName().indexOf("("));
        yearStr = yearStr.substring(1, yearStr.indexOf(")"));
        if(isInt(yearStr)) {
            return Integer.parseInt(yearStr);
        }
        return 1;
    }

    private static List<String> parseLocation(File root, File file) {
        String partialPath = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(root.getName()));
        return Arrays.asList(partialPath.split("\\\\"));
    }

    private static boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch(NumberFormatException nfe) {
            return false;
        }
    }

}
