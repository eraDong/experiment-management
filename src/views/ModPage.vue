<script setup>
import { ref } from 'vue'
import { AdminLoginService } from '@/api/Admin.js'
import { ElMessage } from 'element-plus'
import { equipmentAddService, equipmentRenderService } from '@/api/Equipment.js'
import { useAdminStore } from '@/stores'

const useAdmin = useAdminStore()
const upload = ref('')
const AdminPassword = ref('')
const formName = ref('')
const formAmount = ref('')
const formCategory = ref('')
const formStatus = ref('')
const formDescription = ref('')

const enterAdmin = async () => {
  const res = await AdminLoginService(AdminPassword.value)

  if (res.data.status === 'success') {
    useAdmin.setMod(true)
  }
  AdminPassword.value = ''
}

let imageUrl = ref('')
const uploadImage = (file, fileList) => {
  // 获取上传的文件对象（File 对象）
  const uploadedFile = fileList[0].raw

  // console.log(uploadedFile)
  // 在这里可以进行你的操作，比如显示图片预览
  if (uploadedFile instanceof File) {
    const reader = new FileReader()
    reader.onload = (e) => {
      imageUrl.value = e.target.result
      // console.log(imageUrl.value)
    }
    reader.readAsDataURL(uploadedFile)
  }

  upload.value.clearFiles()
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('Avatar picture must be JPG/PNG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}

const base64ToFile = (urlData, fileIndex = 1) => {
  let arr = urlData.split(',')
  let mime = arr[0].match(/:(.*?);/)[1]
  let bytes = atob(arr[1]) // 解码base64
  let n = bytes.length
  let ia = new Uint8Array(n)
  while (n--) {
    ia[n] = bytes.charCodeAt(n)
  }

  let fileName = `file_${fileIndex}.png`

  return new File([ia], fileName, { type: mime })
}

const onSubmit = async () => {
  const res = await equipmentRenderService()
  const file =
    imageUrl.value === ''
      ? ''
      : base64ToFile(imageUrl.value, res.data.content.length + 1)

  for (let i = 1; i <= formAmount.value; i++) {
    await equipmentAddService({
      name: formName.value,
      category: formCategory.value,
      status: formStatus.value,
      image: file,
      description: formDescription.value
    })
  }
  formAmount.value = ''
  formDescription.value = ''
  formName.value = ''
  formCategory.value = ''
  formStatus.value = ''
  imageUrl.value = ''
}

const logout = () => {
  useAdmin.removeMod()
}
</script>

<template>
  <el-container>
    <el-aside>
      <div class="mod-input" v-if="useAdmin.isMod === false">
        <div class="title">Please input your mod key</div>
        <div class="input">
          <el-input
            v-model="AdminPassword"
            @keyup.enter="enterAdmin"
          ></el-input>
        </div>
      </div>
      <div class="log-out" v-else @click="logout">Log out</div>
    </el-aside>
    <el-main>
      <div class="title">ADD your equipment</div>
      <div class="basic">
        <div class="name">Name : <el-input v-model="formName"></el-input></div>
        <div class="amount">
          Amount : <el-input v-model="formAmount"></el-input>
        </div>
        <div class="category">
          Category : <el-input v-model="formCategory"></el-input>
        </div>
        <div class="status">
          Status : <el-input v-model="formStatus"></el-input>
        </div>
      </div>
      <div class="upload-image">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          @change="uploadImage"
          :auto-upload="false"
          :before-upload="beforeAvatarUpload"
          ref="upload"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">+</el-icon>
        </el-upload>
      </div>
      <div class="descrip">
        <div class="description">
          Description :
          <el-input
            type="textarea"
            :rows="4"
            resize="none"
            v-model="formDescription"
          ></el-input>
        </div>
      </div>
      <div class="btn">
        <el-button
          type="primary"
          round
          v-if="useAdmin.isMod === true"
          @click.prevent="onSubmit"
          >Submit</el-button
        >
        <div class="warn" v-if="useAdmin.isMod === false">
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

    .log-out {
      width: 200px;
      border-radius: 5px;
      height: 50px;
      line-height: 50px;
      text-align: center;
      background-color: #2c3e50;
      color: #2980b9;
      font-weight: 700;
    }
    .log-out:hover {
      background-color: #253444 !important;
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
      .name {
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
        color: #fff;
      }
      .warn {
        font-weight: 700;
        color: #b92929;
      }
    }

    .upload-image {
      width: 178px;
      cursor: pointer;
      border: 1px dashed rgb(104, 100, 100);
      .avatar-uploader .el-upload {
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        transition: var(--el-transition-duration-fast);
      }

      .avatar-uploader .el-upload:hover {
        border-color: var(--el-color-primary);
      }

      .el-icon.avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        text-align: center;
      }
    }
  }
}
</style>
