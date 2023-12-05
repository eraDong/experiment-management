<script setup>
import { ref } from 'vue'
import {
  equipmentRenderService,
  equipmentDeleteService,
  equipmentSearchService
} from '@/api/Equipment'
import { ElMessage, ElMessageBox } from 'element-plus'

const searchContent = ref('')
const centerDialogVisible = ref(false)
const activeIndex = ref(0)
const cardArr = ref([])
const sortWay = ref('⬆️')
const pagenum = ref(0)
const pagesize = ref(0)
const totalElements = ref(0)
const descriptionContent = ref('')
const equipCreateTime = ref('')
const selectedSearch = ref('id')

function sortBy(property) {
  return function (a, b) {
    // Use the property to compare
    const valueA = a[property]
    const valueB = b[property]

    if (valueA < valueB) {
      return -1
    } else if (valueA > valueB) {
      return 1
    } else {
      return 0
    }
  }
}

function sortByDescending(property) {
  return function (a, b) {
    // Use the property to compare
    const valueA = a[property]
    const valueB = b[property]

    if (valueA > valueB) {
      return -1
    } else if (valueA < valueB) {
      return 1
    } else {
      return 0
    }
  }
}

// const objArr = ref('')
const sortOptions = ref([
  {
    id: 0,
    label: 'Deafult'
  },
  {
    id: 1,
    label: 'Name'
  },
  {
    id: 2,
    label: 'Category'
  },
  {
    id: 3,
    label: 'Status'
  }
])

const onEdit = () => {
  console.log('edit')
}

