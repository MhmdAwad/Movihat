package com.mhmdawad.movihat;

public class Movie {

    private String movie_name;
    private String movie_cover;
    private String movie_link;


    public Movie(String movieName, String movieCover, String movieLink) {
        this.movie_name = movieName;
        this.movie_cover = movieCover;
        this.movie_link = movieLink;
    }

    public Movie() {
    }

    public String getMovie_name() {
        return movie_name;
    }

    public String getMovie_cover() {
        return movie_cover;
    }

    public String getMovie_link() {
        return movie_link;
    }
}
