package com.ternerwill.resourceconnector.bean;

import lombok.Data;

@Data
public class Video {
    private String name;
    private String posterUrl;
    private String type;
    private Integer id;

    private Video(String name,String posterUrl,String type,Integer id){
        this.name = name;
        this.posterUrl = posterUrl;
        this.type = type;
        this.id = id;
    }

    public static Video movie( Movie movie){
        return new Video(movie.getMovieName(),movie.getPosterRequestURL(),"movie",movie.getMovieId());
    }

    public static Video season(Season season){
        return new Video(season.getSeasonName(),season.getCoverBaseURL(),"tvPlay",season.getId());
    }
}
