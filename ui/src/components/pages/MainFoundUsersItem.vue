<template>
    <div class="px-2 flex items-center hover:bg-gray-100 cursor-pointer rounded-md">
    <div>
      <img class="h-10 w-10 rounded-full" :src="data.picture" />
    </div>
    <div class="ml-2 flex-1 py-4">
      <div class="flex items-bottom justify-between">
        <p class="text-grey-darkest">{{ data.name }}</p>
      </div>
    </div>
  </div>
</template>
<script>
    export default{
        name: "MainFoundUsersItem",
        props: {
            data: {
                required: true
            }
        }
    }
</script>