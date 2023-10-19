<template>
  <div
    class="px-2 flex items-center hover:bg-slate-100 cursor-pointer"
  >
    <div>
      <img
        class="h-10 w-10 rounded-full"
        :src=user.picture
      />
    </div>
    <div class="ml-2 flex-1 border-b border-grey-lighter py-4">
      <div class="flex items-bottom justify-between">
        <p class="text-grey-darkest">{{ user.name }}</p>
        <p class="text-xs text-grey-darkest">{{ timeago(data.at) }}</p>
      </div>
      <p class="text-grey-dark text-xs">{{ data.message }}</p>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { onMounted, ref, toRefs } from "vue";
import { format } from 'timeago.js';
export default {
  name: "RecentMessageItem",
  setup(props) {
    const data = toRefs(props);
    const user = ref({});
    onMounted(async () => {
      try {
        const response = await axios.get(
          `http://localhost:3001/u/${data.data.value.userId}`
        );
        user.value = response.data;
      } catch (err) {
        console.log(err);
      }
    });
    return {
      user,
    };
  },
  methods:{
    timeago(seconds){
      return format(seconds)
    }
  },
  props: {
    data: {
      required: true,
    },
  },
};
</script>
