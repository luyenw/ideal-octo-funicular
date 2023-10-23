<template>
  <div id="message-container" :class="ownMessage?'justify-end':''" class="flex">
    <div class="rounded p-2" :class="ownMessage?'bg-lime-100':'bg-sky-100'">
      <p class="text-sm">{{ data.message }}</p>
      <p class="text-right text-xs text-grey-dark mt-1">{{ timeago(data.messageId) }}</p>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import utils from '@/utils/index'
import { format } from 'timeago.js';
export default {
  name: "MessageItem",
  setup(){
  },
  computed: {
    ...mapGetters(["user"]),
  },
  props: {
    data: {
      required: true,
    },
    ownMessage: {
      type: Boolean,
      required: true
    }
  },
  methods:{
    timeago(uuid){
      return format(utils.convertTimeUUIDToDate(uuid))
    }
  }
};
</script>
