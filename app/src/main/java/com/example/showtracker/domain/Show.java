package com.example.showtracker.domain;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Show implements Serializable{
    private String title, producer;
    private int id, firstYear, lastYear, numberOfSeasons;
    private double rating;

    @NonNull
    @Override
    public String toString() {
        return "Show{"+
                "title='" + title + '\'' +
                ", producer='" + producer + '\'' +
                ", id=" + id + ", first year=" + firstYear +
                ", last year=" + lastYear +
                ", No seasons=" + numberOfSeasons +
                ", rating=" + rating + '}';
    }

    public Show(String title, String producer, int firstYear, int lastYear, int numberOfSeasons, double rating) {
        this.title = title;
        this.producer = producer;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
        this.numberOfSeasons = numberOfSeasons;
        this.rating = rating;
    }

    public Show(String title, String producer, int firstYear, int lastYear, int numberOfSeasons) {
        this.title = title;
        this.producer = producer;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
        this.numberOfSeasons = numberOfSeasons;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return this.title.equals(show.getTitle()) &&
                this.rating == show.getRating() &&
                this.producer.equals(show.getProducer()) &&
                this.firstYear == show.getFirstYear() &&
                this.lastYear == show.getLastYear() &&
                this.numberOfSeasons == show.getNumberOfSeasons();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title, producer, id, firstYear, lastYear, numberOfSeasons, rating);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    public int getLastYear() {
        return lastYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isSeen(){
        return rating != 0.0;
    }
}
