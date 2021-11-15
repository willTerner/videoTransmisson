package com.ternerwill.resourceconnector.controller.desktop;

import com.ternerwill.resourceconnector.bean.Movie;
import com.ternerwill.resourceconnector.bean.Result;
import com.ternerwill.resourceconnector.exception.MyException;
import com.ternerwill.resourceconnector.service.MovieService;
import com.ternerwill.resourceconnector.util.VideoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/desktop/video/movie")
public class DesktopMovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private VideoValidator validator;

    @PutMapping
    public Result addMovie(@RequestBody Movie movie){
        if(movie.getMovieName() == null || movie.getMovieDescription() == null || movie.getMoviePath() == null || movie.getPosterPath() == null){
            return Result.fail(400,"提交信息不全");
        }
        if(movieService.getMovieByName(movie.getMovieName())!=null){
            return Result.fail(400,"电影名已经存在");
        }
        try{
            validator.validateMovie(movie);
        }
        catch( MyException ex){
            return Result.fail(400,ex.getMessage());
        }
        boolean isSaved =  movieService.insertMovie(movie);
        if( !isSaved ){
            return Result.fail(400,"出了一点错误");
        }
        return Result.success(200,"success");
    }
}
