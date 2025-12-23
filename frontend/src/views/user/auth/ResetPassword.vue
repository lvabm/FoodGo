<template>
  <div
    class="relative flex h-auto min-h-screen w-full flex-col bg-background-light dark:bg-background-dark font-manrope"
  >
    <div class="flex h-full grow flex-col items-center justify-center p-4">
      <div
        class="w-full max-w-md bg-white dark:bg-[#231510] rounded-xl shadow-2xl p-8"
      >
        <h1 class="text-black dark:text-white text-2xl font-bold mb-2">
          Đặt lại mật khẩu
        </h1>
        <p class="text-gray-600 dark:text-white/80 text-sm mb-6">
          Nhập mật khẩu mới của bạn
        </p>

        <form @submit.prevent="handleSubmit">
          <div class="mb-4">
            <label
              class="text-black dark:text-white text-sm font-medium mb-2 block"
              >Mật khẩu mới</label
            >
            <input
              v-model="formData.password"
              type="password"
              placeholder="Nhập mật khẩu mới"
              class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
            />
          </div>

          <div class="mb-6">
            <label
              class="text-black dark:text-white text-sm font-medium mb-2 block"
              >Xác nhận mật khẩu</label
            >
            <input
              v-model="formData.confirmPassword"
              type="password"
              placeholder="Nhập lại mật khẩu mới"
              class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
            />
          </div>

          <button
            type="submit"
            class="w-full h-12 bg-primary text-white rounded-lg font-bold hover:bg-opacity-90 transition-colors"
          >
            Đặt lại mật khẩu
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const formData = ref({
  password: "",
  confirmPassword: "",
});

const handleSubmit = () => {
  console.log("Reset password:", formData.value);
  alert("Mật khẩu đã được đặt lại thành công");
  router.push("/auth/login");
};
</script>
