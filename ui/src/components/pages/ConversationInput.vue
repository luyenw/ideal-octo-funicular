<template>
  <div class="bg-grey-lighter px-4 py-4 flex items-center">
    <div class="flex-1 mx-4">
      <form @submit="emitValue">
        <input
          v-model="input"
          class="w-full border rounded px-2 py-2"
          type="text"
          placeholder="Type your message.."
        />
        <input type="submit" hidden />
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name: "ConversationInput",
  data() {
    return {
      input: "",
    };
  },
  methods: {
    emitValue(e) {
      e.preventDefault();
      this.$emit("inputChange", this.input);
      this.input = ''
    },
  },
};
</script>
