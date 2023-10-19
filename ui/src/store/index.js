import Vuex from "vuex";
import io from 'socket.io-client'
export default new Vuex.Store({
  state: {
    user: null,
    conversation: null,
    socket: null,
    detail_id: null,
    message_list: []
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    setConversation(state, conversation) {
      state.conversation = conversation;
    },
    setSocket(state, url){
      if (state.socket != null)
        state.socket.disconnect()
      state.socket = io(url)
    },
    setDetailId(state, id){
      state.detail_id = id
    },
    setMessageList(state, list){
      state.message_list = list
    },
    appendMessageList(state, message){
      state.message_list.push(message)
    }
  },
  actions: {},
  getters: {
    isLoggedIn(state) {
      return state.user ? true : false;
    },
    user(state) {
      return state.user;
    },
    conversation(state) {
      return state.conversation;
    },
    socket(state){
      return state.socket;
    },
    detail_id(state){
      return state.detail_id
    },
    messageList(state){
      return state.message_list
    }
  },
});
