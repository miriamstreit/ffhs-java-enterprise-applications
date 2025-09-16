<template>
  <v-layout>
    <Navigation />
    <v-main class="ma-12">
      <router-view />
    </v-main>
    <Snackbar ref="snackbar" />
  </v-layout>
</template>

<script>
import Navigation from './components/Navigation.vue';
import Snackbar from "./components/Snackbar.vue";

export default {
  components: {
    Navigation,
    Snackbar
  },
  mounted() {
    window.app.snackbar = this.$refs.snackbar;
  }
};
</script>