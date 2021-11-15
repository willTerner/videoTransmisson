package com.ternerwill.resourceconnector.util;

import com.ternerwill.resourceconnector.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtil {

    @Autowired
    private VideoValidator validator;

    public List<String> getEpisodePath(String rootDir, Integer episodeNumber, String expression){
        File root = new File(rootDir);
        List<String> result = new ArrayList<>(episodeNumber);
        if( !root.exists() || !root.isDirectory()){
            throw new MyException("根目录无效或者不是目录!");
        }
        int number =0;
        for(int i=0;i<expression.length();i++){
            if('%' == expression.charAt(i)){
                number++;
            }
        }
        Integer[] num = new Integer[number];
        for(int i=1;i<=episodeNumber;i++){
            for(int j=0;j<number;j++){
                num[j] = i;
            }
            String fileName =null;
            try{
                fileName = String.format(expression,num);
            }
            catch(Exception ex){
                throw new MyException("文件名格式不能被解析!");
            }
            result.add(getFilePath(rootDir,fileName));
        }
        return result;
    }

    private String getFilePath(String rootDir,String fileName){
        String filePath = null;
        if(rootDir.indexOf('/')!=-1){
            filePath = rootDir+"/"+fileName;
        }
        else if(rootDir.indexOf('\\')!=-1){
            filePath = rootDir + "\\" + fileName;
        }
        else{
            throw new MyException("rootDir格式错误!");
        }
        validator.validateVideo(filePath);
        return filePath;
    }


}
