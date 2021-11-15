<template>
  <van-skeleton avatar title :row="12" :loading="loading">
      <van-grid :border="false" :column-num="2" :gutter="10">
          <van-grid-item v-for="(video,index) in videos" :key="index" @click="videoDetail(index)">
              <van-image  :src="video.posterUrl" lazy-load />
              <p>{{video.name}}</p>
          </van-grid-item>
     </van-grid>
  </van-skeleton>
</template>

<script>
import axios from 'axios'
import config from '../util/config'
export default {
    data(){
      return {
        videos:[],
        loading:true
      }
    },
    methods:{
      videoDetail(index){
        let video = this.videos[index];
        if("movie"==video.type){
          this.$router.push(`/video/movie/${video.id}`).catch(()=>{});
        }
        else{
          this.$router.push(`/video/tvPlay/detail/${video.id}`).catch(()=>{});
        }
      }
    },
    created(){
      let videoCom = this;
      axios.get(config.baseURL+"/videos")
           .then( response => {
             let resp = response.data;
             if(resp.status == 200 ){
               videoCom.videos = resp.data;
               videoCom.loading = false;
             }
             else{
               return Promise.reject(resp.message);
             }
           })
           .catch( err => {
             videoCom.loading = false;
             let message = err;
             if( err instanceof Error ){
               message = err.message;
             }
             videoCom.$toast.fail(message);
           });
    }
}
</script>

<style scoped>

</style>