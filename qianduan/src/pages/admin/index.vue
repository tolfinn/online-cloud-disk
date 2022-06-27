<template>
  <el-container style="
  width: 100%;
  height: 100%;">
    <!-- 顶部导航栏 -->
    <el-header style="background-color: #2b4049;height: 70px; width:100%; padding: 0px; top:0;">
      <el-row style="width: 100%;height: 100%;">
        <el-col :span="4" style="height: 100%;display: flex;align-items: center;justify-content: space-around">
          <img src="../../assets/logo.png" style="width: 50px;height: 50px;object-fit: contain">
          <span
            style="color: #FFFFFF;font-size: 24px;letter-spacing: 5px;font-weight: bold; padding-left: 7px;">轻工云盘</span>
        </el-col>
        <el-col :span="20" style="height: 100%;display: flex;align-items: center;justify-content: center">
          <div style="width: 96%;height: 100%;display: flex;align-items: center;justify-content: space-between">
            <div style="flex:6">
              <el-input v-model="input" @keyup.enter.native="searchUser" placeholder="输入用户名查找用户..." style="width: 340px;">
              </el-input>
            </div>

            <!-- 用户列表 -->
            <el-dropdown class="userlist" @command="handleCommand">
              <div class="header-right">
                <el-avatar>
                  <img src="../../assets/images/animation/touxiang.jpg" alt />
                </el-avatar>
                <span>{{ username }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-s-fold" command="a">退出系统</el-dropdown-item>
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
      <el-aside width="200px" style="display: flex;align-items: center;justify-content: center; ">

        <el-col class="menu">
          <el-menu class="el-menu-vertical-demo" background-color="#ffffff" text-color="#666666"
            active-text-color="#409eff" style="border: 0" :default-openeds='["1"]'>

            <el-menu-item index="1" @click="userControl">
              <i class="el-icon-user-solid"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="2" @click="stationManagement">
              <i class="el-icon-office-building"></i>
              <span slot="title">站内信息管理</span>
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
  name: "admin",
  data() {
    return {
      input: '',
      username: '',
    }
  },
  methods: {
    handleCommand(command) {
      if (command == 'a') {
        console.log('command a');
        this.$router.push("/");
      }
    },
    // 查找用戶
    searchUser() {
      this.$router.push({
        path: '/admin/operate',
        query: {
          search: this.input,
        }
      })
    },
    // 用户管理
    userControl() {
      console.log('toMyFileRoute')
      this.$router.push({
        path: '/admin/operate',
        query: {
           search:'',
        }
      })
    },
    // 站内信息管理
    stationManagement() {

    }
  },

  mounted() {
    // alert(sessionStorage.getItem("userName"))
    if (sessionStorage.getItem("user")) {
      let user = JSON.parse(sessionStorage.getItem("user"));
      this.username = user.userName;
    };
    this.$bus.$on('userUpdate', (data) => {
      this.$router.push({
        name: 'modifyUser',
        params: {
          info: data,
        }
      })
    });
    this.$bus.$on('addUser', (data) => {
      this.$router.push({
        path: '/admin/addUser'
      })
    });
  },
  beforeDestroy() {
    // this.$bus.$off('showMyFile');  //解绑
    // this.$bus.$off('searchMyFile');  //解绑
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