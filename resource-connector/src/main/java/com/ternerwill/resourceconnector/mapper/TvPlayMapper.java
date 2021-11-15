package com.ternerwill.resourceconnector.mapper;

import com.ternerwill.resourceconnector.bean.Episode;
import com.ternerwill.resourceconnector.bean.Season;
import com.ternerwill.resourceconnector.bean.TvPlay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TvPlayMapper {

    @Select("SELECT tv_play_id id,tv_play_name tvPlayName,season_number seasonNumber FROM tv_play where tv_play_name =#{name}")
    public TvPlay getTvPlayByName(String name);

    @Insert("INSERT INTO tv_play (tv_play_name) VALUES (#{tvPlayName})")
    public void insertTvPlay(TvPlay tvPlay);

    public void insertEpisodeBatch(List<Episode> episodeList);

    @Insert("INSERT INTO season (play_name,index,episode_num,season_poster) VALUES (#{name},#{index},#{episodeNumber},#{cover})")
    public void insertSeason(Season season);

    @Update("UPDATE TV_PLAY SET season_number = season_number + 1 WHERE TV_PLAY_NAME =#{name}")
    public void incrementSeasonNumber(String name);

    @Select("SELECT season_id id,play_name name,index,episode_num episodeNumber,season_poster cover FROM season")
    public List<Season> getSeasons();

    @Select("SELECT season_id id,play_name name,index,episode_num episodeNumber,season_poster cover  FROM season WHERE season_id =#{id}")
    public Season getSeasonById(Integer id);

    @Select("SELECT id,name,season_index seasonIndex,index,video_source videoSource FROM episode WHERE name = #{name} AND season_index =#{index}")
    public List<Episode> getEpisodesBySeason(String name,Integer index);

    @Select("SELECT id,name,season_index seasonIndex,index,video_source videoSource FROM episode WHERE id=#{id}")
    public Episode getEpisodeById(Integer id);
}
