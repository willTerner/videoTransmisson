package com.ternerwill.resourceconnector.controller.mobile;

import com.ternerwill.resourceconnector.bean.Episode;
import com.ternerwill.resourceconnector.bean.Result;
import com.ternerwill.resourceconnector.bean.Season;
import com.ternerwill.resourceconnector.service.TvPlayService;
import com.ternerwill.resourceconnector.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mobile/video/tvPlay")
public class MobileSeasonController {
    @Autowired
    private TvPlayService tvPlayService;

    @Autowired
    private DownloadUtil util;

    @GetMapping("/season/{id}")
    public Result getSeason(@PathVariable("id")Integer id){
        List<Episode> episodes = null;
        try{
            Season season = tvPlayService.getSeasonById(id);
            episodes = tvPlayService.getEpisodesBySeason(season.getName(),season.getIndex());
        }
        catch(RuntimeException ex){
            ex.printStackTrace();
            return Result.fail(400,"出了一点问题!");
        }
        List<Map<String,Object>> data = episodes.stream().map(episode -> episode.getEpisodeDetail()).collect(Collectors.toList());
        return Result.success(200,data);
    }

    @GetMapping("/play/{id}")
    public void playEpisode(@PathVariable("id")Integer id, HttpServletRequest request, HttpServletResponse response){
        Episode episode = null;
        try{
            episode = tvPlayService.getEpisodeById(id);
        }catch(RuntimeException ex){
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ;
        }
        util.fileChunkDownload(episode.getVideoSource(),id,request,response);
    }

    @GetMapping("/playInfo/{id}")
    public Result getPlayInfo(@PathVariable("id")Integer id){
        Episode episode = null;
        try{
            episode = tvPlayService.getEpisodeById(id);
        }catch(RuntimeException ex){
            ex.printStackTrace();
            return Result.fail(400,"出了一点问题!");
        }
        return Result.success(200,episode.getPlayInfo());
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id")Integer id,HttpServletResponse response) {
        Episode episode = null;
        try {
            episode = tvPlayService.getEpisodeById(id);
        }catch(RuntimeException ex){
            ex.printStackTrace();
            return ;
        }
        try {
            util.download(episode.getVideoSource(),id,response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
