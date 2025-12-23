<template>
  <router-view />
  <ToastContainer />
  <ConfirmDialog />
</template>

<script setup>
import ToastContainer from "@/components/common/ToastContainer.vue";
import ConfirmDialog from "@/components/common/ConfirmDialog.vue";
</script>

<style scoped>
/* Global styles in style.css */
</style>
