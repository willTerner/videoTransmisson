package com.ternerwill.resourceconnector.bean;

import lombok.Data;

@Data
public class TvPlay {
    private String tvPlayName;
    private Integer seasonNumber;
    private Integer id;
    public TvPlay(){}

    public TvPlay(String tvPlayName,Integer seasonNumber){
        this.tvPlayName = tvPlayName;
        this.seasonNumber = seasonNumber;
    }

    public TvPlay(String tvPlayName){
        this.tvPlayName = tvPlayName;
    }
}
