<script setup></script>

<template>
  <div class="wrapper">
    <div class="header">
      <div class="jmp-btn">
        <router-link to="/home">Home</router-link>
      </div>
      <div class="jmp-btn">
        <router-link to="/mod">Mod</router-link>
      </div>
      <div class="jmp-btn">
        <router-link to="/about">About</router-link>
      </div>
    </div>
    <div class="main">
      <router-view></router-view>
    </div>
  </div>
</template>

<style lang="less" scoped>
.wrapper {
  margin: 0px auto;
  min-height: 1080px;
  background: url('@/assets/bg.jpg') no-repeat;
  background-size: cover;

  .header {
    display: flex;
    margin: 0px 40px;
    height: 100px;
    line-height: 100px;
    .jmp-btn {
      padding: 10px;
      width: 100px;
      font-weight: 700;

      :hover {
        color: #2c3e50;
      }
    }

    .router-link-active {
      color: #2c3e50;
    }
  }

  .main {
    margin: 0px 50px;
  }
}
</style>
