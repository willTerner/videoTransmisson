<template>
  <el-aside  style="background-color:#545c64" width="10%" class="aside">
        <el-menu  text-color="#FFFFFF"  background-color="#545c64" active-text-color="#ffd04b" class="font"> 
            <el-submenu index="1">
              <template slot="title" >
                <i class="el-icon-video-play white" ></i>
                <span class="font">视频</span>
              </template>
              <el-menu-item-group >
                <template slot="title" class="font">电影</template>
                <el-menu-item index="1-1"  class="font" @click="addMovie">添加电影</el-menu-item>
                <el-menu-item index="1-2"  class="font" @click="editMovie">修改电影</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group>
                  <template slot="title" class="font">电视剧</template>
                  <el-menu-item index="1-3" class="font" @click="addTvPlay">添加电视剧</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

           

        </el-menu>
</el-aside>
</template>

<script>
export default {
    data:()=>{
        return {
            
        };
    },
    methods:{
      addMovie(){
        this.$router.push("/video/add");
      },
      editMovie(){

      },
      addTvPlay(){
        this.$router.push("/tvPlay/add");
      }
    }

}
</script>

<style scoped>
.font{
  font-size:14px;
}
.white{
  color:#FFFFFF;
}

</style>