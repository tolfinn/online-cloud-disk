<template>
    <div class="modpas" style="background-color:#FCFCFC;font-family:'宋体';height:100%;">

        <el-col :span="21">
            <!-- 修改密码 -->
            <el-row :span="10">
                <el-card style="margin-top:10px;">
                    <el-form :model="personalForm">
                        <el-row :gutter="12" style="margin-top:20px;">
                            <el-col :span="6">
                                <div style="text-align:right;"><span>原密码：</span></div>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item prop="account">
                                    <el-input type="password" show-password v-model="password" placeholder="请输入您的原密码">
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row :gutter="12" style="margin-top:1px;">
                            <el-col :span="6">
                                <div style="text-align:right;"><span>密码：</span></div>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item prop="password1">
                                    <el-input type="password" show-password v-model="password1" placeholder="请输入新的密码">
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="12" style="margin-top:1px;">
                            <el-col :span="6">
                                <div style="text-align:right;"><span>确认密码：</span></div>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item prop="password2">
                                    <el-input type="password" show-password v-model="password2" placeholder="请再次输入新的密码">
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row :gutter="12" style="margin-top:10px;">
                            <el-col :span="17" style="text-align:center;">
                                <el-button type="primary" plain round size="medium" @click="submit()">修改</el-button>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-row>
        </el-col>
    </div>
</template>
<script>

export default {
    name: 'modifyPassword',
    data() {
        return {
            password: "",
            password1: "",
            password2: "",
        }
    },
    methods: {
        submit() {  //提交
            if (this.password === "") {
                this.$message.warning("原密码不能为空")
            } else if (this.password1 === "") {
                this.$message.warning("密码不能为空")
            } else if (this.password2 === "") {
                this.$message.warning("密码不能为空")
            } else {
                if (this.password1 !== this.password2) {
                    this.$message.warning("两次输入的密码不一致,请重新输入")
                } else if (this.password === this.password1) {
                    this.$message.warning("新旧密码一致,请重新输入")
                } else if (this.password1 === this.password2) {
                    let params = new URLSearchParams();
                    params.append("password", this.password);
                    params.append("newPwd", this.password2);
                    this.$axios.post(
                        "/userInfo/updatePwd", params
                    ).then(res => {
                        console.log(res)
                        if (res.code == 6666) {
                            //修改本地存储的用户信息
                            sessionStorage.setItem("userToken", res.data.password);
                            console.log('密码修改成功', res);
                            this.$message.success("密码修改成功,请重新登录")
                            this.$router.push("/login")
                        } else {
                            this.$message.error(res.msg);
                        }
                    }).catch(err => {
                        console.log(err)
                    });
                }
            }
        },

    }
}
</script>

<style>
.name {
    text-align: right;
}

.value {
    text-align: left;
}
</style>
