package com.ternerwill.resourceconnector.bean;

import com.ternerwill.resourceconnector.exception.MyException;
import com.ternerwill.resourceconnector.util.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Movie {


    private String movieName;

    private String moviePath;

    private Integer movieId;

    private String movieDescription;

    private String posterPath;

    private boolean supportBroadcast;

    private List<String> broadcastTypes = List.of(".mp4");

    public String getMovieRequestUrl(){
        return String.format("%s/mobile/video/movie/play/%d",Config.getRequestBaseUrl(),movieId);
    }

    public String getPosterRequestURL(){
        return String.format("%s/mobile/video/movie/poster/%d",Config.getRequestBaseUrl(),movieId);
    }

    public void setMoviePath(String moviePath){
        this.moviePath = moviePath;
        this.supportBroadcast = broadcastTypes.stream().anyMatch( type -> moviePath.endsWith(type) );
    }

    public Map<String,Object> getPlayInfo(){
        Map<String,Object> result = new HashMap<>();
        result.put("movieName",movieName);
        result.put("movieId",movieId);
        result.put("movieDescription",movieDescription);
        result.put("movieSource",getMovieRequestUrl());
        result.put("supportBroadcast",supportBroadcast);
        result.put("fileName",getFileName());
        result.put("downloadSource",getDownloadUrl());
        return result;
    }

    private String getDownloadUrl(){
        return String.format("%s/mobile/video/movie/download/%d",Config.getRequestBaseUrl(),movieId);
    }

    private String getFileName(){
        if(moviePath == null){
            throw new NullPointerException("moviePath is null!");
        }
        File file = new File(moviePath);
        return file.getName();
    }

}
