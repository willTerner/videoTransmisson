<template>
<van-sticky>
    
      <form action="/">
        <van-search v-model="searchItem"  placeholder="请输入搜索影片或者音乐" @search="search" shape="round" background="#001938">

      </van-search>
      </form>
  
</van-sticky>
</template>

<script>
export default {
    data(){
        return {
            searchItem:""
        }
    },
    methods:{
        search(){

        },
    }

}
</script>

<style>

</style>