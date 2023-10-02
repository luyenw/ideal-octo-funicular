<template>
  <router-view/>
</template>
<script>
import { useRoute } from 'vue-router';
  const setup = ()=>{
    const route = useRoute()
    console.log(route.fullPath)
  }
  export default{
    name: "App",
    setup
  }
</script>