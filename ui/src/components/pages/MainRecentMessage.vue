<template>
    <div
      v-for="item in recentList"
      :key="item.userId"
      class="divide-y divide-slate-200"
    >
      <RecentMessageItem
        :data="item"
        @click="update_conversation(item)"
      />
    </div>
</template>
<script>
import RecentMessageItem from "@/components/pages/MainRecentMessageItem.vue";
import services from '@/services/index';
import { onMounted } from "vue";
import { mapGetters, useStore } from "vuex";
export default {
  name: "MainRecentMessage",
  setup() {
    const store = useStore();
    const id = store.getters["user"].id;
    onMounted(async () => {
      const recent_list = await services.getRecentMessage(id)
      store.commit("setRecentList", recent_list)
    });
    return {
      store
    };
  },
  methods:{
    update_conversation(item){
      this.store.commit("setConversation", item.groupId);
      this.store.commit("setDetailId", item.userId);
      this.store.commit("setSocket", `ws://192.168.1.123:8085?room=${item.groupId}`)
      if (this.store.getters['socket'])
        this.store.getters['socket'].on("get_message", (msg) => {
        this.store.commit('appendMessageList', msg)
      });
    }
  },
  computed: {
    ...mapGetters(["user", "recentList"]),
  },
  components: {
    RecentMessageItem,
  },
};
</script>
