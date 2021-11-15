package com.ternerwill.resourceconnector.util;

import com.ternerwill.resourceconnector.bean.Movie;
import com.ternerwill.resourceconnector.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class VideoValidator {
    private List<String> movieFile = List.of(".mp4",".rmvb",".mkv",".qsv");

    @Autowired
    private PictureValidator pictureValidator;

    public void validateMovie(Movie movie){
        pictureValidator.validatePicture(movie.getMoviePath());
        validateVideo(movie.getMoviePath());
    }

    public void validateVideo(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            throw new MyException(filePath+"不存在或者不是有效的文件！");
        }
        String fileName = file.getName();
        if(!this.movieFile.stream().anyMatch( ( type ) -> fileName.endsWith(type) )){
            throw new MyException(fileName+"格式无效");
        }
    }
}
