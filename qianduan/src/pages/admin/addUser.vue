<template>
    <div>

        <div style="width:40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="用户名">
                    <el-input v-model="form.userName"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input type="password" v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="onSubmit">添加</el-button>
                    <!-- <el-button>取消</el-button> -->
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            form: {
                userName: '',
                password: '',
                phone: '',
                email: '',
            },
        }
    },
    methods: {
        onSubmit() {
            let params = new URLSearchParams();
            params.append("userName", this.form.userName);
            params.append("password", this.form.password);
            params.append("phone", this.form.phone);
            params.append("email", this.form.email);
            this.$axios.post(
                "/userInfo/userRegister", params
            ).then(res => {
                console.log(res)
                if (res.code == 6666) {
                    this.$message.success("用戶添加成功")
                    this.$router.push("/admin/operate");
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(err => {
                console.log(err)
            });
        }
    },
}
</script>

<style>
</style>