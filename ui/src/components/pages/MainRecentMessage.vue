<template>
  <div class="mt-4">
    <div
      v-for="item in recentMsg"
      :key="item.userId"
      class="divide-y divide-slate-200"
    >
      <RecentMessageItem
        :data="item"
        @click="update_conversation(item)"
      />
    </div>
  </div>
</template>
<script>
import RecentMessageItem from "@/components/pages/MainRecentMessageItem.vue";
import axios from "axios";
import { onMounted, ref } from "vue";
import { mapGetters, useStore } from "vuex";
export default {
  name: "MainRecentMessage",
  setup() {
    const recentMsg = ref([]);
    const store = useStore();

    const id = store.getters["user"].id;
    const url = `http://localhost:8080/recent/${id}`;
    onMounted(async () => {
      try {
        const response = await axios.get(url);
        recentMsg.value = response.data;
      } catch (err) {
        console.log(err);
      }
    });

    const update_conversation = (item) => {
      store.commit("setConversation", item.groupId);
      store.commit("setDetailId", item.userId);
      store.commit("setSocket", `ws://192.168.0.101:8085?room=${item.groupId}`)
      if (store.getters['socket'])
        store.getters['socket'].on("get_message", (msg) => {
        console.log(msg) 
        store.commit('appendMessageList', msg) 
      });
      };
    return {
      recentMsg,
      update_conversation,
    };
  },

  computed: {
    ...mapGetters(["user"]),
  },
  components: {
    RecentMessageItem,
  },
};
</script>
