import Vue from 'vue'
import VueRouter from 'vue-router'
import Video from '../views/Video'
import PlayMovie from '../views/PlayMovie'
import MovieDetail from '../views/MovieDetail'
import PlayTvPlay from '../views/PlayTvPlay'
import TvPlayDetail from '../views/TvPlayDetail'
Vue.use(VueRouter)

const routes=[
    {
        //电视剧详情页
        path:"/video/tvPlay/detail/:id",component:TvPlayDetail 
    },
    {
        // 电影详情页
        path:"/video/movie/:id",component:MovieDetail
    },
    {
        //首页
        path:"/",redirect:"/video/home"
    },
    {
        //首页
        path:"/video/home",component:Video
    },
    {
        // 播放电影
        path:"/video/movie/play/:id",component:PlayMovie
    },
    {
        // 播放电视剧
        path:"/video/tvPlay/play/:id",component: PlayTvPlay
    }
]

export default new VueRouter({
    // mode:'history',
    routes
})