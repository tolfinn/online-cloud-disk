<template>
    <!-- 预览组件 -->
    <div class="FilePreview">
        <div class="container">
            <!-- 渲染层 -->
            <div class="object"
                v-if="TragetObj.format == 'jpg' || TragetObj.format == 'png' || TragetObj.format == 'JPG' || TragetObj.format == 'PNG' || TragetObj.format == 'jpeg' || TragetObj.format == 'tiff' || TragetObj.format == 'swf' || TragetObj.format == 'gif' || TragetObj.format == 'JPEG' || TragetObj.format == 'GIF'"
                ref="imgWrap" @mousewheel.prevent="rollImg">
                <img class="Target modal-img" ref="img" @mousedown="moveImg" :src="TragetObj.url" alt="">
            </div>
            <!-- 视频 -->
            <div class="object"
                v-else-if="TragetObj.format == 'webm' || TragetObj.format == 'mov' || TragetObj.format == 'mp4'">
                <video class="Target" controls="controls" width="1200" height="700" :src="TragetObj.url" autoplay
                    ref="videoref">
                    您的浏览器不支持。
                </video>
            </div>
            <!-- 附件 -->
            <div class="object" v-else>
                <iframe class="Target" frameborder="1" index-render="1" scrolling="yes" height="890.15" width="1220.4"
                    :src="TragetObj.url"></iframe>
            </div>
            <!-- 操作按钮 -->
            <div class="viewer_btn">
                <ul>
                    <li @click="onClickEnlarge" class="el-icon-zoom-in"></li>
                    <li @click="onClickNarrow" class="el-icon-zoom-out"></li>
                    <li @click="onClickReturnOriginal" class="el-icon-c-scale-to-original"></li>
                    <!-- 下载 -->
                    <li v-if="TragetObj.downUrl" class="el-icon-download">
                        <a :href="TragetObj.downUrl">

                        </a>
                    </li>
                </ul>
            </div>
            <!--下一个 -->
            <div class="next" v-if="NumIndex < this.FilePresAll.length - 1" @click="onClickNext">
                <i class="el-icon-arrow-right"></i>
            </div>
            <!-- 上一个 -->
            <div class="previous" v-if="NumIndex != 0" @click="onCLickPrevious">
                <i class="el-icon-arrow-left"></i>
            </div>
            <!-- 关闭按钮 -->
            <div class="close" @click="onClickClose">
                <i class="el-icon-close"></i>
            </div>
        </div>
        <div class="viewer__mask" @click.stop="onClickClose">
            <div v-loading="loadingFlag"></div>
        </div>
    </div>
</template>

<script>
export default {
    props: ['TragetPic', 'FilePreAll'],
    data() {
        return {
            EleWidth: "",
            EleHeight: "",
            TragetObj: {}, // 目标对象
            FilePresAll: [], // 渲染数组
            NumIndex: 0, // 当前渲染目标的下标
            loadingFlag: false, // 加载
        }
    },
    created() {
        this.TragetObj = this.TragetPic // 目标对象
        this.FilePresAll = this.FilePreAll // 渲染数组
    },
    mounted() {
        if (this.EleWidth == "") {
            if (this.TragetObj.format != 'jpg' && this.TragetObj.format != 'png' && this.TragetObj.format != 'JPG' && this.TragetObj.format != 'PNG' && this.TragetObj.format != 'jpeg' && this.TragetObj.format != 'tiff' && this.TragetObj.format != 'swf' && this.TragetObj.format != 'gif' && this.TragetObj.format != 'JPEG' && this.TragetObj.format != 'GIF') {
                this.loadingFlag = true
            } else {
                this.loadingFlag = false
            }
        }
        // 获取目标元素值
        let Target = document.getElementsByClassName("Target")[0];
        this.EleWidth = Target.offsetWidth
        this.EleHeight = Target.offsetHeight
        if (this.EleWidth != "") {
            this.loadingFlag = false
        }
        // console.log(this.EleWidth)

        // 获取当前目标的下标值
        this.FilePresAll.forEach((item, index) => {
            if (item.FileName == this.TragetObj.FileName) {
                this.NumIndex = index
            }
        })
    },

    beforeUpdate() {
        if (this.TragetObj.format != 'jpg' && this.TragetObj.format != 'png' && this.TragetObj.format != 'JPG' && this.TragetObj.format != 'PNG' && this.TragetObj.format != 'jpeg' && this.TragetObj.format != 'tiff' && this.TragetObj.format != 'swf' && this.TragetObj.format != 'gif' && this.TragetObj.format != 'JPEG' && this.TragetObj.format != 'GIF') {
            this.loadingFlag = true
        } else {
            this.loadingFlag = false
        }
        this.$nextTick(() => {
            let eleParent = document.querySelector('.object')
            // 获取目标元素值
            let Target = eleParent.children[0]
            this.EleWidth = Target.offsetWidth
            this.EleHeight = Target.offsetHeight
            // console.log(Target)

        })
    },
    methods: {
        // 放大功能
        onClickEnlarge() {
            let Target = document.getElementsByClassName("Target")[0];
            Target.style.height = Target.height * 1.1 + 'px';
            Target.style.width = Target.width * 1.1 + 'px';
        },

        // 缩小功能
        onClickNarrow() {
            let Target = document.getElementsByClassName("Target")[0];
            Target.style.height = Target.height / 1.1 + 'px';
            Target.style.width = Target.width / 1.1 + 'px';
        },

        // 返回原图大小
        onClickReturnOriginal() {
            let Target = document.getElementsByClassName("Target")[0];
            Target.style.height = this.EleHeight + 'px';
            Target.style.width = this.EleWidth + 'px';
        },

        // 点击下一个事件
        onClickNext() {
            if (this.NumIndex < this.FilePresAll.length - 1) {
                this.NumIndex++
                this.TragetObj = this.FilePresAll[this.NumIndex]
            }
        },

        // 点击上一个事件
        onCLickPrevious() {
            if (this.NumIndex != 0) {
                this.NumIndex--
                this.TragetObj = this.FilePresAll[this.NumIndex]
            }
        },

        // 关闭按钮
        onClickClose() {
            // 关闭视频声音
            if (this.$refs.videoref != undefined) {
                this.$refs.videoref.pause()
            }
            this.$emit('Close', false)
        },

        // 拖拽图片事件
        moveImg(e) {
            e.preventDefault()
            // 获取元素
            let imgWrap = this.$refs.imgWrap
            let img = this.$refs.img
            let x = e.pageX - img.offsetLeft
            let y = e.pageY - img.offsetTop
            // 添加鼠标移动事件
            imgWrap.addEventListener('mousemove', move)
            function move(e) {
                img.style.left = e.pageX - x + 'px'
                img.style.top = e.pageY - y + 'px'
            }
            // 添加鼠标抬起事件，鼠标抬起，将事件移除
            img.addEventListener('mouseup', () => {
                imgWrap.removeEventListener('mousemove', move)
            })
            // 鼠标离开父级元素，把事件移除
            imgWrap.addEventListener('mouseout', () => {
                imgWrap.removeEventListener('mousemove', move)
            })
        },

        // 鼠标滚动缩放图片事件
        rollImg(event) {
            /* 获取当前页面的缩放比 若未设置zoom缩放比，则为默认100%，即1，原图大小 */
            let zoom = parseInt(this.$refs.img.style.zoom) || 100
            /* event.wheelDelta 获取滚轮滚动值并将滚动值叠加给缩放比zoom wheelDelta统一为±120，其中正数表示为向上滚动，负数表示向下滚动 */
            zoom += event.wheelDelta / 12
            /* 最小范围 和 最大范围 的图片缩放尺度 */
            if (zoom >= 40 && zoom < 500) {
                this.$refs.img.style.zoom = zoom + '%'
            }
            return false
        },
    }
}
</script>

