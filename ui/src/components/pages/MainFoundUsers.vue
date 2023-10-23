<template>
  <div v-for="item in foundUsers" :key="item.id">
    <MainFoundUsersItem :data="item" @click="update_conversation(item)" />
  </div>
</template>
<script>
import MainFoundUsersItem from "@/components/pages/MainFoundUsersItem";
import { mapGetters, useStore } from "vuex";
export default {
  name: "MainFoundUsers",
  setup() {
    const store = useStore();
    return {
      store,
    };
  },
  computed: {
    ...mapGetters(["foundUsers"]),
  },
  components: {
    MainFoundUsersItem,
  },
  methods: {
    update_conversation(item) {
      this.store.commit("setConversation", item.id);
      this.store.commit("setDetailId", item.id);
    },
  },
};
</script>
