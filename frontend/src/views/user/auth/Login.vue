<template>
  <div
    class="relative flex h-auto min-h-screen w-full flex-col bg-background-light dark:bg-background-dark font-manrope"
  >
    <div class="flex h-full grow flex-col">
      <main class="flex min-h-screen w-full items-center justify-center">
        <div
          class="flex w-full max-w-6xl overflow-hidden rounded-xl shadow-2xl bg-white dark:bg-[#231510]"
        >
          <div class="w-full lg:w-1/2 p-8 sm:p-12 flex flex-col justify-center">
            <div class="max-w-md mx-auto w-full">
              <h1
                class="text-black dark:text-white tracking-light text-[32px] font-bold leading-tight pb-3"
              >
                Chào mừng trở lại!
              </h1>
              <p
                class="text-gray-600 dark:text-white/80 text-base font-normal leading-normal pb-6"
              >
                Khám phá thế giới ẩm thực Sài Gòn cùng chúng tôi.
              </p>

              <!-- Tabs -->
              <div class="flex py-3">
                <div
                  class="flex h-10 flex-1 items-center justify-center rounded-lg bg-gray-100 dark:bg-[#492d22] p-1"
                >
                  <label
                    class="flex cursor-pointer h-full grow items-center justify-center overflow-hidden rounded-md px-2 has-[:checked]:bg-white dark:has-[:checked]:bg-[#231510] has-[:checked]:shadow-sm has-[:checked]:text-black dark:has-[:checked]:text-white text-gray-500 dark:text-[#cba090] text-sm font-medium leading-normal"
                  >
                    <span class="truncate">Đăng nhập</span>
                    <input checked class="invisible w-0" type="radio" />
                  </label>
                  <router-link
                    to="/auth/register"
                    class="flex cursor-pointer h-full grow items-center justify-center overflow-hidden rounded-md px-2 text-gray-500 dark:text-[#cba090] text-sm font-medium leading-normal"
                  >
                    <span class="truncate">Đăng ký</span>
                  </router-link>
                </div>
              </div>

              <!-- Error Message -->
              <div
                v-if="errorMessage"
                class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mt-4"
              >
                {{ errorMessage }}
              </div>

              <form class="space-y-6 mt-4" @submit.prevent="handleLogin">
                <div class="flex flex-col">
                  <label class="flex flex-col min-w-40 flex-1">
                    <p
                      class="text-black dark:text-white text-sm font-medium leading-normal pb-2"
                    >
                      Email hoặc Số điện thoại
                    </p>
                    <input
                      v-model="formData.email"
                      class="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-black dark:text-white focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] h-12 placeholder:text-gray-400 dark:placeholder:text-[#cba090] px-4 text-base font-normal leading-normal"
                      placeholder="Nhập email hoặc số điện thoại của bạn"
                    />
                  </label>
                </div>

                <div class="flex flex-col">
                  <div class="flex justify-between items-center mb-2">
                    <p
                      class="text-black dark:text-white text-sm font-medium leading-normal"
                    >
                      Mật khẩu
                    </p>
                    <router-link
                      to="/auth/forgot-password"
                      class="text-sm font-medium text-primary hover:underline"
                    >
                      Quên mật khẩu?
                    </router-link>
                  </div>
                  <label class="flex flex-col min-w-40 flex-1">
                    <div class="flex w-full flex-1 items-stretch rounded-lg">
                      <input
                        v-model="formData.password"
                        :type="showPassword ? 'text' : 'password'"
                        class="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-black dark:text-white focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] h-12 placeholder:text-gray-400 dark:placeholder:text-[#cba090] p-4 rounded-r-none border-r-0 pr-2 text-base font-normal leading-normal"
                        placeholder="Nhập mật khẩu"
                      />
                      <button
                        type="button"
                        @click="showPassword = !showPassword"
                        class="text-gray-500 dark:text-[#cba090] flex border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] items-center justify-center px-3 rounded-r-lg border-l-0"
                      >
                        <span class="material-symbols-outlined text-xl">{{
                          showPassword ? "visibility_off" : "visibility"
                        }}</span>
                      </button>
                    </div>
                  </label>
                </div>

                <div class="flex items-center">
                  <input
                    type="checkbox"
                    id="remember"
                    class="w-4 h-4 text-primary bg-gray-100 border-gray-300 rounded focus:ring-primary focus:ring-2"
                  />
                  <label
                    for="remember"
                    class="ml-2 text-sm text-gray-900 dark:text-gray-300"
                    >Ghi nhớ đăng nhập</label
                  >
                </div>

                <button
                  type="submit"
                  :disabled="isLoading"
                  class="flex w-full items-center justify-center rounded-lg h-12 px-5 bg-primary text-white text-base font-bold leading-normal tracking-[0.015em] hover:bg-opacity-90 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <span v-if="!isLoading" class="truncate">Đăng nhập</span>
                  <span v-else class="truncate">Đang đăng nhập...</span>
                </button>
              </form>
            </div>
          </div>

          <!-- Right Side Image -->
          <div
            class="hidden lg:block lg:w-1/2 bg-cover bg-center"
            style="
              background-image: url('https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800');
            "
          ></div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth";

const router = useRouter();
const authStore = useAuthStore();

const showPassword = ref(false);
const isLoading = ref(false);
const errorMessage = ref("");

const formData = ref({
  email: "",
  password: "",
});

const handleLogin = async () => {
  console.log("🔵 handleLogin called", formData.value);
  errorMessage.value = "";
  isLoading.value = true;

  try {
    console.log("🔵 Calling authStore.login...");
    await authStore.login({
      emailOrPhone: formData.value.email,
      password: formData.value.password,
    });

    console.log("✅ Login success! User:", authStore.user);
    console.log(
      "✅ isAdmin:",
      authStore.isAdmin,
      "isOwner:",
      authStore.isOwner
    );

    // Redirect based on role
    if (authStore.isAdmin) {
      console.log("➡️ Redirecting to /admin");
      router.push("/admin");
    } else if (authStore.isOwner) {
      console.log("➡️ Redirecting to /owner");
      router.push("/owner");
    } else {
      console.log("➡️ Redirecting to /");
      router.push("/");
    }
  } catch (error) {
    console.error("❌ Login error:", error);
    errorMessage.value =
      error.message || "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.";
  } finally {
    isLoading.value = false;
  }
};
</script>
