<template>
    <div>
        <el-dialog :title="title" :visible.sync="dialogShow" :close-on-click-modal="false">

            <!-- 主要内容 -->

            <el-tree :data="cols" :props="defaultProps" @node-click="handleNodeClick" highlight-current=true>
                <span style="position:relative" class="custom-tree-node" slot-scope="{ node, data }">
                    <span>
                        <img style="margin-top:5px" src="../assets/images/file/dir.png" width="15%" />
                        &nbsp;
                        <span style="position: absolute; bottom: 7px;">
                            {{ node.label | IdToFileName }}
                        </span>
                    </span>
                </span>
            </el-tree>

            <!-- <div style="height:100%;margin-top: -25px; margin-bottom: -25px;"> -->
            <!-- <el-table
                 :data="cols" 
                 style="width: 100%;margin-bottom: 20px;"
                 max-height="250"
                  highlight-current-row
                  @current-change="handleCurrentChange" 
                  :show-header="false"
                 row-key="id" border
                    default-expand-all:false>
                    <el-table-column  >
                    <template slot-scope="scope">
                <img src="../assets/images/file/dir.png" width="5%" />
                &nbsp;
                {{ scope.row.date }}
              </template>
                    </el-table-column>
                </el-table> -->
            <!-- </div> -->


            <span slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    props: {
        // 弹窗显示/隐藏
        dialogVisible: {
            type: Number,
            default: 0
        },
        // 弹窗Title
        title: String,
        // 自定义样式
        customClass: String,
        // 数据列
        cols: {
            type: Array,
            default: () => []
        },
        // 编辑时传入初始值
        data: {
            type: Object,
            default: () => ({})
        }
    },
    data() {
        return {
            submitData: {}, // 提交数据集合
            dialogShow: false,
            // fdata: [{
            //     label: '/a',
            //     children: [{
            //         label: '/a/b',
            //         children: [{
            //             label: '/a/b/c'
            //         }]
            //     }]
            // }, {
            //     label: '/b',
            //     children: [{
            //         label: '/b/c',
            //         children: [{
            //             label: '/b/c/d'
            //         }]
            //     }, {
            //         label: '/b/e',
            //         children: [{
            //             label: '/b/e/f'
            //         }]
            //     }]
            // }, {
            //     label: '/c',
            //     children: [{
            //         label: '/c/a',
            //         children: [{
            //             label: '/c/a/b'
            //         }]
            //     }, {
            //         label: '/c/b',
            //         children: [{
            //             label: '/c/b/e'
            //         }]
            //     }]
            // }],

            defaultProps: {
                children: 'children',
                label: 'label'
            },
        }
    },
    methods: {
        confirm() {
            // 验证必填项
            let isMust = this.cols.filter(item => item.isRequire).map(item => item.key)
            Object.keys(this.submitData).forEach(key => {
                let index = isMust.indexOf(key)
                if ((index > -1) && this.submitData[key] !== '' && !!this.submitData[key]) {
                    isMust.splice(index, 1)
                }
            })
            if (isMust.length > 0) {
                showDefaultTips('请注意必填项!', '', 3)
                return
            }
            console.log(this.submitData);
            this.$bus.$emit('complete', this.submitData)
            this.dialogShow = false
        },
        //取消选择
        cancel() {
            this.dialogShow = false;
            this.$bus.$emit('cancel', this.submitData);
        },
        // 选中某个文件夹
        handleNodeClick(data) {
            console.log('选中某个文件夹', data);
            let arr = data.label.split('/');
            this.title = '移动或复制到：' + arr[arr.length-1];
            this.submitData = { data: data };
        },
        // handleCurrentChange(currentRow, oldCurrentRow) {
        //     console.log(currentRow, oldCurrentRow);
        //     this.title = '移动或复制到：' + currentRow.date;
        // }
    },
    watch: {
        dialogVisible(val) {
            if (val > 0) {
                this.dialogShow = true;
            }
        },
        data: {
            handler(val) {
                this.submitData = val
            },
            immediate: true
        },
        submitData: {
            // 应对 切换单选框隐藏其他元素的问题
            // 父组件监听到单选框的值变化时，修改cols的值，即可实现元素的隐藏与显示
            handler() {
                this.$bus.$emit('change', this.submitData)
            },
            deep: true
        }
    },

}
</script>

<style>
.el-tree-node__content {
    height: 45px;
}
</style>