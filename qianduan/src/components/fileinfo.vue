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
          <!-- 上传文件 -->
          <el-upload class="upload" name="file" :action="doUpload" :before-remove="beforeRemove" :limit="3"
            :on-exceed="handleExceed" :before-upload="handleBeforeUpload" :on-success="uploadSuccess"
            :on-error="uploadFail" :multiple="true" :data="upLoadData">
            <el-button type="primary" round style="
            width: 105px;
            height:30px;
            font-size: 14px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: space-between;
            ">
              <i class="el-icon-upload2" />
              <span style="letter-spacing: 2px;">
                上传
              </span>
            </el-button>
          </el-upload>
        </div>
        <div style=" 
position: absolute; 
left: 155px;
top:-5px">
          <!-- 批量删除 -->
          <el-button @click="batchDel" type="danger" size="mini" round style=" 
          width: 120px;
          height:30px;
          font-size: 14px;
          font-weight: bold;
          display: flex;
          align-items: center;
          justify-content: space-between;
          ">
            <i class="el-icon-delete"></i>&nbsp;&nbsp;批量删除
          </el-button>
        </div>

        <div v-if="title=='我的文件'" style=" 
position: absolute; 
left: 296px;
top:-5px">
          <!-- 新建文件夹 -->
          <el-button @click="addFolder" type="goon" size="mini" round style=" 
          width: 125px;
          height:30px;
          font-size: 14px;
          font-weight: bold;
          display: flex;
          align-items: center;
          justify-content: space-between;
          ">
            <i class="el-icon-folder-add"></i>&nbsp;&nbsp;新建文件夹
          </el-button>
        </div>

        <div style=" position: absolute; left: 752px; top: 5px;">
          <!-- 搜索框 -->
          <el-row>
            <el-col :span="6">
              <el-input type="text" prefix-icon="el-icon-search" v-model="input" placeholder="搜索我的文件..."
                style="width: 240px; cursor: pointer" @keyup.enter.native="searchFile"></el-input>
            </el-col>
          </el-row>
        </div>
      </div>

      <div style="display: flex; font-size: small; font-weight: bold;">

        <!-- 文件路径 -->
        <span v-if="tmp_dir == '' || tmp_dir == '/june1234'">
          {{ title }}
        </span>
        <div v-else style="height:30px">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item class="home_icon">
              <el-tooltip class="item" effect="dark" content="根目录" placement="top">
                <a @click="toHome"> <i class="el-icon-s-home"></i></a>
              </el-tooltip>
            </el-breadcrumb-item>

            <el-breadcrumb-item v-for="(item) of tmp_dir_arr" :key="item.id">
              <span @click="showFolder(item)">
                <a>{{ item.filename }}</a>
              </span>
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <!-- <a v-else >{{tmp_dir}}</a> -->
      </div>

      <div style="width: 100%;height: 86%;display: flex;flex-direction: row;">
        <div style="width: 75%;height: 100%;border-right: 1px solid #f0f0f0">
          <!-- 查询到的内容 -->
          <el-table :row-class-name="rowClassName" ref="multipleTable" :data="file_list" tooltip-effect="dark"
            height="410" style="width: 100%" @row-contextmenu="rightClick" @row-click="clickRow"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column :label="getLabelName" width="350" show-overflow-tooltip>
              <template slot-scope="scope">
                <!-- 视频文件 -->
                <img v-if="['mp4', 'avi'].indexOf((scope.row.type).toLowerCase()) !== -1"
                  src="../assets/images/file/file_avi.png" :width="imgWid" />
                <!-- 图片文件 -->
                <img v-else-if="['jpg', 'png'].indexOf((scope.row.type).toLowerCase()) !== -1"
                  src="../assets/images/file/file_pic.png" :width="imgWid" />
                <!-- txt文件 -->
                <img v-else-if="scope.row.type == 'txt'" src="../assets/images/file/file_txt.png" :width="imgWid" />
                <!-- word文件 -->
                <img v-else-if="['docx', 'doc'].indexOf((scope.row.type).toLowerCase()) !== -1"
                  src="../assets/images/file/file_word.png" :width="imgWid" />
                <!-- pdf文件 -->
                <img v-else-if="scope.row.type == 'pdf'" src="../assets/images/file/file_pdf.png" :width="imgWid" />
                <!-- ppt文件 -->
                <img v-else-if="['pptx', 'ppt'].indexOf((scope.row.type).toLowerCase()) !== -1"
                  src="../assets/images/file/file_ppt.png" :width="imgWid" />
                <!-- 音频文件 -->
                <img v-else-if="['mp3', 'avi'].indexOf((scope.row.type).toLowerCase()) !== -1"
                  src="../assets/images/file/file_music.png" :width="imgWid" />
                <!-- md文件 -->
                <img v-else-if="scope.row.type == 'md'" src="../assets/images/file/file_markdown.png" :width="imgWid" />
                <!-- 目录 -->
                <img v-else-if="scope.row.type == 'dir'" src="../assets/images/file/dir.png" :width="imgWid" />
                <!-- 未知文件 -->
                <img v-else src="../assets/images/file/file_unknown.png" :width="imgWid" />
                &nbsp;
                {{ scope.row.fileName }}
              </template>
            </el-table-column>
            <el-table-column prop="updateTime" label="修改时间" width="220" show-overflow-tooltip sortable>
              <template slot-scope="scope">
                {{ scope.row.updateTime | formatTime }}
              </template>
            </el-table-column>
            <el-table-column prop="size" label="大小" width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.size | bytesToSize }}
              </template>
            </el-table-column>
            <el-table-column v-if="false" prop="url" label="url" />
            <el-table-column v-if="false" prop="index" label="index" />
          </el-table>

          <div class="page" style="display:none">
            <!-- <span class="demonstration">显示总数</span> -->
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
              :current-page.sync="currentPage1" :page-size="100" layout="total, prev, pager, next" :total="1000">
            </el-pagination>
          </div>

        </div>

        <!-- 文件详情展示 -->
        <div style="width: 25%;height: 100%; padding: 10px;">
          <SearchinfoVue :row="row"></SearchinfoVue>
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

    <!-- 预览 -->
    <div class="preview" v-if="IsPreview">
      <file-preview :key="timers" :TragetPic="TragetPic" :FilePreAll="FilePreAll" @Close="onClickClosePreview">
      </file-preview>
    </div>

    <!-- 移動文件夾 -->
    <Choosefile :dialogVisible="dialogVisible" title="移动或复制到：我的文件" :cols="cols"></Choosefile>
  </div>
