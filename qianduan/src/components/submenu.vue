<template>
    <div>
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
    name: 'submenu',
    data() {
        return {
            //右键菜单栏
            menus: [
                { name: "菜单一", operType: 1, icon: "el-icon-upload2" },
                { name: "菜单二", operType: 2, icon: "el-icon-folder-add" },
                { name: "菜单三", operType: 3, icon: "el-icon-edit-outline" },
                { name: "菜单四", operType: 4, icon: "el-icon-folder-remove" },
                { name: "菜单五", operType: 5, icon: "el-icon-download" },
            ],
        }
    },
    methods: {
        // 表格右击的功能
        rightClick(row, column, event) {
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
            console.log(12121);
            let menu = document.querySelector("#menu");
            menu.style.display = "none";
        },
        // 右击自定义菜单的点击事件
        infoClick(index) {
            console.log("要做的事情:", index)
            if (index === 0) {
                // 要做的事情
            }
            let menu = document.querySelector("#menu");
            menu.style.display = "none";
        },

    },
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