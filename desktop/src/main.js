import Vue from 'vue'
import App from './App.vue'
import {Button,Header,Main,Aside,Container,Menu,Submenu,MenuItem,MenuItemGroup,Icon,Avatar,RadioGroup,RadioButton,FormItem,Form,Input,Timeline,TimelineItem,
        Card,
        Pagination,
        Footer,
        Link,
        Row,
        Col,
        Loading,
        Upload,
        Select,
        Option
      } from 'element-ui'
import router from './routers/index'
import store from './store/index'
import myPlugin from './plugins/myPlugin'
Vue.config.productionTip = false

Vue.use(Option);
Vue.use(Select);
Vue.use(Upload);
Vue.use(Loading);
Vue.use(myPlugin);
Vue.use(Col);
Vue.use(Row);
Vue.use(Link);
Vue.use(Button);
Vue.use(Header);
Vue.use(Main);
Vue.use(Aside);
Vue.use(Container);
Vue.use(Menu);
Vue.use(Submenu);
Vue.use(MenuItem);
Vue.use(MenuItemGroup);
Vue.use(Icon);
Vue.use(Avatar);
Vue.use(RadioGroup);
Vue.use(RadioButton);
Vue.use(FormItem);
Vue.use(Form);
Vue.use(Input);
Vue.use(Timeline);
Vue.use(TimelineItem);
Vue.use(Card);
Vue.use(Pagination);
Vue.use(Footer);
new Vue({
  el:"#app",
  render: h => h(App),
  router,
  store
  
});


