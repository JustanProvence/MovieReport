package com.jp;

import java.util.ArrayList;
import java.util.List;

public class Status {
    private int totalNumberOfMovies = 0;
    private int progress = 0;
    private final List<IStatusListener> listeners = new ArrayList<>();

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
        for(IStatusListener listener : listeners) {
            listener.maxUpdated(totalNumberOfMovies);
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        for(IStatusListener listener : listeners) {
            listener.progressUpdated(progress);
        }
    }

    private void updateListeners() {

    }
}
