<template>
  <div class="flex flex-row h-screen">
    <!-- left -->
    <div class="w-1/4 flex-col border-r shadow-md">
      <NavBar :data="user" />
      <div class="relative px-4">
        <label for="Search" class="sr-only"> Search </label>
        <input
          @input="find_user_onchange"
          @focus="search_input_focus = true"
          @blur="search_input_focus = false"
          v-model="search_input"
          type="text"
          id="search"
          placeholder="Search"
          class="w-full px-4 rounded-full bg-gray-200 py-2.5 pe-10 sm:text-sm"
        />
        <span class="absolute inset-y-0 end-4 grid w-10 place-content-center">
          <button type="button" class="text-gray-600 hover:text-gray-700">
            <span class="sr-only">Search</span>

            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="h-4 w-4 mr-4"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"
              />
            </svg>
          </button>
        </span>
      </div>
      <div class="mt-4 px-2">
        <div v-if="search_input == ''">
          <MainRecentMessage />
        </div>
        <div v-else>
          <MainFoundUsers />
        </div>
      </div>
    </div>
    <div class="w-3/4 flex flex-col">
      <ConversationContainer />
      <ConversationInput @inputChange="handleInputChange" />
    </div>
  </div>
</template>
<script>
import ConversationContainer from "@/components/pages/ConversationContainer.vue";
import ConversationInput from "@/components/pages/ConversationInput.vue";
import MainRecentMessage from "@/components/pages/MainRecentMessage.vue";
import MainFoundUsers from "@/components/pages/MainFoundUsers";
import axios from "axios";
import { useRouter } from "vue-router";
import { mapGetters, useStore } from "vuex";
import NavBar from "../NavBar.vue";
import services from "@/services/index";
import io from "socket.io-client";
export default {
  name: "MainPage",
  setup() {
    const store = useStore();
    const router = useRouter();
    axios
      .get("http://localhost:3001/verify")
      .then((response) => {
        store.commit("setUser", response.data);
        const socket = io(
          `ws://192.168.1.123:8085?room=${store.getters["user"].id}`
        );
        socket.on("new_message", async () => {
          const recent_list = await services.getRecentMessage(
            store.getters["user"].id
          );
          store.commit("setRecentList", recent_list);
        });
      })
      .catch((err) => {
        console.log(err);
        router.replace("login");
      });
    return {
      store,
    };
  },
  data() {
    return {
      message_input: "",
      search_input: "",
      search_input_focus: false,
      debounded_search_timeout: null,
    };
  },
  computed: {
    ...mapGetters(["user", "conversation", "socket", "foundUsers"]),
  },
  methods: {
    handleInputChange(value) {
      this.message_input = value;
      this.sent(this.message_input);
    },
    sent() {
      const new_message = {
            message: this.message_input,
            userId: this.user?.id,
            groupId: this.conversation,
          }
      if (!this.conversation?.includes("-")) {
        axios.get("http://localhost:8080/message/request/uuid").then((res) => {
          this.store.commit(
            "setSocket",
            `ws://192.168.1.123:8085?room=${res.data}`
          );
          this.socket.emit('new_group', {
            groupId: res.data,
            userId: this.user.id
          })
          this.socket.emit('new_group', {
            groupId: res.data,
            userId: this.conversation
          })
          this.store.commit("setConversation", res.data);
          new_message.groupId = this.conversation
          this.socket.emit("send_message", new_message);
        });
      } else {
        this.socket.emit("send_message", {
          message: this.message_input,
          userId: this.user?.id,
          groupId: this.conversation,
        });
      }

      this.message_input = "";
    },
    debounded_search(value) {
      clearTimeout(this.debounded_search_timeout);
      this.debounded_search_timeout = setTimeout(async () => {
        const users = await services.findUser(value);
        this.store.commit("setFoundUsers", users);
        console.log(this.foundUsers);
      }, 500);
    },
    find_user_onchange() {
      this.debounded_search(this.search_input);
    },
  },
  mounted() {},
  components: {
    NavBar,
    MainRecentMessage,
    ConversationContainer,
    ConversationInput,
    MainFoundUsers,
  },
};
</script>
