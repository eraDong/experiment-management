import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAdminStore = defineStore(
  'admin',
  () => {
    const isMod = ref(false)
    const setMod = (status) => {
      isMod.value = status
    }
    const removeMod = () => {
      isMod.value = false
    }
    return {
      isMod,
      setMod,
      removeMod
    }
  },
  {
    persist: true
  }
)
