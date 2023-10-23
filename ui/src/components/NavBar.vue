<template>
  <div class="flex w-full h-16 flex-row justify-between border-e bg-white">
    <div>
      <div class="inline-flex h-16 w-16 items-center justify-center">
        <img
          class="inline-block h-10 w-10 rounded-full ring-2"
          :src=data?.picture
          alt=""
        />
      </div>
    </div>

    <div
      class="sticky inset-x-0 bottom-0 border-t border-gray-100 bg-white p-2"
    >
      <button
        @click="logout"
        class="group relative flex w-full justify-center rounded-lg px-2 py-1.5 text-sm text-gray-500 hover:bg-gray-50 hover:text-gray-700"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5 opacity-75"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          stroke-width="2"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
          />
        </svg>
      </button>
    </div>
  </div>
</template>
<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ref } from "vue";
const setup = (props) => {
  const store = useStore();
  const router = useRouter();
  const user = ref({});
  user.value = props.data;
  const logout = (e) => {
    e.preventDefault();
    // store.commit("setUser", null);
    store.commit("setConversation", null)
    store.commit("setConversation", null)
    store.commit("setConversation", null)
    store.commit("closeSocket")
    store.commit("setMessageList", [])
    store.commit("setRecentList", [])
    document.cookie =
      "access_token=; expires=" + new Date() + "; domain=localhost;Path=/";
    router.push("/login");
  };

  return { logout, user };
};
export default {
  name: "NavBar",
  setup,
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
};
</script>
