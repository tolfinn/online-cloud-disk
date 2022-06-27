<template>
  <div>
    <Fileinfo :list="list" :title="title"></Fileinfo>
  </div>
</template>


<script>
import Fileinfo from '../../components/fileinfo.vue';
export default {
  name: "vedios",
  components: { Fileinfo },
  data() {
    return {
      list: [],
      title: '我的视频',
    }
  },
  mounted() {
    let params = new URLSearchParams();
    params.append("attribute", 1);
    this.$axios.post(
      "/file/module", params
    ).then(res => {
      console.log(res)
      if (res.code == 6666) {
        this.list = res.data;
      }
    }).catch(err => {
      console.log(err)
    });
  },
}
</script>

<style scoped>
.top {
  display: flex;
}

.el-pagination {
  display: flex;
  justify-content: center;
  padding-top: 20px;
}
</style>
