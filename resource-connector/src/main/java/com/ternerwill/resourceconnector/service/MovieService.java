package com.ternerwill.resourceconnector.service;

import com.ternerwill.resourceconnector.bean.Movie;
import com.ternerwill.resourceconnector.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieService {

    @Autowired
    private MovieMapper mapper;

    public Movie getMovieById(int id){
        Movie result = null;
        try{
            result = mapper.getMovieById(id);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return result;
        }
        return result;
    }

    public Movie getMovieByName(String movieName){
        Movie result = null;
        try{
            result = mapper.getMovieByName(movieName);
        }
        catch( Exception ex){
            ex.printStackTrace();
            return result ;
        }
        return result;
    }

    public List<Movie> getMovies(){
        List<Movie> result = null;
        try{
            result = mapper.getMovies();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public boolean updateMoviePosterPath(String posterPath,int id){
        try{
            mapper.updateMoviePosterPath(posterPath,id);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertMovie(Movie movie){
        try{
            mapper.insertMovie(movie);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
