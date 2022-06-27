<template>
  <div class="login-div">
    <div class="content">
      <div class="cover-div">
        <h3>
          轻工云盘
        </h3>
      </div>
      <div class="content-div">

        <div class="input-div">
          <el-input v-model="username" placeholder="用户/手机号/邮箱" class="input" prefix-icon="el-icon-user" />
          <el-input v-model="password" placeholder="密码" :show-password="true" class="input" prefix-icon="el-icon-key" />

          <div style="width:90% ;">
            <SliderVerifyCode v-model="isLock">
            </SliderVerifyCode>
          </div>
        </div>
        <div class="submit-div">
          <el-button type="info" plain @click="login" class="btn-submit">
            登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录
          </el-button>
        </div>
        <div class="btn-div">
          <div>
            <i slot="prefix" class="el-input__icon el-icon-lock"></i>
            <el-button type="text">忘记密码？</el-button>
          </div>
          <el-button type="text" @click="register">注册新账号</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SliderVerifyCode from '../components/slider-verify-code.vue';

export default {
  name: "login",
  components: { SliderVerifyCode },
  data() {
    return {
      username: "",
      password: "",
      isLock: true,
    };
  },
  methods: {
    login() {
      if (this.isLock) {
        this.$message({
          showClose: true,
          message: '请先滑动解锁！',
          type: 'warning'
        });
        return;
      }
      let params = new URLSearchParams();
      params.append("userName", this.username);
      params.append("password", this.password);
      console.log(params, this.username, this.password);
      // `${this.$config.baseContext}/userInfo/userLogin`  http://192.168.9.10
      // this.$router.push("index");
      // this.$router.push("/index/myfile?path=/home");
      // 登录时请求首页文件信息进行展示
      this.$axios.post("/userInfo/userLogin", params).then(res => {
        console.log(res);
        if (res.code == 6666) { //请求成功
          //1.判断用户身份。。
          if (res.data.status == 0) {
            this.$message.error('账号已被冻结，暂无访问权限');
            return;
          }
          //2.storage 和 vuex保存用户数据
          //将用户名放入sessionStorage中
          sessionStorage.setItem("user", JSON.stringify(res.data));
          sessionStorage.setItem("userToken", res.data.password);
          //将用户名放入vuex中
          this.$store.dispatch("setUser", JSON.stringify(res.data));
          this.$store.dispatch("setToken", res.data.password);
          //打印login状态
          console.log('打印login状态', this.$store.state.isLogin);
          //3.查询首页需要的信息
          //4.路由跳转
          if (res.data.level == 1) {
            this.$router.push("/index/myfile");
          } else {
            this.$router.push("/admin/operate");
          }
        }
        else {
          this.$message.error(res.msg);
        }
      }).catch(err => {
        console.log(err);
      });
    },
    register() {
      this.$router.push("register");
    },

  },
  mounted() {
    this.$bus.$on('verifyChange', (data) => {
      this.isLock = !data;
    });
  },
};
</script>

<style scoped>
.login-div {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ecf0f1;
}

.content {
  width: 420px;
  height: 430px;
  border: none;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  overflow: hidden;
}

.cover-div {
  width: 100%;
  height: 35%;
  background-image: url('../assets/bg.jpg');
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #FFFFFF;
  letter-spacing: 10px;
}

.content-div {
  width: 100%;
  height: 65%;
  background-color: #FFFFFF;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.input-div {
  width: 100%;
  height: 40%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  margin-top: 25px;
}

.el-input {
  margin-bottom: 10px;
}

.input {
  width: calc(100% - 50px);
}

.submit-div {
  width: 100%;
  height: 20%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
}

.btn-submit {
  width: 30%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #2b4049;
  color: white;
  font-size: large;
}

.btn-div {
  width: 80%;
  height: 20%;
  display: flex;
  justify-content: space-between;
}

.btn-div>div {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
