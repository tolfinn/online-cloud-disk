import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "@/axios/axios";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import echarts from 'echarts'
import store from "./store/store";


Vue.config.productionTip = false;
Vue.prototype.$axios = axios;

Vue.use(ElementUI);
Vue.prototype.$echarts = echarts

//全局过滤器  格式化文件大小和日期
Vue.filter('formatTime', function (time) {
  // 格式化时间
  var dt = new Date(time);
  let year = dt.getFullYear();
  let month = (dt.getMonth() + 1).toString().padStart(2, '0');
  let date = dt.getDate().toString().padStart(2, '0');
  let hour = dt.getHours().toString().padStart(2, '0');
  let minute = dt.getMinutes().toString().padStart(2, '0');
  let second = dt.getSeconds().toString().padStart(2, '0');
  let res = year + '-' + month + '-' + date + ' ' + hour + ':' + minute + ':' + second;
  return res;
})
Vue.filter('bytesToSize', function (bytes) {
  // 格式化时间
  if(bytes < 0) return '-';
  if (bytes === 0) return '0 B';
  var k = 1000, // or 1024
    sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
    i = Math.floor(Math.log(bytes) / Math.log(k));
  return (bytes / Math.pow(k, i)).toFixed(1) + ' ' + sizes[i];
})

Vue.filter('IdToFileName', function (fileid) {
  // 文件id转为文件名
  let arr = fileid.split('/');
  return arr[arr.length-1];
})

new Vue({
  router,
  store,
  render: (h) => h(App),
  beforeCreate() {
    Vue.prototype.$bus = this //安装全局事件总线，$bus就是当前应用的vm
  },
}).$mount("#app");