</template>


<script>
import SearchinfoVue from './searchinfo.vue';
import FilePreview from '../pages/file/box/FilePreview.vue'
import Choosefile from './choosefile.vue';
// import {download} from '../request/download'
export default {
  name: "fileinfo",
  components: { SearchinfoVue, FilePreview, Choosefile },
  props: ['list', 'path', 'title'],
  data() {
    return {
      input: '',  //文件搜索框搜索内容
      file_list: [], //文件列表
      // loading:true, //列表是否加载
      // doUpload: 'http://192.168.3.22:80' + '/file/upload',
      doUpload: 'http://192.168.42.10:80' + '/file/upload',
      tmp_dir: '/june1234', //当前目录路径
      upLoadData: {},
      tmp_dir_arr: [], //当前目录数组{filename：当前所在文件夹，filepath：当前所在文件夹路径,id:..}
      dialogVisible: 0, //移動文件夾選項是否顯示
      cols: [    //文件夹树
        {
          label: '/june1234',
          children: [
            { label: '/june1234/img', children: [] },
            {
              label: '/june1234/我的视频',
              children: [
                {
                  label: '/june1234/我的视频/dir1',
                  children: []
                },
                {
                  label: '/june1234/我的视频/dir2',
                  children: []
                }
              ]
            },
            {
              label: '/june1234/我的文件1',
              children: [
                {
                  label: '/june1234/我的文件1/dir3',
                  children: [
                    {
                      label: '/june1234/我的文件1/dir3/dir4',
                      children: []
                    }
                  ]
                }
              ]
            }
          ]
        }],
      imgWid: "5%",  //图标大小
      selectedFile: {}, //鼠标右键选中的文件条目
      multipleSelection: [], //记录选择条目
      row: {}, //选中某行的文件信息
      //右键菜单项
      menus: [
        { name: "预览", operType: 1, icon: "el-icon-view" },
        { name: "收藏", operType: 2, icon: "el-icon-star-off" },
        { name: "移动", operType: 2, icon: "el-icon-rank" },
        { name: "重命名", operType: 3, icon: "el-icon-edit-outline" },
        { name: "删除", operType: 4, icon: "el-icon-delete" },
        { name: "下载", operType: 5, icon: "el-icon-download" },
        { name: "分享", operType: 6, icon: "el-icon-share" },
      ],
      // -----预览数据-----
      IsPreview: false,
      timers: "",
      FilePreAll: [],
      TragetPic: {}, // 当前点击的预览文件对象
    };
  },
  methods: {
    rowClassName({ row, rowIndex }) {
      //把每一行的索引放进row.id
      row.index = rowIndex;
    },
    searchFile() {
      // alert('搜索的内容'+this.input);
      this.$bus.$emit('searchMyFile', this.input);
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

    toHome() {
      this.$bus.$emit('showMyFile', '/june1234');
      this.tmp_dir = '/june1234';
    },
    // 文件上传
    handleBeforeUpload(file) {
      const uploadLimit = 20
      // const uploadTypes = ['jpg', 'png', 'txt', 'doc', 'docx', 'xlsx', 'zip', 'rar', 'pdf']
      // const filetype = file.name.replace(/.+\./, '')
      const isRightSize = (file.size || 0) / 1024 / 1024 < uploadLimit
      if (!isRightSize) {
        this.$message.error('文件大小超过 ' + uploadLimit + 'MB')
        return false
      }

      // if (uploadTypes.indexOf(filetype.toLowerCase()) === -1) {
      //   this.$message.warning({
      //     message: '请上传后缀名为jpg、png、txt、pdf、doc、docx、xlsx、zip或rar的附件'
      //   })
      //   return false
      // }
      return true
    },

    // 批量删除
    batchDel() {
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
          "/file/recycleA", params
        ).then(res => {
          console.log(res)
          if (res.code == 6666) {
            location.reload();
          }
        }).catch(err => {
          console.log(err)
        });
        this.$message.success("删除成功")
      }).catch((meg) => console.log(meg));
    },

    //上传成功
    uploadSuccess(res) {
      //提示信息
      console.log(res),
        console.log('upLoadData 上传路径', this.upLoadData)
      this.$bus.$emit('showMyFile', this.tmp_dir);
      this.$message({
        type: 'success',
        message: '上传成功'
      })
// location.reload();
    },
    //上传失败
    uploadFail() {
      this.$message({
        type: 'error',
        message: '上传失败'
      })
    },

    addFolder() {
      this.$prompt("请输入新文件夹名：", "新建文件夹", {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        console.log(value)
        if (!value) {
          return false;
        }
        let params = new URLSearchParams();
        params.append("parentDir", this.tmp_dir);
        params.append("fileName", value);
        this.$axios.post("/file/mkdir", params)
          .then(res => {
            console.log('新建文件夹：', res);
            this.$message.success("操作成功");
            //更改file_list
            this.file_list.unshift(res.data);
          }).catch(err => {
            console.log(err);
          });
      }).catch((data) => { console.log(data) })
    },

    // -----------------菜单功能----------------
    // 表格右击的功能
    rightClick(row, column, event) {
      console.log(row, column, event);
      //修改子菜单的收藏图标，将其变为默认的未收藏
      this.menus[1].icon = "el-icon-star-off";
      this.$refs.multipleTable.toggleRowSelection(row);
      this.selectedFile = row;
      // console.log('右键菜单this.selectedFile:', this.selectedFile);
      event.preventDefault();
      let menu = document.querySelector("#menu");
      // 根据事件对象中鼠标点击的位置，进行定位
      menu.style.left = event.clientX + "px";
      // console.log(event.clientY);
      if (event.clientY >= 290) {
        menu.style.top = 290 + "px";
      } else {
        menu.style.top = event.clientY - 10 + "px";
      }
      //如果该row被收藏 则更改菜单的图标
      console.log('iscollect', row.iscollect)
      // console.log(this.menus[1])
      if (row.iscollect == 1) {
        this.menus[1].icon = "el-icon-star-on";
      }
      // 改变自定义菜单的隐藏与显示
      menu.style.display = "block";
      menu.style.zIndex = 900;
    },

    // table的左键点击当前行事件
    clickRow(row, column, event) {
      console.log('table的左键点击当前行事件', row, column, event);
      //选中该条目
      this.$refs.multipleTable.toggleRowSelection(row);
      // 隐藏右键菜单
      let menu = document.querySelector("#menu");
      menu.style.display = "none";
      //展示文件信息 将row递交给信息显示组件
      this.row = row;
      //修改子菜单的收藏图标，将其变为默认的未收藏
      this.menus[1].icon = "el-icon-star-off";

      //如果点击的是目录 展示目录里的内容 
      if (row.type == 'dir') {
        //跳转页面
        this.$bus.$emit('showMyFile', row.fileId);
        //1. 显示当前所在文件夹
        this.tmp_dir = row.fileId;
      }
    },
    // 右击自定义菜单的点击事件
    infoClick(index) {
      console.log("要做的事情:", index);
      if (index === 0) {  //预览
        this.PreviewFile();
      } else if (index === 1) {  //收藏
        this.handleFileCollect(this.selectedFile);
      } else if (index === 2) { // 移动
        this.dialogVisible = 1;
        this.$axios.get("/file/getalldir")
          .then(res => {
            console.log(res);
            if (res.code == 6666) {
              this.cols = res.data;
            }
          }).catch(err => {
            console.log(err);
          });
        // this.dialogVisible = 0;
      } else if (index === 3) { // 重命名
        this.handleFileName(this.selectedFile);
      } else if (index === 4) {  // 删除
        this.handleFileRemove(this.selectedFile);
      } else if (index === 5) { // 下载
        console.log(this.selectedFile);
        let url = this.$axios.defaults.baseURL + "/file/download?fileid=" + this.selectedFile.fileId;
        window.open(url);
      } else if (index === 6) { //分享

      }
      let menu = document.querySelector("#menu");
      menu.style.display = "none";
    },


    // -------------预览—----------
    PreviewFile() {
      this.$message.success('预览：'+this.selectedFile.fileName);
      let file = this.selectedFile;
      this.TragetPic = {
        FileName: this.selectedFile.fileName, // 文件名称
        // name: val.name, // 文件名称（可以不传）
        id: 12, // 文件id
        format: this.selectedFile.type, // 文件格式
        url: file.url,  // 预览地址
        downUrl: 'http://img.hb.aicdn.com/38ab9e558bcba041be979f03bfd31bd67bf1e6f35815a-8PD8Eo_fw658', // 下载地址
      };
      this.IsPreview = true;
    },
    // 打开预览
    onClickOpenPreview(val) {
      this.TragetPic = {
        FileName: val.name,
        name: val.name,
        id: val.guid_name,
        format: val.extensions,
        url: this.previewFile + val.guid_name,
        downUrl: "", // 下载地址
      }; // 目标对象
      this.tableData.forEach(item => {
        let obj = {
          FileName: item.name,
          name: item.name,
          id: item.guid_name,
          format: item.extensions,
          url: this.previewFile + item.guid_name,
          downUrl: "",
        };
        this.FilePreAll.push(obj);
      });
      this.IsPreview = true; // 打开预览弹窗
      this.timers = new Date().getTime(); // 刷新预览地址
    },
    // 关闭预览
    onClickClosePreview(val) {
      this.IsPreview = val; // 由组件内部传入的关闭数据赋值关闭
    },

    // 右键菜单的一系列操作
    // 重命名
    handleFileName(file) {//修改名字
      console.log('修改名字',file.fileId)
      let i = file.index;
      let that = this;
      this.$prompt("请输入新文件名：", "Rename", {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        console.log('value:？？？？？',value)
        let params = new URLSearchParams();
        params.append("fileid", file.fileId);
        params.append("filename", value);
        this.$axios.post(
          "/file/rename", params
        ).then(res => {
          console.log(res)
          if (res.code == 6666) {
            // location.reload();
            // 修改数组
            if (that.file_list[i].type == 'dir') {
              that.file_list[i].fileName = value;
            } else {
              that.file_list[i].fileName = value + '.' + that.file_list[i].type;
            }
            this.$message.success("重命名成功")
          }else {
            this.$message.error(res.msg)
          }
        }).catch(err => {
          console.log(err)
        });

      }).catch(() => { })
    },

    handleFileCollect(item) { //收藏
      let key = item.index;
      console.log('collect!')
      if (item.iscollect == 1) {
        this.$axios.get("/file/rmCollect?id=" + item.id)
          .then(res => {
            console.log(res);
          }).catch(err => {
            console.log(err);
          });
        console.log('取消收藏');
        item.iscollect = 0
        this.$message({
          type: 'success',
          message: '已取消收藏',
        })
      } else {
        this.$axios.get("/file/addCollect?id=" + item.id)
          .then(res => {
            console.log(res);
          }).catch(err => {
            console.log(err);
          });
        console.log('收藏成功');
        item.iscollect = 1
        this.$message({
          type: 'success',
          message: '收藏成功',
        })
      }
    },
    handleFileRemove(file) {//删除
      let i = file.index;
      let that = this;
      this.$confirm('是否删除此文件？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //可添加axios
        this.$axios.get(
          "/file/addRecycle?id=" + file.id,
        ).then(res => {
          console.log(res)
          if (res.code == 6666) { //请求成功
            this.$message({
              type: 'success',
              message: '删除成功',
              onClose: () => {
                that.file_list.splice(i, 1)
              }
            })
            // this.$router.push("/index/myfile");
          } else {
            this.$message(res.msg);
          }
        }).catch(err => {
          console.log(err)
        });
      }).catch((meg) => console.log(meg))
    },

    // 展示对应文件夹内容
    showFolder(item) {
      // alert(item.filepath);
      // 返送请求，展示 文件夹内容
       this.$bus.$emit('showMyFile', item.filepath);
    },
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
    this.upLoadData = { fileId: this.tmp_dir };
    this.$bus.$on('complete', (data) => {
      console.log('我是fileinfo组件，收到了数据', data);
      this.dialogVisible = 0;
      console.log('fileId', this.selectedFile.fileId, 'parentId', data.data.label)
      let params = new URLSearchParams();
      params.append("fileid", this.selectedFile.fileId);
      params.append("parentDir", data.data.label);
      this.$axios.post(
        "/file/remove", params
      ).then(res => {
        console.log(res)
        if (res.code == 6666) {
          location.reload();
          this.$message.success("移动成功")
        }
      }).catch(err => {
        console.log(err)
      });
    })
    this.$bus.$on('cancel', (data) => {
      // console.log('我是fileinfo组件，收到了数据',data);
      this.dialogVisible = 0;
    })
  },
  beforeDestroy() {
    //解绑
    this.$bus.$off('complete');
    this.$bus.$off('cancel');
  },
  watch: {
    // 将props的值给data
    list(val) {
      console.log(val, '!!!')
      this.file_list = val;
    },
    path(val) {
      this.tmp_dir = val;
    },
    // 显示文件路径
    tmp_dir(val) {
      // 清空数组
      this.tmp_dir_arr.splice(0, this.tmp_dir_arr.length);
      console.log('tmp_dir', val);
      let arr = val.split('/');
      console.log('arr', arr);
      let cnt = 0;
      for (let i = 0; i < val.length; i++) {
        if (val[i] == '/') {
          ++cnt;
          if (cnt > 2) {
            this.tmp_dir_arr.push({
              filename: arr[cnt - 1],
              filepath: val.slice(0, i),
              id: i
            });
          }
        }
      }
      //最后一个文件夹位置
      this.tmp_dir_arr.push({
        filename: arr[cnt],
        filepath: val.slice(0, val.length + 1),
        id: val.length + 1
      });
      console.log('this.tmp_dir_arr', this.tmp_dir_arr);

      this.upLoadData = { fileId: this.tmp_dir };
      console.log('this.upLoadData', this.upLoadData)
    },

    file_list(val) {
      this.loading = false;
    }
  }

}
</script>

<style lang="scss" scoped>
// 不显示上传的文件列表 （代码写在app.vue中）

// 图标效果
.home_icon {
  // font-size: 30px;
  color: #606266
}

.el-breadcrumb {
  font-size: 14px;
  line-height: 3;
}

/* 自定义elbutton颜色 */
.el-button--goon.is-active,
.el-button--goon:active {
  background: #20B2AA;
  border-color: #20B2AA;
  color: #fff;
}

.el-button--goon:focus,
.el-button--goon:hover {
  background: #48D1CC;
  border-color: #48D1CC;
  color: #fff;
}

.el-button--goon {
  color: #FFF;
  background-color: #20B2AA;
  border-color: #20B2AA;
}

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
