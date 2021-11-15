package com.ternerwill.resourceconnector.controller.desktop;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternerwill.resourceconnector.bean.Episode;
import com.ternerwill.resourceconnector.bean.Result;
import com.ternerwill.resourceconnector.bean.Season;
import com.ternerwill.resourceconnector.bean.TvPlay;
import com.ternerwill.resourceconnector.service.TvPlayService;
import com.ternerwill.resourceconnector.util.FileUtil;
import com.ternerwill.resourceconnector.util.PictureValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/desktop/video")
public class DesktopTvPlayController {
    @Autowired
    private TvPlayService tvPlayService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private PictureValidator pictureValidator;

    @PutMapping("/season")
    public Result addSeason(@RequestBody String data){
        Map<String,Object> map = null;
        try {
            map = mapper.readValue(data, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Result.fail(400,"输入格式错误!");
        }
        Integer episodeNumber = null;
        String name = (String)map.get("name");

        Integer index = (Integer)map.get("index");
        String cover = (String)map.get("cover");
        List<Episode> episodeList = new ArrayList<>();
        try{
            episodeNumber = Integer.valueOf((String)map.get("episodeNumber"));
        }
        catch(Exception ex){
            return Result.fail(400,"集数格式异常!");
        }
        List<String> files = null;
        try{
            pictureValidator.validatePicture(cover);
            files = fileUtil.getEpisodePath((String)map.get("rootDir"),episodeNumber,(String)map.get("expression"));
        }
        catch(Exception ex){
            ex.printStackTrace();
            return Result.fail(400,ex.getMessage());
        }
        for(int i=1;i<=episodeNumber;i++){
            episodeList.add(new Episode(name,index,i,files.get(i-1)));
        }
        TvPlay tvPlay = new TvPlay(name);
        Season season = new Season(name,index,cover,episodeNumber);
        try{
            tvPlayService.insertEpisodes(episodeList,tvPlay,season);
        }
        catch(RuntimeException ex){
            ex.printStackTrace();
            return Result.fail(400,"插入数据失败！");
        }
        return Result.success(200,"success");
    }
}
