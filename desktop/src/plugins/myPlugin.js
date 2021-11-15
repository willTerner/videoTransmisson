function formatter(time){
    return time<10?"0"+time:time;
}

let plugin={};
plugin.install=function(Vue){
    //定义时间格式化过滤器
    Vue.filter("timeFormatter",function(time){
        time=new Date(time);
        return `${time.getFullYear()}-${formatter(time.getMonth()+1)}-${formatter(time.getDate())} ${formatter(time.getHours())}:${formatter(time.getMinutes())}:${formatter(time.getSeconds())}`;
    });
}
export default plugin;

