<template>
  <van-cell-group insert>
      <van-cell :title="movie.movieName" is-link :label="movie.movieDescription">
          <template>
              <a class="badge badge-pill badge-success mr-1" v-if="movie.supportBroadcast" @click="playMovie">在线观看</a>
              <a :href="movie.downloadSource" :download="movie.fileName" class="badge badge-pill badge-warning" >下载</a>
          </template>
      </van-cell>
  </van-cell-group>
</template>

<script>
import axios from 'axios'
import config from '../util/config'

export default {
    data(){
        return {
            movie:{}
        }
    },
    created(){
        let id = this.$route.params.id;
        let movieDetail = this;
        axios.get(config.baseURL+`/video/movie/playInfo/${id}`)
             .then( response => {
                 let resp = response.data;
                 if(resp.status == 200 ){
                     movieDetail.movie = resp.data;
                 }
                 else{
                     return Promise.reject(resp.message);
                 }
             })
             .catch( err => movieDetail.$toast.fail(err));
    },
    methods:{
        playMovie(){
            this.$router.push(`/video/movie/play/${this.movie.movieId}`);
        },
        downLoadVideo(){
            
        }
    }
}
</script>

<style scoped>

</style>