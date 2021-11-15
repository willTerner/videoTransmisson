package com.ternerwill.resourceconnector.controller.mobile;

import com.ternerwill.resourceconnector.bean.Movie;
import com.ternerwill.resourceconnector.bean.Result;
import com.ternerwill.resourceconnector.service.MovieService;
import com.ternerwill.resourceconnector.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mobile/video/movie")
public class MobileMovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private DownloadUtil util;



    @GetMapping("/playInfo/{id}")
    public Result getPlayInfo(@PathVariable("id")Integer id){
        Movie movie = movieService.getMovieById(id);
        if( movie == null){
            return Result.fail(400,"电影不存在!");
        }
        Map<String,Object> result = movie.getPlayInfo();
        return Result.success(200,result);
    }

    @GetMapping("/play/{id}")
    public void getMoviePlay(@PathVariable("id")Integer id,HttpServletRequest request,HttpServletResponse response){
        Movie movie = movieService.getMovieById(id);
        if(movie == null){
            return ;
        }
        util.fileChunkDownload(movie.getMoviePath(), movie.getMovieId(), request,response);
    }

    @GetMapping("/download/{id}")
    public void downLoadMovie(@PathVariable("id") Integer id,HttpServletResponse response) throws IOException {
        Movie movie = movieService.getMovieById(id);
        if(movie == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ;
        }
        util.download(movie.getMoviePath(),movie.getMovieId(),response);
    }
}
