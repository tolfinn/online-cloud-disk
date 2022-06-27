<template>
    <div>
        <!-- 文件详情 -->
        <!-- 未选中文件时，展示图片 -->
        <div v-if="row.id === undefined" style="margin-left:45px ;margin-top:89px">
            <img width="80%" src="../assets/pls_select.png" />
        </div>
        <div v-else>
            <!-- 图片 -->
            <div class="show_img">
                <!-- 视频文件 -->
                <img v-if="['mp4', 'avi'].indexOf((row.type).toLowerCase()) !== -1"
                    src="../assets/images/file/file_avi.png" :width="imgWid" />
                <!-- 图片文件 -->
                <img v-else-if="['jpg', 'png'].indexOf((row.type).toLowerCase()) !== -1"
                    src="../assets/images/file/file_pic.png" :width="imgWid" />
                <!-- txt文件 -->
                <img v-else-if="row.type == 'txt'" src="../assets/images/file/file_txt.png" :width="imgWid" />
                <!-- word文件 -->
                <img v-else-if="['docx', 'doc'].indexOf((row.type).toLowerCase()) !== -1"
                    src="../assets/images/file/file_word.png" :width="imgWid" />
                <!-- pdf文件 -->
                <img v-else-if="row.type == 'pdf'" src="../assets/images/file/file_pdf.png" :width="imgWid" />
                <!-- ppt文件 -->
                <img v-else-if="['pptx', 'ppt'].indexOf((row.type).toLowerCase()) !== -1"
                    src="../assets/images/file/file_ppt.png" :width="imgWid" />
                <!-- 音频文件 -->
                <img v-else-if="['mp3', 'avi'].indexOf((row.type).toLowerCase()) !== -1"
                    src="../assets/images/file/file_music.png" :width="imgWid" />
                <!-- md文件 -->
                <img v-else-if="row.type == 'md'" src="../assets/images/file/file_markdown.png" :width="imgWid" />
                <!-- 目录 -->
                <img v-else-if="row.type == 'dir'" src="../assets/images/file/dir.png" :width="imgWid" />
                <!-- 未知文件 -->
                <img v-else src="../assets/images/file/file_unknown.png" :width="imgWid" />
            </div>
            <!-- 文件名 -->
            <span class="file_name">{{ row.fileName }}</span>
            <ul class="file_info">
                <li>创建时间：{{ row.createTime | formatTime }}</li>
                <li>最后修改时间：{{ row.updateTime | formatTime }}</li>
                <li>文件类型：{{ row.type }}</li>
                <li>文件大小：{{ row.size | bytesToSize }}</li>
                <li>所在目录：{{ row.parentDir }}</li>
            </ul>
        </div>
    </div>
    <!-- <div style="width: 100%;height: 86%;display: flex;flex-direction: row;">
        <div style="width: 75%;height: 100%;border-right: 1px solid #f0f0f0">内容列表</div>
        <div style="width: 25%;height: 100%; padding: 10px;">
            搜索详情在此展示
        </div>
    </div> -->
</template>

<script>
export default {
    name: 'searchinfo',
    props: ['row'],
    data() {
        return {
            imgWid: '40%',
        }
    },
    methods: {
        showRow() {
            console.log(this.row.id);
        }
    },
    mounted() {
    },
}
</script>

<style scoped>
.show_img {
    width: 95%;
    height: 128px;
    text-align: center;
    padding-top: 10px;
    margin: 25px 0px 13px 7px;
    background-color: #F9FAFA;
    border-radius: 10%;
}

.show_img img {
    margin-top: 16px;
}

.file_name {
    color: #454D5A;
    font-weight: bold;
    font-size: 15px;
    /* 自动换行 */
    word-break: break-all;
    width: auto;
    display: block;
    white-space: pre-wrap;
}

.file_info {
    list-style: none;
    font-size: 10px;
    color: #9296A5;
    margin-top: 15px;
    padding-left: 0px;
}

.file_info li {
    margin-bottom: 10px;
}
</style>