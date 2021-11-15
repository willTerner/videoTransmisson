<template>
  
    <el-container id="app">

      
      <my-aside></my-aside>
      <el-container >
        <el-header style="background-color:#000000" >
            <my-header></my-header>
        </el-header>
        

        <el-main>
          <router-view></router-view>
            
        </el-main>
      </el-container>
    </el-container>
 
 
</template>

<script>
import myAside from './components/Aside.vue'
import myHeader from './components/Header.vue'

export default {
  name: 'App',
  components:{
    myAside,myHeader
  }
 
 
}
</script>

<style>


#app{
  min-height:97vh;
  
}




</style>
