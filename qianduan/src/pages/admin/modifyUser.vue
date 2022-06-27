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
                <el-form-item label="用户状态">
                    <el-select v-model="form.status" placeholder="请选择用户状态">
                        <el-option label="禁用" :value="0"></el-option>
                        <el-option label="正常访问" :value="1"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="onSubmit">提交</el-button>
                    <!-- <el-button>取消</el-button> -->
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    props: ['info'],
    data() {
        return {
            form: {}
        }
    },
    methods: {
        onSubmit() {
            let userInfo = this.form;
            this.$axios.post(
                "/userInfo/adminUpdate",userInfo 
            ).then(res => {
                console.log(res)
                if (res.code == 6666) {
                    // location.reload();
                    this.$message.success("修改成功");
                }
            }).catch(err => {
                console.log(err)
            });
        }
    },
    mounted() {
        this.form = this.info;
        // alert(this.info);
    },
}
</script>

<style>
</style>