const onDelete = async (id) => {
  // console.log('delete')
  // await equipmentDeleteService(id)
  // console.log(id)
  ElMessageBox.confirm('Continue to delete?', 'Warning', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(async () => {
    await equipmentDeleteService(id)
    ElMessage({
      type: 'success',
      message: 'Delete completed'
    })
    equipmentRender()
  })
  // .catch(() => {
  //   ElMessage({
  //     type: 'info',
  //     message: 'Delete canceled'
  //   })
  // })
}

const onCard = (id) => {
  centerDialogVisible.value = true
  // 申请描述内容
  // console.log(cardArr)

  for (let i = 0; i < cardArr.value.length; i++) {
    if (cardArr.value[i].id === id) {
      descriptionContent.value = cardArr.value[i].description
      equipCreateTime.value = cardArr.value[i].createTime
      break
    }
  }
  // console.log(descriptionContent.value)
}

const onSort = (item) => {
  if (sortWay.value === '⬆️' && activeIndex.value === item.id)
    sortWay.value = '⬇️'
  else sortWay.value = '⬆️'
  activeIndex.value = item.id
  // console.log(item.id)

  //排序逻辑
  const sortedArr = cardArr.value.slice()
  if (activeIndex.value === 0) {
    equipmentRender()
    sortedArr.sort(sortBy('id'))
  } else {
    if (sortWay.value === '⬆️') {
      if (activeIndex.value === 1) sortedArr.sort(sortBy('name'))
      else if (activeIndex.value === 2) sortedArr.sort(sortBy('category'))
      else if (activeIndex.value === 3) sortedArr.sort(sortBy('status'))
    } else if (sortWay.value === '⬇️') {
      if (activeIndex.value === 1) sortedArr.sort(sortByDescending('name'))
      else if (activeIndex.value === 2)
        sortedArr.sort(sortByDescending('category'))
      else if (activeIndex.value === 3)
        sortedArr.sort(sortByDescending('status'))
    }
  }
  cardArr.value = sortedArr
}

const equipmentRender = async () => {
  cardArr.value = await equipmentRenderService()
  totalElements.value = cardArr.value.data.totalElements
  pagenum.value = cardArr.value.data.pageable.pageNumber
  pagesize.value = cardArr.value.data.pageable.pageSize
  cardArr.value = cardArr.value.data.content
  // console.log(cardArr.value)
}
equipmentRender()

const pageChange = async (e) => {
  // console.log(e)
  pagenum.value = e - 1
  cardArr.value = await equipmentRenderService(pagenum.value)
  cardArr.value = cardArr.value.data.content
}

const onSearch = async () => {
  // console.log(searchContent.value)

  if (searchContent.value !== '') {
    // 构建参数对象
    const searchParams = {
      [selectedSearch.value]: searchContent.value
    }

    const searchResult = await equipmentSearchService(searchParams)
    totalElements.value = searchResult.data.totalElements
    pagenum.value = searchResult.data.pageable.pageNumber
    pagesize.value = searchResult.data.pageable.pageSize
    cardArr.value = searchResult.data.content
  }

  // 重置搜索内容
  searchContent.value = ''
}

const handleCommand = (command) => {
  // console.log(`click on item ${command}`)
  // // 或者使用其他消息通知库，例如：
  // // alert(`click on item ${command}`);
  selectedSearch.value = command
}
</script>

<template>
  <div class="equipment">
    <el-container>
      <el-aside>
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            {{ selectedSearch
            }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="id">id</el-dropdown-item>
              <el-dropdown-item command="name">name</el-dropdown-item>
              <el-dropdown-item command="category">category</el-dropdown-item>
              <el-dropdown-item command="status">status</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <el-input v-model="searchContent" @keyup.enter="onSearch()"></el-input>

        <el-button class="search" @click="onSearch()">Search</el-button>

        <div class="sort-list">
          <div class="title">Sort according to</div>
          <ul>
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

      <el-main v-if="cardArr.length !== 0">
        <!-- 限制一个页面最多十个 不然页面会发生拉伸现象 -->

        <el-card v-for="item in cardArr" :key="item" @click="onCard(item.id)">
          <div class="name">{{ item.name }}</div>
          <div class="image">
            <img
              class="default-img"
              @dragstart.prevent
              src="@/assets/default-equipment.png"
              alt=""
              v-if="!item.image"
            />
            <img
              @dragstart.prevent
              :src="`/experiment-management-server/uploads/${item.image}`"
              alt=""
              v-else
            />
          </div>

          <div class="id">ID : {{ item.id }}</div>
          <!-- <div class="count">Amount : {{ item.amount }}</div> -->
          <div class="department">Category : {{ item.category }}</div>
          <div class="damaged">Status : {{ item.status }}</div>
          <div class="buttons">
            <el-button type="text" @click.stop="onEdit(item.id)"
              >edit</el-button
            >
            <el-button type="text" @click.stop="onDelete(item.id)"
              >delete</el-button
            >
          </div>
        </el-card>

        <el-pagination
          :hide-on-single-page="true"
          layout="prev, pager, next"
          :total="totalElements"
          background="true"
          @next-click="pageNext"
          @prev-click="pagePrev"
          @current-change="pageChange"
        />
      </el-main>
      <el-empty description="No Result" v-else />
    </el-container>

    <!-- describe of the equipment -->
    <el-dialog
      v-model="centerDialogVisible"
      title="Description"
      width="30%"
      center
    >
      <span>
        {{ descriptionContent }}
      </span>
      <div>CreateTime : {{ equipCreateTime }}</div>
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
    margin-right: 50px;
    .el-dropdown {
      text-align: center;
      width: 50px;
      height: 30px;
      line-height: 30px;
      color: #2980b9;
      font-weight: 700;
      .example-showcase .el-dropdown-link {
        cursor: pointer;
        color: var(--el-color-primary);
        display: flex;
        align-items: center;
      }
    }
    .el-input {
      width: 130px;
      margin-left: 15px;
      color: #2980b9;
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

      height: 345px;
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
        .default-img {
          opacity: 0.5;
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

  .el-pagination {
    width: 100%;
  }

  .el-empty {
    width: 100%;
    height: 550px;
    --el-empty-fill-color-1: #5e5e5e;
    --el-empty-fill-color-2: #757575;
    --el-empty-fill-color-3: #807d7d;
    --el-empty-fill-color-4: #838282;
    --el-empty-fill-color-5: #9e9d9d;
    --el-empty-fill-color-6: #888787;
    --el-empty-fill-color-7: rgb(129, 127, 127);
    --el-empty-fill-color-8: #5e5e5e;
    --el-empty-fill-color-9: #9b9999;
  }
}
</style>
