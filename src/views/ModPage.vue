<script setup>
import { ref } from 'vue'

const isMod = ref(false)
</script>

<template>
  <el-container>
    <el-aside>
      <div class="mod-input">
        <div class="title">Please input your mod key</div>
        <div class="input"><el-input></el-input></div>
      </div>
    </el-aside>
    <el-main>
      <div class="title">ADD your equipment</div>
      <div class="basic">
        <div class="amount">Amount : <el-input></el-input></div>
        <div class="category">Category : <el-input></el-input></div>
        <div class="status">Status : <el-input></el-input></div>
      </div>
      <div class="descrip">
        <div class="description">
          Description :
          <el-input type="textarea" :rows="4" resize="none"></el-input>
        </div>
      </div>
      <div class="btn">
        <el-button type="primary" round>Submit</el-button>
        <div class="warn" v-if="isMod === false">
          🔒 GET THE PERMISSION FIRST
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<style lang="less" scoped>
.el-container {
  .el-aside {
    .mod-input {
      width: 230px;
      background-color: #2c3e50;
      padding: 20px;
      border-radius: 5px;
      .title {
        color: #2980b9;
        font-weight: 700;
        margin-bottom: 10px;
      }
      .input {
        .el-input {
          width: 215px;
          :deep(.el-input__wrapper) {
            background-color: transparent;
          }
          :deep(.el-input__inner) {
            color: #2980b9;
          }
        }
      }
    }
  }

  .el-main {
    margin-right: 130px;
    width: 100%;
    background-color: #2c3e50;

    color: #2980b9;
    border-radius: 5px;
    .title {
      font-weight: 700;
      margin-bottom: 30px;
    }
    .basic {
      display: flex;
      justify-content: space-between;
      .amount {
        .el-input {
          width: 215px;
          :deep(.el-input__wrapper) {
            background-color: transparent;
          }
          :deep(.el-input__inner) {
            color: #2980b9;
          }
        }
      }
      .category {
        .el-input {
          width: 215px;
          :deep(.el-input__wrapper) {
            background-color: transparent;
          }
          :deep(.el-input__inner) {
            color: #2980b9;
          }
        }
      }

      .status {
        .el-input {
          width: 215px;
          :deep(.el-input__wrapper) {
            background-color: transparent;
          }
          :deep(.el-input__inner) {
            color: #2980b9;
          }
        }
      }

      margin-bottom: 30px;
    }

    .descrip {
      .description {
        // .el-textarea {
        //   width: 215px;
        //   :deep(.el-textarea__wrapper) {
        //     background-color: transparent;
        //   }
        //   :deep(.el-textarea__inner) {
        //     color: #2980b9;
        //   }
        // }
        :deep(.el-textarea__inner) {
          background-color: transparent;
          color: #2980b9;
        }
      }
    }

    .btn {
      margin-top: 30px;
      display: flex;
      justify-content: space-between;
      .el-button {
        background-color: #2980b9;
        color: #2c3e50;
      }
      .warn {
        font-weight: 700;
        color: #b92929;
      }
    }
  }
}
</style>
