import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import axios from 'axios'
import store from '@/store/index'

axios.defaults.withCredentials = true
const app = createApp(App)
app.use(router)
app.use(store)
app.mount('#app')
