<template>
  <div
    v-if="conversation"
    class="py-2 px-3 bg-grey-lighter flex flex-row justify-between items-center"
  >
    <div class="flex items-center">
      <div>
        <img class="w-10 h-10 rounded-full" :src="groupData?.picture" />
      </div>
      <div class="ml-4">
        <p class="text-grey-darkest">{{ groupData?.name }}</p>
      </div>
    </div>
  </div>

  <!-- Messages -->
  <div
    class="flex-1 overflow-auto"
    id="parentDiv"
    style="background-color: #dad3cc"
  >
    <div class="py-1 px-2" v-for="(item, index) in messageList" :key="index">
      <MessageItem :data="item" :ownMessage="item.userId == user.id" />
    </div>
  </div>
</template>
<script>
import MessageItem from "@/components/pages/ConversationMessageItem.vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { mapGetters, useStore } from "vuex";
export default {
  name: "ConversationContainer",
  setup() {
    const store = useStore();
    const router = useRouter();
    return {
      store,
      router,
    };
  },
  computed: {
    ...mapGetters(["conversation", "user", "detail_id", "messageList"]),
  },
  data() {
    return {
      groupData: {},
    };
  },
  watch: {
    async conversation(newValue, oldValue) {
      oldValue;
      newValue;
      try {
        var response = await axios.get(
          `http://localhost:3001/u/${this.detail_id}`
        );
        this.groupData = await response.data;

        response = await axios.get(
          `http://localhost:8080/message/${this.conversation}`
        );
        this.store.commit("setMessageList", response.data);
        if (response.data[0]) {
          this.store.commit("setConversation", response.data[0].groupId);
        }
      } catch (err) {
        console.log(err);
        this.store.commit("setMessageList", []);
      }
    },
    /* eslint-disable */
    messageList(newValue, oldValue) {
      var element = document.getElementById("parentDiv");
      const h = element.scrollHeight;
      console.log(element);
      element.scrollTop = h;
    },
  },
  components: {
    MessageItem,
  },
};
</script>
