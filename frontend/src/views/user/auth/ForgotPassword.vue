<template>
  <div
    class="relative flex h-auto min-h-screen w-full flex-col bg-background-light dark:bg-background-dark font-manrope"
  >
    <div class="flex h-full grow flex-col items-center justify-center p-4">
      <div
        class="w-full max-w-md bg-white dark:bg-[#231510] rounded-xl shadow-2xl p-8"
      >
        <h1 class="text-black dark:text-white text-2xl font-bold mb-2">
          Quên mật khẩu?
        </h1>
        <p class="text-gray-600 dark:text-white/80 text-sm mb-6">
          Nhập email của bạn để nhận link đặt lại mật khẩu
        </p>

        <form @submit.prevent="handleSubmit">
          <div class="mb-6">
            <label
              class="text-black dark:text-white text-sm font-medium mb-2 block"
              >Email</label
            >
            <input
              v-model="email"
              type="email"
              placeholder="email@example.com"
              class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
            />
          </div>

          <button
            type="submit"
            class="w-full h-12 bg-primary text-white rounded-lg font-bold hover:bg-opacity-90 transition-colors mb-4"
          >
            Gửi link đặt lại mật khẩu
          </button>

          <router-link
            to="/auth/login"
            class="block text-center text-primary hover:underline text-sm"
          >
            Quay lại đăng nhập
          </router-link>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const email = ref("");

const handleSubmit = () => {
  console.log("Forgot password:", email.value);
  // Show success message
  alert("Link đặt lại mật khẩu đã được gửi đến email của bạn");
  router.push("/auth/login");
};
</script>
