package com.jp;

import java.util.ArrayList;
import java.util.List;

public class Status {

    private int totalNumberOfMovies = 0;
    private String textStatus = "...";
    private List<IStatusListener> listeners = new ArrayList<>();

    public Status() {

    }

    public void addListener(IStatusListener listener) {
        listeners.add(listener);
    }

    public int getTotalNumberOfMovies() {
        return totalNumberOfMovies;
    }

    public void setTotalNumberOfMovies(int totalNumberOfMovies) {
        this.totalNumberOfMovies = totalNumberOfMovies;
        updateListeners();
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
        updateListeners();
    }

    private void updateListeners() {
        for(IStatusListener listener : listeners) {
            listener.statusUpdated(this.totalNumberOfMovies , this.textStatus);
        }
    }
}
