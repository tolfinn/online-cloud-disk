<template>
  <div>
    <Fileinfo :title="title" :list="list"></Fileinfo>
  </div>
</template>

<script>
import Fileinfo from '../../components/fileinfo.vue';

export default {
  name: "documents",
  props: ["title"],
  components: { Fileinfo },
  data() {
    return {
      list: [],
    };
  },
  mounted() {
    let params = new URLSearchParams();
    params.append("attribute", 2);
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
};
</script>

<style scoped>
</style>
