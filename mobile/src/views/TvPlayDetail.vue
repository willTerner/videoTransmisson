<template>
  <van-cell-group inset>
      <van-cell v-for="(episode,index) in episodes" :key="index" :title="episode.name"  is-link >
          <template>
              <a class="badge badge-pill badge-success mr-1" @click="playTvPlay(index)" v-if="episode.supportBroadcast">在线观看</a>
              <a class="badge badge-pill badge-warning" :href="episode.downloadSource" download>下载</a>
          </template>
      </van-cell>
      <van-cell title="没有更多了!" value="" is-link arrow-direction="up"></van-cell>
  </van-cell-group>
</template>

<script>
import axios from 'axios'
import config from '../util/config'
export default {
    data(){
        return {
            episodes:[]
        }
    },
    methods:{
        playTvPlay(index){
            this.$router.push(`/video/tvPlay/play/${this.episodes[index].id}`);
        }
    },
    created(){
        let tvPlayDetailCom = this;
        axios.get(`${config.baseURL}/video/tvPlay/season/${this.$route.params.id}`)
            .then( response => {
                let resp = response.data;
                if(resp.status == 200){
                    tvPlayDetailCom.episodes = resp.data;
                }
                else{
                    return Promise.reject(resp.message);
                }
            })
            .catch( err => {
                let message = err;
                if( err instanceof Error){
                    message = err.message;
                }
                tvPlayDetailCom.$toast.fail(message);
            });
    }
}
</script>

<style scoped>

</style>