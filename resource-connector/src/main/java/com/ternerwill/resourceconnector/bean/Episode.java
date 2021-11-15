package com.ternerwill.resourceconnector.bean;

import com.ternerwill.resourceconnector.util.Config;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Episode {
    private String name;

    private Integer seasonIndex;

    private Integer index;

    private String videoSource;

    private Integer id;

    private List<String> supportBroadcastTypes = List.of(".mp4");

    public Episode(){}

    public Episode(String name,Integer seasonIndex,Integer index,String videoSource){
        this.name = name;
        this.seasonIndex = seasonIndex;
        this.index = index;
        this.videoSource = videoSource;
    }

    public String getEpisodeName(){
        return String.format("%s第%d季第%d集",name,seasonIndex,index);
    }

    public String getDownloadSource(){
        return String.format("%s/mobile/video/tvPlay/download/%d", Config.getRequestBaseUrl(),id);
    }

    public String getRequestUrl(){
        return String.format("%s/mobile/video/tvPlay/play/%d",Config.getRequestBaseUrl(),id);
    }

    public Boolean supportBroadcast(){
        return supportBroadcastTypes.stream().anyMatch( type -> videoSource.endsWith(type));
    }

    public Map<String,Object> getEpisodeDetail(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",getEpisodeName());
        map.put("id",id);
        map.put("downloadSource",getDownloadSource());
        map.put("supportBroadcast",supportBroadcast());
        return map;
    }

    public Map<String,Object> getPlayInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",getEpisodeName());
        map.put("tvPlaySource",getRequestUrl());
        return map;
    }
}
