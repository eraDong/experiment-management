import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout',
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('../views/HomePage.vue')
        },
        {
          path: '/about',
          name: 'about',
          component: () => import('../views/AboutPage.vue')
        },
        {
          path: '/mod',
          name: 'mode',
          component: () => import('../views/ModPage.vue')
        }
      ],
      component: () => import('../views/LayoutContainer.vue')
    }
  ]
})

export default router
