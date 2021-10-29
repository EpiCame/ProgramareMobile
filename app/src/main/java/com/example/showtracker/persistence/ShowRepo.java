package com.example.showtracker.persistence;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.showtracker.domain.Show;

import java.util.ArrayList;
import java.util.List;

public class ShowRepo {
    public static List<Show> shows = new ArrayList<>();
    private static int id = 0;
    public static List<Show> createShows(){
        List<Show> shows = new ArrayList<>();
        Show show;
        show = new Show("The Wire", "HBO", 2002, 2008, 5, 9);
        addShow(show);
        show = new Show("House of Cards", "Netflix", 2013,2018, 6, 7.5);
        addShow(show);
        show = new Show("Lost", "ABC", 2004, 2010, 6, 9.3);
        addShow(show);
        show = new Show("Archer", "FXX", 2009, 2020, 12, 7);
        addShow(show);
        return shows;
    }

    public static void addShow(Show show) {
        show.setId(id);
        shows.add(show);
        id++;
    }

    public static void updateShow(Show show){
        Show showToUpdate = getShowById(show.getId());
        showToUpdate.setTitle(show.getTitle());
        showToUpdate.setProducer(show.getProducer());
        showToUpdate.setFirstYear(show.getFirstYear());
        showToUpdate.setLastYear(show.getLastYear());
        showToUpdate.setNumberOfSeasons(show.getNumberOfSeasons());
        showToUpdate.setRating(show.getRating());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void deleteShowById(int id){
        shows.removeIf(show -> show.getId() == id);
    }

    public static Show getShowById(int id){
        for(Show show : shows)
            if(show.getId() == id)
                return show;
        return null;
    }
}
