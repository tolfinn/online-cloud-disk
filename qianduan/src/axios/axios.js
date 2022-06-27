/**
 * ajax请求配置
 */
import axios from "axios";


// axios默认配置
axios.defaults.timeout = 10 * 60000;   // 超时时间  1分钟
// axios.defaults.baseURL = "http://localhost:8080";   // 默认地址
axios.defaults.baseURL = "http://192.168.42.10:80";   // 默认地址

//整理数据
// axios.defaults.transformRequest = function (data) {
//   return JSON.stringify(data);
// };

// http request interceptor
axios.interceptors.request.use(
  config => {
    // eslint-disable-next-line no-prototype-builtins
    if (!config.headers.hasOwnProperty("Content-Type")) {
      // config.headers["Content-Type"] = "application/json;charset=UTF-8;text/plain";
      // config.headers["Content-Type"] = "application/x-www-form-urlencoded";
      config.headers["token"] = sessionStorage.getItem("token");
    }
    return config;
  },
  error => {
    return Promise.reject(error.response);
  });

// http response interceptor
axios.interceptors.response.use(
  response => {
    let code = response.data.code;
    if (code != undefined && code == 50014) {
      location.href = "/login";
    } else {
      return response.data;
    }
  },
  error => {
    return Promise.reject(error.response);   // 返回接口返回的错误信息
  });


export default axios;
