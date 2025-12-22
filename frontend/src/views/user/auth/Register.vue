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
                Tạo tài khoản mới
              </h1>
              <p
                class="text-gray-600 dark:text-white/80 text-base font-normal leading-normal pb-6"
              >
                Tham gia cùng chúng tôi để khám phá ẩm thực Sài Gòn.
              </p>

              <!-- Tabs -->
              <div class="flex py-3">
                <div
                  class="flex h-10 flex-1 items-center justify-center rounded-lg bg-gray-100 dark:bg-[#492d22] p-1"
                >
                  <router-link
                    to="/auth/login"
                    class="flex cursor-pointer h-full grow items-center justify-center overflow-hidden rounded-md px-2 text-gray-500 dark:text-[#cba090] text-sm font-medium leading-normal"
                  >
                    <span class="truncate">Đăng nhập</span>
                  </router-link>
                  <label
                    class="flex cursor-pointer h-full grow items-center justify-center overflow-hidden rounded-md px-2 has-[:checked]:bg-white dark:has-[:checked]:bg-[#231510] has-[:checked]:shadow-sm has-[:checked]:text-black dark:has-[:checked]:text-white text-gray-500 dark:text-[#cba090] text-sm font-medium leading-normal"
                  >
                    <span class="truncate">Đăng ký</span>
                    <input checked class="invisible w-0" type="radio" />
                  </label>
                </div>
              </div>

              <!-- Error Message -->
              <div
                v-if="errorMessage"
                class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mt-4"
              >
                {{ errorMessage }}
              </div>

              <!-- Success Message -->
              <div
                v-if="successMessage"
                class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg mt-4"
              >
                {{ successMessage }}
              </div>

              <form class="space-y-4 mt-4" @submit.prevent="handleRegister">
                <div>
                  <label
                    class="text-black dark:text-white text-sm font-medium mb-2 block"
                    >Họ và tên</label
                  >
                  <input
                    v-model="formData.name"
                    type="text"
                    placeholder="Nhập họ và tên"
                    class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
                  />
                </div>

                <div>
                  <label
                    class="text-black dark:text-white text-sm font-medium mb-2 block"
                    >Email</label
                  >
                  <input
                    v-model="formData.email"
                    type="email"
                    placeholder="email@example.com"
                    class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
                  />
                </div>

                <div>
                  <label
                    class="text-black dark:text-white text-sm font-medium mb-2 block"
                    >Số điện thoại</label
                  >
                  <input
                    v-model="formData.phone"
                    type="tel"
                    placeholder="0901234567"
                    class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
                  />
                </div>

                <div>
                  <label
                    class="text-black dark:text-white text-sm font-medium mb-2 block"
                    >Mật khẩu</label
                  >
                  <input
                    v-model="formData.password"
                    type="password"
                    placeholder="Nhập mật khẩu"
                    class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
                  />
                </div>

                <div>
                  <label
                    class="text-black dark:text-white text-sm font-medium mb-2 block"
                    >Xác nhận mật khẩu</label
                  >
                  <input
                    v-model="formData.confirmPassword"
                    type="password"
                    placeholder="Nhập lại mật khẩu"
                    class="w-full h-12 px-4 rounded-lg border border-gray-300 dark:border-[#684031] bg-gray-50 dark:bg-[#342018] text-black dark:text-white focus:ring-2 focus:ring-primary/50"
                  />
                </div>

                <button
                  type="submit"
                  :disabled="isLoading"
                  class="w-full h-12 bg-primary text-white rounded-lg font-bold hover:bg-opacity-90 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <span v-if="!isLoading">Đăng ký</span>
                  <span v-else>Đang xử lý...</span>
                </button>
              </form>
            </div>
          </div>

          <!-- Right Side Image -->
          <div
            class="hidden lg:block lg:w-1/2 bg-cover bg-center"
            style="
              background-image: url('https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=800');
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

const isLoading = ref(false);
const errorMessage = ref("");
const successMessage = ref("");

const formData = ref({
  name: "",
  email: "",
  phone: "",
  password: "",
  confirmPassword: "",
});

const handleRegister = async () => {
  errorMessage.value = "";
  successMessage.value = "";

  // Validate
  if (formData.value.password !== formData.value.confirmPassword) {
    errorMessage.value = "Mật khẩu xác nhận không khớp";
    return;
  }

  isLoading.value = true;

  try {
    await authStore.register({
      name: formData.value.name,
      email: formData.value.email,
      phone: formData.value.phone,
      password: formData.value.password,
    });

    successMessage.value = "Đăng ký thành công! Đang chuyển hướng...";

    setTimeout(() => {
      // Check role and redirect
      if (authStore.isAdmin) {
        router.push("/admin");
      } else if (authStore.isOwner) {
        router.push("/owner");
      } else {
        router.push("/");
      }
    }, 1500);
  } catch (error) {
    errorMessage.value = error.message || "Đăng ký thất bại. Vui lòng thử lại.";
  } finally {
    isLoading.value = false;
  }
};
</script>
