<template>
  <div class="login-div">
    <div style="display: flex; background-color:#FAFAFA;font-family:'宋体';height:450px; width: 99%;">

      <div class="form_div">
        <span>修改个人信息</span>
        <br /><br />
        <el-input @input="inputChange" v-model="nickName" placeholder="昵称" class="input"
          prefix-icon="el-icon-s-check" />
        <br />
        <el-input @input="inputChange" v-model="telephone" placeholder="手机号" class="input"
          prefix-icon="el-icon-phone" />
        <br />
        <el-input @input="inputChange" v-model="mail" placeholder="邮箱" class="input" prefix-icon="el-icon-message" />

        <div class="btn">
          <el-button type="info" plain @click="modifyUserInfo" class="btn-submit">
            修&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;改
          </el-button>
        </div>
      </div>

      <div class="content_div" style="text-align:center">
        <span style="font-weight: bold; font-size: large;"> 用户存储空间展示</span>
        <Content></Content>
      </div>
    </div>
  </div>
</template>
<script>
import Content from '../content.vue';
export default {
  name: "modifyInformation",
  components: { Content },
  data() {
    return {
      telephone: "",
      mail: "",
      nickName: '',
    };
  },
  methods: {
    inputChange() {
      this.$forceUpdate();
    },
    modifyUserInfo() {
      if (this.telephone == "") {
        this.$message.warning("输入的手机号为空！");
      } else if (this.mail == "") {
        this.$message.warning("输入的邮箱为空！");
      } else if (this.nickName == "") {
        this.$message.warning("输入的昵称为空！");
      } else { //修改用户基本信息
        let params = new URLSearchParams();
        params.append("nickname", this.nickName);
        params.append("phone", this.telephone);
        params.append("email", this.mail);
        this.$axios.post(
          "/userInfo/updateMsg", params
        ).then(res => {
          console.log(res)
          if (res.code == 6666) {
            //修改本地存储的用户信息
            sessionStorage.setItem("user", JSON.stringify(res.data));
            this.nickName = res.data.nickName;
            this.mail = res.data.email;
            this.telephone = res.data.phone;
            console.log('用户信息修改成功', res);
            // location.reload();
            this.$message.success("修改成功");
          } else {
            this.$message.error(res.msg);
          }
        }).catch(err => {
          console.log(err)
        });
      }
    }
  },
  mounted() {
    let user = JSON.parse(sessionStorage.getItem("user"));
    this.nickName = user.nickName;
    this.mail = user.email;
    this.telephone = user.phone;
  },
};
</script>

<style scoped>
.form_div {
  width: 40%;
  margin-top: 40px;
  margin-left: 40px;
}

.el-input {
  margin-bottom: 10px;
}

.btn {
  text-align: center;
  margin-top: 10px;
  margin-left: -50px;
}

.el-button {
  font-weight: bold;
}

.content_div {
  margin-top: 30px;
  margin-left: 120px;
}
</style>
