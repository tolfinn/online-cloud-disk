<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style>
html {
  overflow-y: overlay;
}

html, body {
  width: 100%;
  height: 100%;
  margin: 0;
}

#app {
  width: 100%;
  height: 100%;
  margin: 0;
}
/* 不显示上传的文件列表  */
.el-upload-list {
display:none;
}


</style>
