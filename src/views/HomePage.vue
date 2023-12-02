<script setup>
import { ref } from 'vue'

const searchContent = ref('')
// const objArr = ref('')
</script>

<template>
  <div class="equipment">
    <el-container>
      <el-aside>
        <el-input v-model="searchContent"></el-input>
        <el-button class="search">Search</el-button>
      </el-aside>
      <el-main>
        <!-- 限制一个页面最多十个 不然页面会发生拉伸现象 -->
        <el-card v-for="item in 10" :key="item">
          <div class="name">嘴部固定器</div>
          <div class="image"><img src="@/assets/equipment1.png" alt="" /></div>
          <div class="id">ID : 1</div>
          <div class="count">Amount : 1</div>
          <div class="department">Department : Jigsaw</div>
          <div class="damaged">Integrity : 100%</div>
          <div class="buttons">
            <el-button type="text">edit</el-button>
            <el-button type="text">delete</el-button>
          </div>
        </el-card>
        <el-pagination
          :hide-on-single-page="true"
          :page-size="6"
          :pager-count="6"
          layout="prev, pager, next"
          :total="12"
          background="true"
        />
      </el-main>
    </el-container>
  </div>
</template>

<style lang="less" scoped>
.equipment {
  .el-aside {
    .el-input {
      width: 170px;
      :deep(.el-input__wrapper) {
        background-color: transparent;
      }
      :deep(.el-input__inner) {
        color: #2980b9;
      }
    }
    .search {
      width: 60px;
      background-color: transparent;
      color: #2980b9;
    }
  }
  .el-main {
    padding: 0;
    display: flex;
    flex-wrap: wrap;

    .el-card {
      margin: 30px;
      margin-left: 0px;
      margin-top: 0px;

      width: 250px;

      height: 350px;
      background-color: #2c3e50;
      border: none;
      color: #2980b9;

      cursor: default;
      .name {
        margin: 5px;
        font-weight: 700;
        text-align: center;
      }
      .image {
        img {
          width: 200px;
          height: 150px;
          border-radius: 15px;
        }
      }

      .buttons {
        margin-top: 30px;
        .el-button {
          background-color: transparent;
          border: none;
        }
      }
    }
  }
}
</style>
