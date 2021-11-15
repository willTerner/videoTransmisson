package com.ternerwill.resourceconnector.service;


import com.ternerwill.resourceconnector.bean.Episode;
import com.ternerwill.resourceconnector.bean.Season;
import com.ternerwill.resourceconnector.bean.TvPlay;
import com.ternerwill.resourceconnector.mapper.TvPlayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TvPlayService {

    @Autowired
    private TvPlayMapper mapper;

    public void insertTvPlay(TvPlay tvPlay){
        mapper.insertTvPlay(tvPlay);
    }

    public void insertEpisodeBatch(List<Episode> episodeList){
        mapper.insertEpisodeBatch(episodeList);
    }

    public TvPlay getTvPlayByName(String name){
        TvPlay result = mapper.getTvPlayByName(name);
        return result;
    }

    public void insertSeason(Season season){
        mapper.insertSeason(season);
    }

    @Transactional
    public void insertEpisodes(List<Episode> episodeList, TvPlay tvPlay, Season season){
        // 插入新的电视剧
        if(getTvPlayByName(tvPlay.getTvPlayName())==null){
            insertTvPlay(tvPlay);
        }
        else{
            mapper.incrementSeasonNumber(tvPlay.getTvPlayName());
        }
        // 插入剧集
        insertEpisodeBatch(episodeList);
        // 插入季
        insertSeason(season);
    }

    public List<Season> getSeasons(){
        return mapper.getSeasons();
    }

    public Season getSeasonById(Integer id){
        return mapper.getSeasonById(id);
    }

    public List<Episode> getEpisodesBySeason(String name,Integer index){
        return mapper.getEpisodesBySeason(name,index);
    }

    public Episode getEpisodeById(Integer id){
        return mapper.getEpisodeById(id);
    }
}
