<template>
  <div>
    <el-container style="height:100%">
      <el-aside width="auto">
        <CommonAside />
      </el-aside>
      <el-container>
        <el-header>
          <CommonHeader />
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CommonAside from "../components/CommonAside";
import CommonHeader from "../components/CommonHeader";
export default {
  name: "Main",
  components: { CommonAside, CommonHeader }
};
</script>
<style  scoped>
.el-header {
  background-color: #333;
}

.el-aside {
  background-color: #545c64;
}

.el-main {
  padding-top: 0;
}

div {
  height: 100%;
}
</style>