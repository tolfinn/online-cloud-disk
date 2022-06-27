<template>
  <el-container style="
  width: 100%;
  height: 100%;">
    <!-- 顶部导航栏 -->
    <el-header style="background-color: #2b4049;height: 70px; width:100%; padding: 0px; top:0;">
      <el-row style="width: 100%;height: 100%;">
        <el-col :span="4" style="height: 100%;display: flex;align-items: center;justify-content: space-around">
          <img src="../assets/logo.png" style="width: 50px;height: 50px;object-fit: contain">
          <span
            style="color: #FFFFFF;font-size: 24px;letter-spacing: 5px;font-weight: bold; padding-left: 7px;">轻工云盘</span>
        </el-col>
        <el-col :span="20" style="height: 100%;display: flex;align-items: center;justify-content: center">
          <div style="width: 96%;height: 100%;display: flex;align-items: center;justify-content: space-between">
            <div style="flex:6">
              <el-input v-model="input" placeholder="请输入...." style="width: 340px;"></el-input>
            </div>

            <!-- 用户列表 -->
            <el-dropdown class="userlist" @command="handleCommand">
              <div class="header-right">
                <el-avatar>
                  <img src="../assets/images/animation/touxiang.jpg" alt />
                </el-avatar>
                <span>{{ username }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-user" command="a">个人信息</el-dropdown-item>
                <el-dropdown-item icon="el-icon-edit" command="b">修改密码</el-dropdown-item>
                <el-dropdown-item icon="el-icon-s-fold" command="c">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

            <!-- 用户提示 -->
            <div style="flex:1 ; display: flex; justify-content: right;">
              <el-badge :value="99999" :max="99" class="item">
                <i class="el-icon-bell" style="font-size: 30px;color: #FFFFFF"></i>
              </el-badge>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-header>

    <el-container>
      <el-aside width="213px" style="display: flex;align-items: center;justify-content: center; ">

        <el-col class="menu">
          <el-menu class="el-menu-vertical-demo" background-color="#ffffff" text-color="#666666"
            active-text-color="#409eff" style="border: 0" :default-openeds='["1"]'>
            <el-submenu index="1">
              <template slot="title">
                <span @click="toMyFileRoute"><strong>我的文件</strong></span>
              </template>
              <el-menu-item index="1-1" @click="toImgRoute">
                <i class="el-icon-picture-outline"></i>图片
              </el-menu-item>
              <el-menu-item index="1-2" @click="toDocRoute">
                <i class="el-icon-document"></i>文档
              </el-menu-item>
              <el-menu-item index="1-3" @click="toVedioRoute">
                <i class="el-icon-film"></i>视频
              </el-menu-item>
              <el-menu-item index="1-4" @click="toAudioRoute">
                <i class="el-icon-headset"></i>音频
              </el-menu-item>
              <el-menu-item index="1-5" @click="toOtherRoute">
                <i class="el-icon-more-outline"></i>其他
              </el-menu-item>
            </el-submenu>
            <el-menu-item index="2" @click="toCollectionRoute">
              <i class="el-icon-star-off"></i>
              <span slot="title">我的收藏</span>
            </el-menu-item>
            <el-menu-item index="3" @click="toShareRoute">
              <i class="el-icon-share"></i>
              <span slot="title">我的分享</span>
            </el-menu-item>
            <el-menu-item index="4" @click="toRecycleBinRoute">
              <i class="el-icon-setting"></i>
              <span slot="title">回收站</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>

  </el-container>
</template>

<script>

export default {
  name: "index",
  data() {
    return {
      input: '',
      username: 'username',
    }
  },
  methods: {
    handleCommand(command) {
      if (command == 'a') {
        console.log('command a');
        this.modifyInformation();
      } else if (command == 'b') {
        this.modifyPassword();
      } else if (command == 'c') {
        this.cancellation();
      }
    },
    // 修改个人信息
    modifyInformation() {
      this.$router.push("modifyInformation");
    },
    //修改密码
    modifyPassword() {
      this.$router.push("modifyPassword");
    },
    // 退出登录
    cancellation() {
      this.$confirm('确定退出当前账号, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push("/");
        this.$message({
          type: 'success',
          message: '退出成功!'
        });
      }).catch(() => {
        this.exits = false;
        this.$message({
          type: 'info',
          message: '已取消退出'
        });
      });
    },

    // 路由跳转
    // 点击我的文件实现跳转
    toMyFileRoute() {
      console.log('toMyFileRoute')
      this.$router.push({
        path: '/index/myfile',
        query: {
        }
      })
    },
    // 图片
    toImgRoute() {
      this.$router.push({
        name: 'pictures',
        params: {
          list: [
            { id: '1', name: 'tilfin' },
            { id: '1', name: 'tilfin' },
            { id: '1', name: 'tilfin' },
          ],
        }
      })
    },
    // 文档
    toDocRoute() {
      this.$router.push({
        path: '/index/documents',
        query: {
          title: '我的文档'
        }
      })
    },
    // 视频
    toVedioRoute() {
      this.$router.push({
        path: '/index/vedios',
        query: {

        }
      })
    },
    // 音频
    toAudioRoute() {
      this.$router.push({
        path: 'audios',
        query: {

        }
      })
    },
    // 其它
    toOtherRoute() {
      this.$router.push({
        path: '/index/others',
        query: {

        }
      })
    },
    // 收藏
    toCollectionRoute() {
      this.$router.push({
        path: '/index/collection',
        query: {

        }
      })
    },
    // 分享
    toShareRoute() {
      this.$router.push({
        path: '/index/share',
        query: {

        }
      })
    },
    // 回收站
    toRecycleBinRoute() {
      this.$router.push({
        path: '/index/recyclebin',
        // query: {}
      })
    },
  },
  mounted() {
    // alert(sessionStorage.getItem("userName"))
    if (sessionStorage.getItem("user")) {
      let user = JSON.parse(sessionStorage.getItem("user"));
      this.username = user.userName;
    };
    this.$bus.$on('showMyFile', (data) => {
      console.log('$bus.$on showMyFile 收到数据，开始', data);
      this.$router.replace({
        path: '/index/myfile?path=' + data,
      })
    });
    this.$bus.$on('searchMyFile', (data) => {
      // alert('我是searchMyFile收到了数据：'+data);
      this.$router.replace({
        path: '/index/myfile?search=' + data,
      })
    })
  },
  beforeDestroy() {
    this.$bus.$off('showMyFile');  //解绑
    this.$bus.$off('searchMyFile');  //解绑
  },
};
</script>

<style scoped>
html {
  overflow-y: overlay;
}

.menu {
  width: 250px;
  height: 100%;
  /* display: flex; */
  /* justify-content: center; */
  border-right: solid 1px #e6e6e6;
  background-color: #FFFFFF;
  color: #333333;
  /* position: relative; */
}

.space {
  width: 210px;
  position: absolute;
  bottom: 15px;
}

.space_context {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 15px;
  position: relative;
}

.add_space {
  text-decoration-line: none;
  color: #2b4049;
  font-size: 13px;
}

.item {
  margin-top: 10px;
  margin-right: 40px;
}

/* 用户信息下拉列表 */
.userlist {
  flex: 1;
  display: flex;
  justify-content: right;
}

.userlist span {
  color: #dedcdc;
  font-size: medium;
  font: bold;
  margin-bottom: -10px;
  margin-left: 5px;
}

a {
  text-decoration: none;
}
</style>