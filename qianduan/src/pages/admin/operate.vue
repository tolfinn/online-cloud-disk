<template>
    <div>
        <!-- 用户信息修改 -->
        <div style="margin-bottom:10px">
            <el-button @click="addUser">添加用户</el-button>
        </div>

        <!-- 用户信息修改 -->
        <el-table :data="tableData" border max-height="400" style="width: 100%" @row-contextmenu="rightClick"
            @row-click="clickRow">
            <el-table-column fixed prop="userName" label="用户名" width="130">
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="160">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="230">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="230">
                <template slot-scope="scope">
                    {{ scope.row.createTime | formatTime }}
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="120">
                <template slot-scope="scope">
                    {{ scope.row.status == 1 ? "正常访问" : "已禁用" }}
                </template>
            </el-table-column>
            <!-- <el-table-column prop="zip" label="邮编" width="120">
            </el-table-column> -->
            <el-table-column fixed="right" label="操作" width="150">
                <template slot-scope="scope">
                    <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button> -->
                    <el-button type="text" size="small" @click="handleClick(scope.row)">编辑用户</el-button>
                    <el-button type="text" size="small" @click="handleDelete(scope.row)">删除用户</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>

<script>
import { allowedNodeEnvironmentFlags } from 'process';

export default {
    name: 'operate',
    props: ['search'],
    data() {
        return {
            tableData: [],
        }
    },
    methods: {
        // 修改用户信息
        handleClick(row) {
            console.log(row);
            this.$bus.$emit('userUpdate', row);
        },
        // 删除用户
        handleDelete(row) {
            // alert(row.id);
            this.$confirm('是否删除选中用户？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios.get(
                    "/userInfo/adminDelete?id=" + row.id
                ).then(res => {
                    console.log(res)
                    if (res.code == 6666) {
                        location.reload();
                        this.$message.success("删除成功");
                    }
                }).catch(err => {
                    console.log(err)
                });
            }).catch((meg) => console.log(meg));
        },
        // 添加用户
        addUser() {
            this.$bus.$emit('addUser', '');
        }
    },
    mounted() {
        if (this.search) {
            this.$axios.get(
                "/userInfo/adminQuery?name=" + this.search
            ).then(res => {
                console.log(res)
                if (res.code == 6666) {
                    this.tableData = res.data;
                    
                }
            }).catch(err => {
                console.log(err)
            });
            
        } else {
            // 查找用户信息
            this.$axios.get(
                "/userInfo/admin"
            ).then(res => {
                console.log(res)
                if (res.code == 6666) {
                    this.tableData = res.data;
                }
            }).catch(err => {
                console.log(err)
            });
        }
    },
    watch: {
        search(val) {
            this.$axios.get(
                "/userInfo/adminQuery?name=" + val
            ).then(res => {
                console.log(res)
                if (res.code == 6666) {
                    this.tableData = res.data;
                }
            }).catch(err => {
                console.log(err)
            });
            // location.reload();
        }

    }
}
</script>

<style lang="scss" scoped>
/* // 菜单样式 */
.menuDiv {
    display: none;
    position: absolute;

    .menuUl {
        height: auto;
        width: auto;
        font-size: 14px;
        text-align: left;
        border-radius: 4px;
        border: none;
        background-color: #ffffff;
        color: #606266;
        list-style: none;
        border: 1px solid #ebeef5;

        li {
            width: 140px;
            height: 35px;
            line-height: 35px;
            // padding: 0 px;
            cursor: pointer;
            border-bottom: 1px solid rgba(255, 255, 255, 0.47);

            &:hover {
                display: block;
                background-color: #ecf5ff;
                color: #7abbff;
            }
        }
    }
}
</style>