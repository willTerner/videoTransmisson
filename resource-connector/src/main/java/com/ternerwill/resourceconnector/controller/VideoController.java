package com.ternerwill.resourceconnector.controller;

import com.ternerwill.resourceconnector.bean.Movie;
import com.ternerwill.resourceconnector.bean.Result;
import com.ternerwill.resourceconnector.bean.Season;
import com.ternerwill.resourceconnector.bean.Video;
import com.ternerwill.resourceconnector.service.MovieService;
import com.ternerwill.resourceconnector.service.TvPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mobile/videos")
public class VideoController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private TvPlayService tvPlayService;

    @GetMapping
    public Result getVideos(){
        List<Movie> movies = movieService.getMovies();
        List<Season> seasons = null;
        try{
            seasons = tvPlayService.getSeasons();
        }catch(RuntimeException ex){
            ex.printStackTrace();
            return Result.fail(400,"出了一点问题!");
        }

        // 查询电影
        if( movies == null ){
            return Result.fail(400,"出了一点问题!");
        }
        List<Video> result = new ArrayList<>();
        result.addAll(movies.stream().map( movie -> Video.movie(movie)).collect(Collectors.toList()));
        result.addAll(seasons.stream().map( season -> Video.season(season)).collect(Collectors.toList()));
        return Result.success(200,result);
    }
}
