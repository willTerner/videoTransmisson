package com.ternerwill.resourceconnector.mapper;

import com.ternerwill.resourceconnector.bean.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MovieMapper {
    @Select("SELECT * FROM movie where movie_id = #{id}")
    public Movie getMovieById(int id);

    @Select("SELECT * FROM movie where movie_name = #{movieName}")
    public Movie getMovieByName(String movieName);

    @Select("SELECT * FROM movie")
    public List<Movie> getMovies();

    @Update("UPDATE movie set poster_path = #{posterPath} WHERE movie_id =#{id}")
    public void updateMoviePosterPath(String posterPath,int id);

    @Options(useGeneratedKeys=true,keyColumn="movie_id",keyProperty="movieId")
    @Insert("INSERT INTO movie (movie_name,movie_path,movie_description,poster_path) VALUES(#{movieName},#{moviePath},#{movieDescription},#{posterPath})")
    public void insertMovie(Movie movie);


}
