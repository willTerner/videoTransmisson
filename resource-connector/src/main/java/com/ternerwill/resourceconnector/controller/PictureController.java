package com.ternerwill.resourceconnector.controller;

import com.ternerwill.resourceconnector.bean.Movie;
import com.ternerwill.resourceconnector.bean.Season;
import com.ternerwill.resourceconnector.service.MovieService;
import com.ternerwill.resourceconnector.service.TvPlayService;
import com.ternerwill.resourceconnector.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mobile")
public class PictureController {

    @Autowired
    private DownloadUtil downloadUtil;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TvPlayService tvPlayService;

    @GetMapping("/video/movie/poster/{id}")
    public void getMoviePoster(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response){
        // 根据id 查询路径
        Movie movie = movieService.getMovieById(id);
        if(movie == null){
            return ;
        }
        downloadUtil.fileChunkDownload(movie.getPosterPath(),movie.getMovieId(),request,response);
    }

    @GetMapping("/video/tvPlay/poster/{id}")
    public void getTvPlayPoster(@PathVariable("id")Integer id,HttpServletRequest request,HttpServletResponse response){
        Season season = tvPlayService.getSeasonById(id);
        if(season == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        downloadUtil.fileChunkDownload(season.getCover(),season.getId(),request,response);
    }
}
