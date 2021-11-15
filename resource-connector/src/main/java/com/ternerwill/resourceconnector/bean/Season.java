package com.ternerwill.resourceconnector.bean;

import com.ternerwill.resourceconnector.util.Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Season {
    private String name;
    private Integer index;
    private String cover;
    private Integer episodeNumber;
    private Integer id;

    public String getCoverBaseURL(){
        if(name==null || index ==0 ){
            throw new IllegalArgumentException("season name and index haven't been initialized!");
        }
        return String.format("%s/mobile/video/tvPlay/poster/%d",Config.getRequestBaseUrl(),id);
    }

    public Season(){}

    public Season(String name,Integer index,String cover,Integer episodeNumber){
        this.name = name;
        this.index = index;
        this.cover = cover;
        this.episodeNumber = episodeNumber;
    }

    public String getSeasonName(){
        return String.format("%s第%d季",name,index);
    }

}
