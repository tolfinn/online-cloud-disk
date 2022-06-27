import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

//创建一个路由
const routes = [
  {
    path: "/", // url路径"/"代表首页，http://localhost:8080/
    redirect: "/login"
  },
  {
    path: "/login",
    name: "login",
    component: () => import("@/pages/login")
  },
  {
    path: "/register",
    name: "register",
    component: () => import("@/pages/register")
  },
  {
    path: "/admin",
    // name: "admin",
    component: () => import("@/pages/admin/index"),
    children: [
      {
        path: "operate",
        name: "operate",
        component: () => import("@/pages/admin/operate"),
        props(route) {
          return {
            search: route.query.search,
          }
        },
      },
      {
        path: "modifyUser",
        name: "modifyUser",
        component: () => import("@/pages/admin/modifyUser"),
        props(route) {
          return {
            info: route.params.info,
          }
        },
      },
      {
        path: "addUser",
        name: "addUser",
        component: () => import("@/pages/admin/addUser"),
      },
    ],
  },
  {
    path: "/index",
    // name: "index",
    // redirect:"/content",
    component: () => import("@/pages/index"),
    children: [
      {
        path: "",
        name: "content",
        component: () => import("@/pages/content")
      },
      {
        path: "myfile",
        name: "myfile",
        component: () => import("@/pages/file/myfile"),
        props(route) {
          return {
            path: route.query.path,
            search:route.query.search,
          }
        },
      },
      {
        path: "pictures/:list",
        name: "pictures",
        component: () => import("@/pages/file/pictures"),
        props: true,
        // props(route) {
        //   console.log(route);
        //   return {
        //     list:route.params.list,
        //   }
        // },
      },
      {
        path: "documents",
        name: "documents",
        component: () => import("@/pages/file/documents"),
        props(route) {
          return {
            title: route.query.title,
          }
        },
      },
      {
        path: "vedios",
        name: "vedios",
        component: () => import("@/pages/file/vedios")
      },
      {
        path: "audios",
        name: "audios",
        component: () => import("@/pages/file/audios")
      },
      {
        path: "others",
        name: "others",
        component: () => import("@/pages/file/others")
      },
      {
        path: "collection",
        name: "collection",
        component: () => import("@/pages/collection"),
      },
      {
        path: "share",
        name: "share",
        component: () => import("@/pages/share"),
      },
      {
        path: "recyclebin",
        name: "recyclebin",
        component: () => import("@/pages/recyclebin"),
      },
      {
        path: "modifyPassword",
        name: "modifyPassword",
        component: () => import("@/pages/userUpdate/modifyPassword"),
      },
      {
        path: "modifyInformation",
        name: "modifyInformation",
        component: () => import("@/pages/userUpdate/modifyInformation"),
      },
    ],
  },

];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

// const VueRouterPush = router.prototype.push
// router.prototype.push = function push (to) {
//   return VueRouterPush.call(this, to).catch(err => err)
// }

export default router;
