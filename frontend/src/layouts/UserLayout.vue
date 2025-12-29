<template>
  <div
    class="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark"
  >
    <header
      class="sticky top-0 z-50 flex items-center justify-center border-b border-border-light dark:border-border-dark backdrop-premium dark:backdrop-premium-dark shadow-sm transition-all duration-300"
    >
      <div
        class="flex h-16 w-full max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8"
      >
        <router-link to="/" class="flex items-center gap-4 cursor-pointer">
          <svg
            class="h-6 w-6 text-primary"
            fill="none"
            viewBox="0 0 48 48"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M44 4H30.6666V17.3334H17.3334V30.6666H4V44H44V4Z"
              fill="currentColor"
            ></path>
          </svg>
          <h2 class="text-xl font-bold text-text-light dark:text-text-dark">
            FoodGo
          </h2>
        </router-link>

        <!-- Desktop Navigation -->
        <nav class="hidden items-center gap-6 md:flex">
          <router-link
            to="/"
            class="text-sm font-semibold text-text-light dark:text-text-dark hover:text-primary transition-all duration-300 relative group px-3 py-2 rounded-lg hover:bg-primary/10 dark:hover:bg-primary/20"
          >
            Trang chủ
            <span class="absolute bottom-0 left-1/2 -translate-x-1/2 w-0 h-0.5 bg-primary transition-all group-hover:w-3/4 rounded-full"></span>
          </router-link>
          <router-link
            to="/search"
            class="text-sm font-semibold text-text-light dark:text-text-dark hover:text-primary transition-all duration-300 relative group px-3 py-2 rounded-lg hover:bg-primary/10 dark:hover:bg-primary/20"
          >
            Khám phá
            <span class="absolute bottom-0 left-1/2 -translate-x-1/2 w-0 h-0.5 bg-primary transition-all group-hover:w-3/4 rounded-full"></span>
          </router-link>

          <router-link
            v-if="!authStore.isAuthenticated"
            to="/auth/login"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary transition-colors relative group"
          >
            Đăng nhập
            <span class="absolute bottom-0 left-0 w-0 h-0.5 bg-primary transition-all group-hover:w-full"></span>
          </router-link>

          <div v-else class="relative group">
            <button
              class="flex items-center gap-2 text-sm font-medium text-text-light dark:text-text-dark hover:text-primary transition-colors"
            >
              <span class="material-symbols-outlined text-xl"
                >account_circle</span
              >
              <span class="hidden lg:inline">{{
                authStore.user?.fullName || authStore.user?.email
              }}</span>
            </button>

            <div
              class="absolute right-0 mt-2 w-48 bg-white dark:bg-surface-dark rounded-xl shadow-xl border border-border-light dark:border-border-dark opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 z-[110] overflow-hidden"
            >
              <router-link
                to="/profile"
                class="block px-4 py-3 text-sm text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-2"
              >
                <span class="material-symbols-outlined text-base">person</span>
                Hồ sơ cá nhân
              </router-link>
              <router-link
                to="/booking-history"
                class="block px-4 py-3 text-sm text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-2"
              >
                <span class="material-symbols-outlined text-base">history</span>
                Lịch sử đặt bàn
              </router-link>
              <router-link
                to="/membership"
                class="block px-4 py-3 text-sm text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-2"
              >
                <span class="material-symbols-outlined text-base">star</span>
                Đăng ký membership
              </router-link>
              <div class="border-t border-border-light dark:border-border-dark"></div>
              <button
                @click="handleLogout"
                class="block w-full text-left px-4 py-3 text-sm text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors flex items-center gap-2"
              >
                <span class="material-symbols-outlined text-base">logout</span>
                Đăng xuất
              </button>
            </div>
          </div>
        </nav>

        <!-- Mobile Menu Button -->
        <button
          @click="showMobileMenu = !showMobileMenu"
          class="md:hidden p-2 text-text-light dark:text-text-dark hover:text-primary transition-colors"
        >
          <span class="material-symbols-outlined text-2xl">
            {{ showMobileMenu ? 'close' : 'menu' }}
          </span>
        </button>

        <!-- Desktop Owner Button with Premium Style -->
        <button
          @click="handleOwnerClick"
          class="btn-premium hidden md:flex min-w-[84px] cursor-pointer items-center justify-center overflow-hidden rounded-full h-10 px-4 text-white text-sm font-bold shadow-lg hover:shadow-xl transition-all duration-300"
        >
          <span class="truncate">Dành cho chủ quán</span>
        </button>
      </div>
    </header>

    <transition name="fade">
      <div
        v-if="showOwnerModal"
        @click.self="showOwnerModal = false"
        class="fixed inset-0 z-[100] flex items-center justify-center bg-black/50 p-4"
      >
        <div
          class="relative bg-white dark:bg-surface-dark rounded-xl max-w-2xl w-full max-h-[90vh] overflow-y-auto p-6 shadow-lg border border-border-light dark:border-border-dark"
        >
          <button
            @click="showOwnerModal = false"
            class="absolute top-3 right-3 text-subtext-light hover:text-red-600"
          >
            <span class="material-symbols-outlined">close</span>
          </button>

          <h3 class="text-xl font-bold mb-2">Đăng ký làm chủ quán</h3>
          <p class="text-sm text-subtext-light mb-4">
            Nhập địa chỉ, chọn khu vực và nhấn tìm kiếm để ghim vị trí quán.
          </p>

          <form
            @submit.prevent="submitOwnerRegistration"
            class="grid grid-cols-1 sm:grid-cols-2 gap-4"
          >
            <div class="sm:col-span-2">
              <label class="block text-sm font-medium mb-1"
                >Tên quán <span class="text-red-500">*</span></label
              >
              <input
                v-model="ownerForm.name"
                class="w-full px-4 py-2 rounded-lg border outline-none"
                placeholder="Tên quán"
                required
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-1"
                >Loại quán <span class="text-red-500">*</span></label
              >
              <select
                v-model="ownerForm.typeId"
                class="w-full px-4 py-2 rounded-lg border outline-none"
              >
                <option value="">Chọn loại quán</option>
                <option v-for="t in types" :key="t.id" :value="t.id">
                  {{ t.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium mb-1"
                >Số điện thoại</label
              >
              <input
                v-model="ownerForm.phoneNumber"
                class="w-full px-4 py-2 rounded-lg border outline-none"
                placeholder="Số điện thoại"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-1">Tỉnh/Thành</label>
              <select
                v-model="ownerForm.provinceId"
                @change="onProvinceChange"
                class="w-full px-4 py-2 rounded-lg border outline-none"
              >
                <option value="">Chọn tỉnh</option>
                <option v-for="p in provinces" :key="p.id" :value="p.id">
                  {{ p.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium mb-1"
                >Quận/Huyện <span class="text-red-500">*</span></label
              >
              <select
                v-model="ownerForm.districtId"
                class="w-full px-4 py-2 rounded-lg border outline-none"
                :disabled="!ownerForm.provinceId"
              >
                <option value="">Chọn huyện</option>
                <option v-for="d in districts" :key="d.id" :value="d.id">
                  {{ d.name }}
                </option>
              </select>
            </div>

            <div class="sm:col-span-2">
              <label class="block text-sm font-medium mb-1"
                >Địa chỉ chi tiết & Định vị
                <span class="text-red-500">*</span></label
              >
              <div class="flex gap-2 mb-2">
                <input
                  v-model="ownerForm.address"
                  @keyup.enter="searchLocation"
                  class="flex-1 px-4 py-2 rounded-lg border outline-none focus:ring-2 focus:ring-primary/50"
                  placeholder="Ví dụ: 123 Lê Lợi..."
                  required
                />
                <button
                  type="button"
                  @click="searchLocation"
                  class="bg-primary text-white px-4 rounded-lg flex items-center justify-center hover:bg-opacity-90 transition-all"
                  title="Tìm vị trí"
                >
                  <span class="material-symbols-outlined">search_check</span>
                </button>
              </div>

              <div
                id="map-registration"
                class="w-full h-[280px] rounded-xl border border-border-light shadow-inner z-0"
              ></div>

              <div
                v-if="ownerForm.latitude"
                class="mt-2 p-2 bg-gray-50 dark:bg-gray-800 rounded text-[11px] text-gray-500 flex justify-between"
              >
                <span>Kinh độ: {{ ownerForm.longitude.toFixed(6) }}</span>
                <span>Vĩ độ: {{ ownerForm.latitude.toFixed(6) }}</span>
              </div>
            </div>

            <div class="sm:col-span-2">
              <label class="block text-sm font-medium mb-1">Mô tả</label>
              <textarea
                v-model="ownerForm.description"
                rows="2"
                class="w-full px-4 py-2 rounded-lg border outline-none"
                placeholder="Mô tả quán"
              ></textarea>
            </div>

            <div class="sm:col-span-2 flex justify-end gap-3 mt-4">
              <button
                type="button"
                @click="showOwnerModal = false"
                class="px-4 py-2 border rounded-lg hover:bg-gray-50"
              >
                Hủy
              </button>
              <button
                type="submit"
                :disabled="isSubmittingOwner"
                class="px-6 py-2 bg-primary text-white rounded-lg font-bold shadow-md hover:translate-y-[-1px] transition-all"
              >
                {{ isSubmittingOwner ? "Đang xử lý..." : "Đăng ký quán" }}
              </button>
            </div>
          </form>

          <div
            v-if="ownerError"
            class="mt-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm text-center"
          >
            {{ ownerError }}
          </div>
        </div>
      </div>
    </transition>

    <!-- Mobile Menu -->
    <Transition name="slide-down">
      <div
        v-if="showMobileMenu"
        class="md:hidden fixed inset-x-0 top-16 z-40 bg-white dark:bg-surface-dark border-b border-border-light dark:border-border-dark shadow-lg"
      >
        <nav class="flex flex-col p-4 space-y-2">
          <router-link
            to="/"
            @click="showMobileMenu = false"
            class="px-4 py-3 rounded-lg text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-3"
          >
            <span class="material-symbols-outlined">home</span>
            Trang chủ
          </router-link>
          <router-link
            to="/search"
            @click="showMobileMenu = false"
            class="px-4 py-3 rounded-lg text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-3"
          >
            <span class="material-symbols-outlined">explore</span>
            Khám phá
          </router-link>
          
          <div v-if="authStore.isAuthenticated" class="border-t border-border-light dark:border-border-dark pt-2 mt-2">
            <router-link
              to="/profile"
              @click="showMobileMenu = false"
              class="px-4 py-3 rounded-lg text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-3"
            >
              <span class="material-symbols-outlined">person</span>
              Hồ sơ cá nhân
            </router-link>
            <router-link
              to="/booking-history"
              @click="showMobileMenu = false"
              class="px-4 py-3 rounded-lg text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-3"
            >
              <span class="material-symbols-outlined">history</span>
              Lịch sử đặt bàn
            </router-link>
            <router-link
              to="/membership"
              @click="showMobileMenu = false"
              class="px-4 py-3 rounded-lg text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-3"
            >
              <span class="material-symbols-outlined">star</span>
              Đăng ký membership
            </router-link>
            <button
              @click="handleLogout; showMobileMenu = false"
              class="w-full text-left px-4 py-3 rounded-lg text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors flex items-center gap-3"
            >
              <span class="material-symbols-outlined">logout</span>
              Đăng xuất
            </button>
          </div>
          
          <router-link
            v-else
            to="/auth/login"
            @click="showMobileMenu = false"
            class="px-4 py-3 rounded-lg text-text-light dark:text-text-dark hover:bg-primary/10 dark:hover:bg-primary/20 transition-colors flex items-center gap-3"
          >
            <span class="material-symbols-outlined">login</span>
            Đăng nhập
          </router-link>
          
          <button
            @click="handleOwnerClick; showMobileMenu = false"
            class="w-full mt-4 px-4 py-3 rounded-lg bg-primary text-white font-bold hover:bg-opacity-90 transition-colors flex items-center justify-center gap-2"
          >
            <span class="material-symbols-outlined">store</span>
            Dành cho chủ quán
          </button>
        </nav>
      </div>
    </Transition>

    <main class="flex-1 w-full"><router-view /></main>

    <footer
      class="w-full bg-gradient-to-b from-surface-light to-surface-light/95 dark:from-surface-dark dark:to-surface-dark/95 border-t border-border-light dark:border-border-dark mt-auto"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <!-- Main Footer Content -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-8">
          <!-- Brand Section -->
          <div class="space-y-4">
            <div class="flex items-center gap-3">
              <svg
                class="h-8 w-8 text-primary"
                fill="none"
                viewBox="0 0 48 48"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M44 4H30.6666V17.3334H17.3334V30.6666H4V44H44V4Z"
                  fill="currentColor"
                ></path>
              </svg>
              <h3 class="text-2xl font-black text-text-light dark:text-text-dark">
                FoodGo
              </h3>
            </div>
            <p class="text-sm text-subtext-light dark:text-subtext-dark leading-relaxed">
              Khám phá và đặt bàn tại các nhà hàng ngon nhất Sài Gòn. 
              Trải nghiệm ẩm thực tuyệt vời cùng chúng tôi.
            </p>
            <!-- Social Media Links -->
            <div class="flex items-center gap-3 pt-2">
              <a
                href="https://facebook.com"
                target="_blank"
                rel="noopener noreferrer"
                class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center text-primary hover:bg-primary hover:text-white transition-all duration-300 hover:scale-110"
                aria-label="Facebook"
              >
                <span class="material-symbols-outlined text-lg">facebook</span>
              </a>
              <a
                href="https://instagram.com"
                target="_blank"
                rel="noopener noreferrer"
                class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center text-primary hover:bg-primary hover:text-white transition-all duration-300 hover:scale-110"
                aria-label="Instagram"
              >
                <span class="material-symbols-outlined text-lg">photo_camera</span>
              </a>
              <a
                href="https://youtube.com"
                target="_blank"
                rel="noopener noreferrer"
                class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center text-primary hover:bg-primary hover:text-white transition-all duration-300 hover:scale-110"
                aria-label="YouTube"
              >
                <span class="material-symbols-outlined text-lg">play_circle</span>
              </a>
              <a
                href="https://zalo.me"
                target="_blank"
                rel="noopener noreferrer"
                class="w-10 h-10 rounded-full bg-primary/10 dark:bg-primary/20 flex items-center justify-center text-primary hover:bg-primary hover:text-white transition-all duration-300 hover:scale-110"
                aria-label="Zalo"
              >
                <span class="material-symbols-outlined text-lg">chat</span>
              </a>
            </div>
          </div>

          <!-- Quick Links -->
          <div>
            <h4 class="text-lg font-bold text-text-light dark:text-text-dark mb-4">
              Liên kết nhanh
            </h4>
            <ul class="space-y-3">
              <li>
                <router-link
                  to="/"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Trang chủ</span>
                </router-link>
              </li>
              <li>
                <router-link
                  to="/search"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Khám phá</span>
                </router-link>
              </li>
              <li>
                <router-link
                  to="/membership"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Membership</span>
                </router-link>
              </li>
              <li>
                <a
                  href="#"
                  @click.prevent="handleOwnerClick"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Dành cho chủ quán</span>
                </a>
              </li>
            </ul>
          </div>

          <!-- Support -->
          <div>
            <h4 class="text-lg font-bold text-text-light dark:text-text-dark mb-4">
              Hỗ trợ
            </h4>
            <ul class="space-y-3">
              <li>
                <a
                  href="#"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Về chúng tôi</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Liên hệ</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Điều khoản sử dụng</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>Chính sách bảo mật</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors flex items-center gap-2 group"
                >
                  <span class="material-symbols-outlined text-base opacity-0 group-hover:opacity-100 transition-opacity">arrow_forward</span>
                  <span>FAQ</span>
                </a>
              </li>
            </ul>
          </div>

          <!-- Contact Info -->
          <div>
            <h4 class="text-lg font-bold text-text-light dark:text-text-dark mb-4">
              Liên hệ
            </h4>
            <ul class="space-y-3">
              <li class="flex items-start gap-3">
                <span class="material-symbols-outlined text-primary text-lg mt-0.5">location_on</span>
                <div>
                  <p class="text-sm font-medium text-text-light dark:text-text-dark">Địa chỉ</p>
                  <p class="text-sm text-subtext-light dark:text-subtext-dark">
                    123 Nguyễn Huệ, Quận 1<br />
                    TP. Hồ Chí Minh, Việt Nam
                  </p>
                </div>
              </li>
              <li class="flex items-start gap-3">
                <span class="material-symbols-outlined text-primary text-lg mt-0.5">call</span>
                <div>
                  <p class="text-sm font-medium text-text-light dark:text-text-dark">Hotline</p>
                  <a
                    href="tel:1900123456"
                    class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors"
                  >
                    1900 123 456
                  </a>
                </div>
              </li>
              <li class="flex items-start gap-3">
                <span class="material-symbols-outlined text-primary text-lg mt-0.5">mail</span>
                <div>
                  <p class="text-sm font-medium text-text-light dark:text-text-dark">Email</p>
                  <a
                    href="mailto:support@foodgo.vn"
                    class="text-sm text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors"
                  >
                    support@foodgo.vn
                  </a>
                </div>
              </li>
              <li class="flex items-start gap-3">
                <span class="material-symbols-outlined text-primary text-lg mt-0.5">schedule</span>
                <div>
                  <p class="text-sm font-medium text-text-light dark:text-text-dark">Giờ làm việc</p>
                  <p class="text-sm text-subtext-light dark:text-subtext-dark">
                    Thứ 2 - Chủ nhật<br />
                    8:00 - 22:00
                  </p>
                </div>
              </li>
            </ul>
          </div>
        </div>

        <!-- Bottom Bar -->
        <div class="border-t border-border-light dark:border-border-dark pt-8">
          <div class="flex flex-col md:flex-row items-center justify-between gap-4">
            <div class="flex flex-col md:flex-row items-center gap-4 text-sm text-subtext-light dark:text-subtext-dark">
              <p>&copy; 2025 FoodGo. All rights reserved.</p>
              <span class="hidden md:inline">•</span>
              <p>Made with <span class="text-red-500">❤️</span> in Ho Chi Minh City</p>
            </div>
            <div class="flex items-center gap-4">
              <span class="text-xs text-subtext-light dark:text-subtext-dark">Ngôn ngữ:</span>
              <button
                class="text-xs text-primary font-medium hover:underline"
                title="Tiếng Việt"
              >
                Tiếng Việt
              </button>
              <span class="text-xs text-subtext-light dark:text-subtext-dark">|</span>
              <button
                class="text-xs text-subtext-light dark:text-subtext-dark hover:text-primary transition-colors"
                title="English"
              >
                English
              </button>
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import {ref, onMounted, nextTick} from "vue";
import {useAuthStore} from "@/stores/auth";
import {useRouter} from "vue-router";
import {outletApi} from "@/api/outlet";
import {locationApi} from "@/api/location";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

// Fix Marker Icons
import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";
const defaultIcon = L.icon({
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
});

const authStore = useAuthStore();
const router = useRouter();

const showMobileMenu = ref(false);
const showOwnerModal = ref(false);
const isSubmittingOwner = ref(false);
const ownerError = ref("");
const ownerForm = ref({
  name: "",
  address: "",
  phoneNumber: "",
  description: "",
  typeId: "",
  provinceId: "",
  districtId: "",
  latitude: null,
  longitude: null,
});

const types = ref([]);
const provinces = ref([]);
const districts = ref([]);
let map = null;
let marker = null;

const handleLogout = () => {
  authStore.logout();
  router.push("/auth/login");
};

const handleOwnerClick = async () => {
  if (!authStore.isAuthenticated) return router.push("/auth/login");
  if (authStore.isOwner) return router.push("/owner");

  showOwnerModal.value = true;
  ownerError.value = "";
  await nextTick();
  setTimeout(() => initMap(), 300);
};

const initMap = () => {
  if (map) {
    map.remove();
    map = null;
  }
  map = L.map("map-registration").setView([10.7769, 106.7009], 13);
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap",
  }).addTo(map);
  map.on("click", (e) => {
    updateMarker(e.latlng.lat, e.latlng.lng);
  });
};

const updateMarker = (lat, lng) => {
  ownerForm.value.latitude = lat;
  ownerForm.value.longitude = lng;
  if (marker) {
    marker.setLatLng([lat, lng]);
  } else {
    marker = L.marker([lat, lng], {icon: defaultIcon, draggable: true}).addTo(
      map
    );
    marker.on("dragend", (event) => {
      const position = event.target.getLatLng();
      ownerForm.value.latitude = position.lat;
      ownerForm.value.longitude = position.lng;
    });
  }
  map.flyTo([lat, lng], 17);
};

// --- FIX LỖI TÌM KIẾM ĐỊA CHỈ ---
const searchLocation = async () => {
  const address = ownerForm.value.address;
  if (!address || address.length < 3) {
    ownerError.value = "Vui lòng nhập địa chỉ cụ thể hơn.";
    return;
  }

  const provinceObj = provinces.value.find(
    (p) => p.id === ownerForm.value.provinceId
  );
  const districtObj = districts.value.find(
    (d) => d.id === ownerForm.value.districtId
  );

  const provinceName = provinceObj ? provinceObj.name : "";
  const districtName = districtObj ? districtObj.name : "";

  // Thử tìm theo cấu trúc đầy đủ nhất
  let fullQuery = `${address}, ${districtName}, ${provinceName}, Việt Nam`;

  try {
    const response = await fetch(
      `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
        fullQuery
      )}&limit=1`
    );
    const data = await response.json();

    if (data && data.length > 0) {
      updateMarker(parseFloat(data[0].lat), parseFloat(data[0].lon));
      ownerError.value = "";
    } else {
      // BACKUP: Nếu không ra, thử tìm theo Địa chỉ + Tỉnh (bỏ Quận vì đôi khi tên Quận bị lệch chuẩn)
      const fallbackQuery = `${address}, ${provinceName}, Việt Nam`;
      const fbRes = await fetch(
        `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
          fallbackQuery
        )}&limit=1`
      );
      const fbData = await fbRes.json();

      if (fbData && fbData.length > 0) {
        updateMarker(parseFloat(fbData[0].lat), parseFloat(fbData[0].lon));
        ownerError.value = "";
      } else {
        ownerError.value =
          "Không tìm thấy vị trí. Bạn hãy tự ghim điểm trên bản đồ.";
      }
    }
  } catch (err) {
    ownerError.value = "Lỗi khi tìm kiếm địa chỉ.";
  }
};

const onProvinceChange = async () => {
  const provId = ownerForm.value.provinceId;
  districts.value = [];
  ownerForm.value.districtId = "";
  if (!provId) return;
  try {
    const d = await locationApi.getDistricts(provId);
    districts.value = d || [];
  } catch (e) {
    console.error("Lỗi tải quận huyện");
  }
};

const submitOwnerRegistration = async () => {
  if (!ownerForm.value.latitude) {
    ownerError.value = "Vui lòng ghim vị trí quán trên bản đồ.";
    return;
  }
  isSubmittingOwner.value = true;
  try {
    const payload = {
      ...ownerForm.value,
      typeId: Number(ownerForm.value.typeId),
      districtId: Number(ownerForm.value.districtId),
    };
    await outletApi.createOutlet(payload);
    await authStore.fetchUserProfile();
    showOwnerModal.value = false;
    router.push("/owner");
  } catch (err) {
    ownerError.value = err.message || "Đăng ký thất bại";
  } finally {
    isSubmittingOwner.value = false;
  }
};

onMounted(async () => {
  try {
    const [t, p] = await Promise.all([
      outletApi.getTypes(),
      locationApi.getProvinces(),
    ]);
    types.value = t || [];
    provinces.value = p || [];
  } catch (e) {
    console.warn("Lỗi tải dữ liệu");
  }
});
</script>

<style scoped>
#map-registration {
  background: #f8f9fa;
  cursor: crosshair;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
