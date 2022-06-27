<template>
  <div class="register-div">
    <div class="content">
      <div class="cover-div">
        <h3>
          注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册
        </h3>
      </div>
      <div class="content-div">
        <div class="input-div">
          <el-input @input="inputChange" v-model="username" placeholder="用户名称" prefix-icon="el-icon-user">
          </el-input>
          <el-input @input="inputChange" v-model="telephone" placeholder="手机号" prefix-icon="el-icon-user">
          </el-input>
          <el-input @input="inputChange" v-model="mail" placeholder="邮箱" prefix-icon="el-icon-user">
          </el-input>
          <el-input @input="inputChange" v-model="password1" placeholder="密码" :show-password="true"
            prefix-icon="el-icon-key"></el-input>
          <el-input @input="inputChange" v-model="password2" placeholder="确认密码" :show-password="true"
            prefix-icon="el-icon-key"></el-input>
        </div>

        <div class="btn">
          <el-button type="info" plain @click="register1" class="btn-submit">
            注&nbsp;&nbsp;&nbsp;&nbsp;册
          </el-button>
        </div>
        <div style="width:150px; margin-left: 311px; margin-top: 10px; color:slateblue;">
          <a @click="$router.push('/')">已有账号？登录</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'register',
  data() {
    return {
      username: "",
      telephone: "",
      mail: "",
      password1: "",
      password2: "",
    }
  },
  methods: {
    inputChange() {
      this.$forceUpdate();
    },
    register1() {
      if (this.username === "") {
        this.$message.warning("用户名称不能为空")
      } else if (this.userNickname === "") {
        this.$message.warning("用户昵称不能为空")
      } else if (this.telephone === "") {
        this.$message.warning("手机号不能为空")
      } else if (this.mail === "") {
        this.$message.warning("邮箱不能为空")
      } else if (this.password1 === "") {
        this.$message.warning("密码不能为空")
      }
      else if (this.password2 === "") {
        this.$message.warning("确认密码不能为空")
      } else {
        if (this.password1 === this.password2) {
          let params = new URLSearchParams();
          params.append("userName", this.username);
          params.append("password", this.password1);
          params.append("phone", this.telephone);
          params.append("email", this.mail);
          this.$axios.post(
            "/userInfo/userRegister", params
          ).then(res => {
            console.log(res)
            if (res.code == 6666) {
              this.$message.success("注册成功")
              this.$router.push("/");
            } else {
              this.$message.error(res.msg)
            }
          }).catch(err => {
            console.log(err)
          });
        } else {
          this.$message.warning("两次输入的密码不一致,请重新输入")
        }

      }
    }

  }
};
</script>

<style scoped>
.btn {
  width: 100px;
  margin-top: 63px;
  margin-left: -24px;
  display: flex;
}

.register-div {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ecf0f1;
}

.content {
  width: 420px;
  height: 500px;
  border: none;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  overflow: hidden;
}

.cover-div {
  width: 100%;
  height: 25%;
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
  height: 75%;
  background-color: #FFFFFF;
  display: flex;
  flex-direction: column;
  /* justify-content: space-around; */
  align-items: center;
}

.input-div {
  width: 80%;
  height: 40%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  margin-top: 65px;
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
}

.btn-submit {
  width: 100%;
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
