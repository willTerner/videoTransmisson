<template>
  <van-tabbar v-model="active" active-color="#ee0a24" inactive-color="#000">
      <van-tabbar-item icon="play-circle-o">视频</van-tabbar-item>
      <van-tabbar-item icon="music-o">音乐</van-tabbar-item>
      <van-tabbar-item icon="user-o">用户</van-tabbar-item>
  </van-tabbar>
</template>

<script>
export default {
    data(){
        return {
            active:0
        }
    }

}
</script>

<style scoped>


</style>
