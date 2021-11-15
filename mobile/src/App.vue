<template>
  <div id="app">
    <my-header></my-header>
    
  <div class="box">
      
      <div class="roll">
        <router-view></router-view>
      </div>
      
      <footer>
        <my-footer></my-footer>
      </footer>
  </div>    
  </div>
</template>

<script>


import Vue from 'vue'
import { Search ,Sticky,Tabbar,TabbarItem,Grid,GridItem,Image,Cell,CellGroup,Toast,Tag,Button,Skeleton,Lazyload} from 'vant'
import MyHeader from './components/Header.vue'
import MyFooter from './components/Footer.vue'
import 'bootstrap/dist/css/bootstrap.min.css'


Vue.use(Lazyload,{
  lazyComponent:true
});
Vue.use(Skeleton);
Vue.use(Button);
Vue.use(Tag);
Vue.use(Toast);
Vue.use(Cell);
Vue.use(CellGroup);
Vue.use(Image);
Vue.use(Grid);
Vue.use(GridItem);
Vue.use(Search);
Vue.use(Sticky);
Vue.use(Tabbar);
Vue.use(TabbarItem);

export default {
  name: 'App',
  components: {
    MyHeader,
    MyFooter,
  },
  data(){
    return {
      arr:[]
    }
  },
  created(){
    for(let i=0;i<100;i++){
      this.arr.push(i);
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

html,body{
    margin:0;padding:0;height:100%;width:100%;
}
footer{
  max-width: 750px;width: 100%;height: 6vh;
}
.roll{
            padding-bottom:1rem;
}
footer{
		position:fixed;bottom:0;z-index:999;
}
</style>
