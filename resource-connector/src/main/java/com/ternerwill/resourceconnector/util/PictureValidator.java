package com.ternerwill.resourceconnector.util;

import com.ternerwill.resourceconnector.exception.MyException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class PictureValidator {
    private List<String> validPicture = List.of(".webp",".jpg",".png",".jpeg",".svg");

    public void validatePicture(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            throw new MyException(filePath+"不存在或者不是有效的文件!");
        }
        String fileName = file.getName();
        if(!validPicture.stream().anyMatch(( type ) -> fileName.endsWith(type))){
            throw new MyException(fileName+"格式无效!");
        }
    }
}
