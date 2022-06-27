<template>
  <div>
    <Fileinfo :list="list" :title="title"></Fileinfo>
  </div>
</template>

<script>
import Fileinfo from '../../components/fileinfo.vue'
export default {
  name: "audios",
  components: { Fileinfo },
  data() {
    return {
      list: [],
      title: '我的音频',
    }
  },
  mounted() {
    let params = new URLSearchParams();
    params.append("attribute", 3);
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
</style>