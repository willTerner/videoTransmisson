<template>
  <div id="video" style="z-index:1000">

  </div>
</template>

<script>
import axios from 'axios'
import config from '../util/config'
import Player from 'xgplayer/dist/simple_player';
import volume from 'xgplayer/dist/controls/volume';
import playbackRate from 'xgplayer/dist/controls/playbackRate';

export default {
    data(){
        return {
            episode:{}
        }
    },
    mounted(){
        let id = this.$route.params.id;
        let playVideo = this;
        (async() => {
             await axios.get(config.baseURL+`/video/tvPlay/playInfo/${id}`)
             .then( response => {
                 let resp = response.data;
                 if(resp.status == 200){
                     playVideo.episode = resp.data;
                 }
                 else{
                     return Promise.reject(resp.message);
                 }
             });

             new Player({
                id:"video",
                url:playVideo.episode.tvPlaySource,
                fluid:true,
                rotateFullscreen:true,
                controlPlugins: [
                    volume,
                    playbackRate,
                ],
                playbackRate: [0.5, 0.75, 1, 1.5, 2],
                closeVideoTouch:true,
                closeVideoDblclick:true,
                enableVideoDbltouch: true,
        });

        })().catch(err => playVideo.$toast.fail(err ));
    }

}
</script>

<style>

</style>