<style lang="scss" scoped>
.FilePreview {
    position: fixed;
    left: 0;
    top: 0;
    z-index: 8888;
    height: 100%;
    width: 100%;

    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        /*由于flex-direction: column，因此align-items代表的是水平方向*/
        justify-content: center;
        /*由于flex-direction: column，因此justify-content代表的是垂直方向*/
        position: relative;
        left: 0;
        top: 0;
        height: 100%;
        width: 100%;

        .object {
            z-index: 10001;
            display: flex;
            flex-direction: column;
            align-items: center;
            /*由于flex-direction: column，因此align-items代表的是水平方向*/
            justify-content: center;
            /*由于flex-direction: column，因此justify-content代表的是垂直方向*/
            margin: 25px;

            .modal-img {
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%);
                max-width: 100%;
                cursor: move;

                &-wrap {
                    position: relative;
                    width: 960px;
                    height: 560px;
                    overflow: hidden;
                }
            }
        }

        .viewer_btn {
            position: absolute;
            left: 50%;
            bottom: 20px;
            z-index: 10020;
            -webkit-transform: translateX(-50%);
            transform: translateX(-50%);
            // width: 250px;
            height: 44px;
            padding: 0 23px;
            background-color: #606266;
            border-color: #fff;
            border-radius: 22px;

            ul {
                padding: 0;
                margin: 0;
                display: flex;
                justify-content: space-between;
                align-items: center;

                li {
                    font-size: 30px;
                    color: #fff;
                    padding: 6px 10px;
                    cursor: pointer;
                }
            }
        }

        .next {
            position: absolute;
            top: 50%;
            right: 15px;
            z-index: 10020;
            font-size: 30px;
            height: 44px;
            width: 44px;
            line-height: 44px;
            text-align: center;
            background: #606266;
            color: #fff;
            border-radius: 50%;
            cursor: pointer;
        }

        .previous {
            position: absolute;
            top: 50%;
            left: 15px;
            z-index: 10020;
            font-size: 30px;
            height: 44px;
            width: 44px;
            line-height: 44px;
            text-align: center;
            background: #606266;
            color: #fff;
            border-radius: 50%;
            cursor: pointer;
        }

        .close {
            position: absolute;
            top: 15px;
            right: 15px;
            z-index: 10020;
            font-size: 30px;
            cursor: pointer;
            background: #606266;
            border-radius: 50%;
            width: 44px;
            height: 44px;
            line-height: 44px;
            text-align: center;
        }
    }

    .viewer__mask {
        position: absolute;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.9);
        top: 0;
        left: 0;
        z-index: 8888;
        display: flex;
        flex-direction: column;
        align-items: center;
        /*由于flex-direction: column，因此align-items代表的是水平方向*/
        justify-content: center;
        /*由于flex-direction: column，因此justify-content代表的是垂直方向*/
    }
}
</style>
<style>
/* 关闭图标 */
.FilePreview .el-icon-close {
    color: #fff;
}
</style>
