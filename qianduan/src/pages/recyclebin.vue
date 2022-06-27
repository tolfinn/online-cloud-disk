<template>
    <div>
        <div style="width: 100%;height: 100%;display: flex;flex-direction: column;">

            <div style="width: 100%;
        height: 27px;
        display: flex;
        align-items: center;
        justify-content: space-between;
         margin-bottom: 11px;
         position: relative;
        ">
                <div style="
position: absolute;
top:-5px;
left: 25px;">
                    <!-- 批量删除 -->
                    <el-button @click="batchDel" type="primary" size="small" round style=" 
          width: 130px;
          height:40px;
          font-size: 14px;
          font-weight: bold;
          display: flex;
          align-items: center;
          justify-content: space-between;
          ">
                        <i class="el-icon-takeaway-box"></i>&nbsp;&nbsp;清空回收站
                    </el-button>
                </div>

                <div style=" 
position: absolute; 
left: 175px;
top:-5px">
                    <!-- 选中批量删除 -->
                    <el-button @click="selectBatchDel" type="danger" size="mini" round style=" 
          width: 120px;
          height:40px;
          font-size: 14px;
          font-weight: bold;
          display: flex;
          align-items: center;
          justify-content: space-between;
          ">
                        <i class="el-icon-delete"></i>&nbsp;&nbsp;批量删除
                    </el-button>
                </div>
            </div>

            <div style="margin-top: 20px; width: 100%;height: 86%;">
                <div style="width: 100%;height: 100%;border-right: 1px solid #f0f0f0">

                    <!-- 查询到的内容 -->
                    <el-table :row-class-name="rowClassName" ref="multipleTable" :data="file_list" tooltip-effect="dark"
                        max-height="400" style="width: 100%" @row-contextmenu="rightClick" @row-click="clickRow"
                        @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="55">
                        </el-table-column>
                        <el-table-column :label="getLabelName" width="400" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <img v-if="scope.row.type == 'mp4'" src="../assets/images/file/file_avi.png"
                                    width="5%" />
                                <img v-else-if="['jpg', 'png'].indexOf((scope.row.type).toLowerCase()) !== -1"
                                    src="../assets/images/file/file_pic.png" width="5%" />
                                <!-- 音频文件 -->
                                <img v-else-if="['mp3', 'avi'].indexOf((scope.row.type).toLowerCase()) !== -1"
                                    src="../assets/images/file/file_music.png" width="5%" />
                                <img v-else-if="scope.row.type == 'txt'" src="../assets/images/file/file_txt.png"
                                    width="5%" />
                                <img v-else-if="scope.row.type == 'dir'" src="../assets/images/file/dir.png"
                                    width="5%" />
                                <img v-else src="../assets/images/file/file_unknown.png" width="5%" />

                                &nbsp;
                                {{ scope.row.fileName }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="size" label="大小" width="100" show-overflow-tooltip>
                            <template slot-scope="scope">
                                {{ scope.row.size | bytesToSize }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="updateTime" label="删除时间" width="220" show-overflow-tooltip>
                            <template slot-scope="scope">
                                {{ scope.row.updateTime | formatTime }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="0" label="有效时间" width="220" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column v-if="false" prop="url" label="url" />
                        <el-table-column v-if="false" prop="index" label="index" />
                    </el-table>

                </div>
            </div>
        </div>


        <!-- 菜单 -->
        <div id="menu" class="menuDiv">
            <ul class="menuUl">
                <li v-for="(item, index) in menus" :key="index" @click.stop="infoClick(index)">
                    <i :class="item.icon"></i> {{ item.name }}
                </li>
            </ul>
        </div>

    </div>
</template>

<script>
export default {
    name: 'recyclebin',
    // props: ['file_list'],
    data() {
        return {
            file_list: [],
            selectedFile: {}, //鼠标右键选中的文件条目
            multipleSelection: [], //记录选择条目
            //右键菜单项
            menus: [
                { name: "彻底删除", operType: 1, icon: "el-icon-delete-solid" },
                { name: "还原", operType: 2, icon: "el-icon-refresh-right" },
            ],
        };
    },
    methods: {
        rowClassName({ row, rowIndex }) {
            //把每一行的索引放进row.id
            row.index = rowIndex;
        },
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },

        // -----------------菜单功能----------------
        // 表格右击的功能
        rightClick(row, column, event) {
            // this.$refs.multipleTable.toggleRowSelection(row);
            console.log(row, column, event);
            this.selectedFile = row;
            console.log('右键菜单this.selectedFile:', this.selectedFile);
            event.preventDefault();
            let menu = document.querySelector("#menu");
            // 根据事件对象中鼠标点击的位置，进行定位
            menu.style.left = event.clientX + "px";
            menu.style.top = event.clientY - 10 + "px";
            // 改变自定义菜单的隐藏与显示
            menu.style.display = "block";
            menu.style.zIndex = 900;
        },
        // table的左键点击当前行事件
        clickRow(row, column, event) {
            console.log('table的左键点击当前行事件', row, column, event);
            this.$refs.multipleTable.toggleRowSelection(row);
            let menu = document.querySelector("#menu");
            menu.style.display = "none";
        },
        // 右击自定义菜单的点击事件
        infoClick(index) {
            console.log("要做的事情:", index);
            if (index === 0) {  //彻底删除
                this.handleFileRemove(this.selectedFile);
            } else if (index === 1) {  //还原
                this.handleFileReduction(this.selectedFile);
            }
            let menu = document.querySelector("#menu");
            menu.style.display = "none";
        },

        //选中还原
        handleFileReduction(file) {
            // alert('还原');
            this.$axios.get("/file/rmRecycle?id=" + file.id)
                .then(res => {
                    console.log(res);
                    if (res.code == 6666) {
                        location.reload();
                        this.$message({
                            type: 'success',
                            message: '还原成功',
                            onClose: () => {
                                that.file_list.splice(i, 1)
                            }
                        })
                    }
                }).catch(err => {
                    console.log(err);
                });
        },
        // 选中删除
        handleFileRemove(file) {//删除
            let i = file.index;
            let that = this;
            this.$confirm('是否删除此文件？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios.get("/file/delete?id=" + file.id)
                    .then(res => {
                        console.log(res);
                        if (res.code == 6666) {
                            this.$message({
                                type: 'success',
                                message: '删除成功',
                                onClose: () => {
                                    that.file_list.splice(i, 1)
                                }
                            })
                        }
                    }).catch(err => {
                        console.log(err);
                    });
            }).catch((meg) => console.log(meg))
        },
        // 清空回收站
        batchDel() {
            this.$confirm('是否要清空回收站？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios.get(
                    "/file/removeAll"
                ).then(res => {
                    console.log(res)
                    if (res.code == 6666) {
                        this.file_list.splice(0, this.file_list.length);
                        this.$message({
                            type: 'success',
                            message: '回收站已清空',
                        })
                    }
                }).catch(err => {
                    console.log(err)
                });
            }).catch((meg) => console.log(meg))
        },
        // 选中批量删除
        selectBatchDel() {
            console.log(this.multipleSelection);
            let arr = [];//保存Id
            for (let i = 0; i < this.multipleSelection.length; i++) {
                arr.push(this.multipleSelection[i].id);
            }
            console.log('要删除的文件id', arr);
            this.$confirm('是否批量删除选中文件？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let params = new URLSearchParams();
                params.append("ids", arr);
                this.$axios.post(
                    "/file/deleteA", params
                ).then(res => {
                    console.log(res)
                    if (res.code == 6666) {
                        this.$message.success("删除成功")
                        location.reload();
                    }
                }).catch(err => {
                    console.log(err)
                });
            }).catch((meg) => console.log(meg));
        }
    },
    computed: {
        getLabelName() {
            var len = this.multipleSelection.length;
            if (len === 0) {
                return '文件名称';
            }
            return '已选中' + len + '个文件/文件夹';
        },
    },
    mounted() {
        this.$axios.get(
            "/file/recycle"
        ).then(res => {
            console.log(res)
            if (res.code == 6666) { //请求成功
                this.file_list = res.data;
            } else {
                this.$message(res.msg);
            }
        }).catch(err => {
            console.log(err)
        });
    },
}
</script>


<style lang="scss" scoped>
.page {
    margin-top: 10px;
}

.midbutton {
    position: relative;
    left: -255px;
    margin-left: 10px;
}

.top {
    display: flex;
}

.el-pagination {
    display: flex;
    justify-content: center;
    padding-top: 20px;
}

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