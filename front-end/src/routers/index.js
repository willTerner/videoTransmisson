import VueRouter from 'vue-router'
import Vue from 'vue'
import AddVideo from '../views/AddVideo'
import Music from '../views/Music'
import AddTvPlay from '../views/AddTvPlay'
Vue.use(VueRouter);

const routes=[
        {
            //主页面重定向至博客展示页
            path:"/",redirect:"/music/listen"
        },
        {
            //播放音乐
            path:"/video/listen",component:Music
        },
        {
            //添加电影
            path:"/video/add",component:AddVideo
        },
        {
            path:"/tvPlay/add",component:AddTvPlay
        }
    ];

let router=new VueRouter({
    // mode:"history",
    routes
});


export default router;