import { createRouter, createWebHistory } from "vue-router"
import LoginPage from '@/components/pages/Login'
import MainPage from '@/components/pages/Main'
import NotFoundPage from '@/components/pages/NotFound'

const routes = [
  { path: '/', component: MainPage },
  { path: '/login', component: LoginPage },
  {
    path: '/:pathMatch(.*)*',
    component: NotFoundPage
}
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router