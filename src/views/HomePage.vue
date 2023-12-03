<script setup>
import { ref } from 'vue'

const searchContent = ref('')
const centerDialogVisible = ref(false)
const activeIndex = ref(0)
const sortWay = ref('⬆️')

// const objArr = ref('')
const sortOptions = ref([
  {
    id: 0,
    label: 'Deafult'
  },
  {
    id: 1,
    label: 'Amount'
  },
  {
    id: 2,
    label: 'Category'
  },
  {
    id: 3,
    label: 'Integrity'
  }
])

const onEdit = () => {
  console.log('edit')
}

const onDelete = () => {
  console.log('delete')
}

const onCard = () => {
  centerDialogVisible.value = true
  // 申请描述内容
}

const onSort = (item) => {
  if (sortWay.value === '⬆️' && activeIndex.value === item.id)
    sortWay.value = '⬇️'
  else sortWay.value = '⬆️'
  activeIndex.value = item.id
  // isUp.value = !isUp.value
  // console.log(activeIndex.value)
}
</script>

<template>
  <div class="equipment">
    <el-container>
      <el-aside>
        <el-input v-model="searchContent"></el-input>
        <el-button class="search">Search</el-button>
        <div class="sort-list">
          <div class="title">Sort according to</div>
          <ul>
            <!-- <li>
              <el-button type="text">Default</el-button>
            </li>
            <li>
              <el-button type="text">
                Amount
                <div class="up" v-if="isUp">⬆️</div>
                <div class="down" v-else>⬇️</div>
              </el-button>
            </li>
            <li>
              <el-button type="text">
                Category
                <div class="up" v-if="isUp">⬆️</div>
                <div class="down" v-else>⬇️</div>
              </el-button>
            </li>
            <li>
              <el-button type="text">
                Integrity
                <div class="up" v-if="isUp">⬆️</div>
                <div class="down" v-else>⬇️</div>
              </el-button>
            </li> -->
            <li
              @click="onSort(item)"
              :class="{ 'is-active': activeIndex === index }"
              v-for="(item, index) in sortOptions"
              :key="index"
            >
              <el-button type="text">
                {{ item.label }}
                <!-- <div class="up" v-if="isUp">⬆️</div>
                <div class="down" v-else>⬇️</div> -->
                <div
                  v-if="activeIndex === index && activeIndex !== 0"
                  v-html="sortWay"
                ></div>
              </el-button>
            </li>
          </ul>
        </div>
      </el-aside>
      <el-main>
        <!-- 限制一个页面最多十个 不然页面会发生拉伸现象 -->
        <el-card v-for="item in 10" :key="item" @click="onCard">
          <div class="name">嘴部固定器</div>
          <div class="image">
            <img @dragstart.prevent src="@/assets/equipment1.png" alt="" />
          </div>
          <div class="id">ID : 1</div>
          <div class="count">Amount : 1</div>
          <div class="department">Category : Jigsaw</div>
          <div class="damaged">Status : Avaliable</div>
          <div class="buttons">
            <el-button type="text" @click.stop="onEdit">edit</el-button>
            <el-button type="text" @click.stop="onDelete">delete</el-button>
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

    <!-- describe of the equipment -->
    <el-dialog
      v-model="centerDialogVisible"
      title="Description"
      width="30%"
      center
    >
      <span>
        It should be noted that the content will not be aligned in center by
        default
      </span>
      <template #footer>
        <span class="dialog-footer"> </span>
      </template>
    </el-dialog>
  </div>
</template>

<style lang="less" scoped>
.is-active {
  background-color: #253444;
}
.equipment {
  .el-aside {
    .el-input {
      width: 170px;
      :deep(.el-input__wrapper) {
        background-color: transparent;
      }
      :deep(.el-inpu t__inner) {
        color: #2980b9;
      }
    }
    .search {
      width: 60px;
      background-color: transparent;
      color: #2980b9;
    }

    .sort-list {
      margin-top: 30px;
      width: 215px;
      background-color: #2c3e50;
      border-radius: 5px;
      padding: 10px;
      .title {
        color: #2980b9;
        font-weight: 700;
      }
      ul {
        text-align: center;
        li {
          display: flex;
          margin-top: 15px;
          border-radius: 5px;
          .el-button {
            width: 100%;
          }
          :hover {
            background-color: #253444;
          }
        }
      }
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

      cursor: pointer;
      :hover {
        background-color: #253444;
      }
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

  :deep(.el-dialog) {
    .el-dialog__title {
      font-weight: 700;
      color: #2980b9;
    }
    .el-dialog__body {
      color: #2980b9;
    }
    background-color: #2c3e50;
  }
}
</style>
