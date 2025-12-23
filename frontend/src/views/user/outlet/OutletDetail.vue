<template>
  <div class="w-full">
    <!-- Loading State -->
    <div v-if="isLoading" class="w-full max-w-7xl mx-auto px-4 py-12">
      <div class="flex items-center justify-center">
        <div
          class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"
        ></div>
      </div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="w-full max-w-7xl mx-auto px-4 py-12">
      <div
        class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg"
      >
        {{ error }}
      </div>
    </div>

    <!-- Outlet Details -->
    <div v-else-if="outlet" class="w-full">
      <!-- Notification Messages -->
      <div
        v-if="errorMessage || successMessage"
        class="w-full max-w-7xl mx-auto px-4 pt-4"
      >
        <div
          v-if="errorMessage"
          class="mb-4 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg"
        >
          {{ errorMessage }}
        </div>
        <div
          v-if="successMessage"
          class="mb-4 bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg"
        >
          {{ successMessage }}
        </div>
      </div>

      <!-- Hero Image Gallery -->
      <div class="relative w-full h-[400px] bg-gray-200 dark:bg-gray-800">
        <ImageDisplay
          :image-url="getOutletImageUrl(outlet)"
          :alt="outlet.name"
          :lazy="false"
          placeholder-icon="restaurant"
          :icon-size="'96px'"
          container-class="w-full h-full"
          image-class="w-full h-full object-cover"
        />

        <!-- Image Gallery Thumbnails -->
        <div
          v-if="outlet.images && outlet.images.length > 1"
          class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex gap-2 z-10"
        >
          <button
            v-for="(img, index) in outlet.images.slice(0, 5)"
            :key="index"
            class="w-16 h-16 rounded-lg overflow-hidden border-2 border-white shadow-lg hover:scale-110 transition-transform bg-white"
          >
            <ImageDisplay
              :image-url="img"
              :alt="`Image ${index + 1}`"
              :lazy="true"
              placeholder-icon="image"
              :icon-size="'24px'"
              container-class="w-full h-full"
              image-class="w-full h-full object-cover"
            />
          </button>
        </div>
      </div>

      <!-- Main Content -->
      <div class="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <!-- Left Column - Main Info -->
          <div class="lg:col-span-2 space-y-6">
            <!-- Header -->
            <div>
              <div class="flex items-start justify-between mb-2">
                <h1
                  class="text-3xl font-bold text-text-light dark:text-text-dark"
                >
                  {{ outlet.name }}
                </h1>
                <button
                  class="p-2 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-full transition-colors"
                >
                  <span class="material-symbols-outlined text-2xl text-red-500"
                    >favorite_border</span
                  >
                </button>
              </div>

              <div
                class="flex flex-wrap items-center gap-4 text-sm text-subtext-light dark:text-subtext-dark"
              >
                <!-- Rating + Aspect summary -->
                <div class="flex items-center gap-4">
                  <div class="flex items-center gap-2">
                    <span
                      class="material-symbols-outlined text-yellow-500 text-xl"
                      >star</span
                    >
                    <div>
                      <div class="font-medium text-lg">{{ ratingDisplay }}</div>
                      <div
                        class="text-xs text-subtext-light dark:text-subtext-dark"
                      >
                        <template v-if="numReviews > 0"
                          >{{ numReviews }} đánh giá</template
                        >
                        <template v-else>Chưa có đánh giá</template>
                      </div>
                    </div>
                  </div>

                  <div
                    v-if="numReviews > 0"
                    class="flex items-center gap-2 text-sm"
                  >
                    <span
                      v-for="(val, label) in aspectAverages"
                      :key="label"
                      class="px-2 py-1 bg-gray-100 dark:bg-gray-800 rounded text-xs"
                    >
                      <strong>{{ label }}:</strong> {{ val }}
                    </span>
                  </div>
                </div>

                <!-- Category -->
                <div v-if="outlet.outletCategory?.name" class="flex items-center gap-1">
                  <span class="material-symbols-outlined">restaurant</span>
                  <span>{{ outlet.outletCategory.name }}</span>
                </div>

                <!-- Price Range -->
                <div v-if="outlet.priceRange" class="flex items-center gap-1">
                  <span class="material-symbols-outlined">payments</span>
                  <span>{{ outlet.priceRange }}</span>
                </div>
              </div>
            </div>

            <!-- Tabs -->
            <div class="border-b border-border-light dark:border-border-dark">
              <nav class="flex gap-6">
                <button
                  v-for="tab in tabs"
                  :key="tab.id"
                  @click="activeTab = tab.id"
                  :class="[
                    'pb-3 px-1 text-sm font-medium border-b-2 transition-colors',
                    activeTab === tab.id
                      ? 'border-primary text-primary'
                      : 'border-transparent text-subtext-light dark:text-subtext-dark hover:text-text-light dark:hover:text-text-dark',
                  ]"
                >
                  {{ tab.label }}
                </button>
              </nav>
            </div>

            <!-- Tab Content -->
            <div class="py-4">
              <!-- Overview Tab -->
              <div v-if="activeTab === 'overview'" class="space-y-6">
                <!-- Description -->
                <div>
                  <h3 class="text-lg font-semibold mb-3">Giới thiệu</h3>
                  <p
                    class="text-subtext-light dark:text-subtext-dark leading-relaxed"
                  >
                    {{ outlet.description || "Chưa có mô tả" }}
                  </p>
                </div>

                <!-- Address & Contact -->
                <div>
                  <h3 class="text-lg font-semibold mb-3">Thông tin liên hệ</h3>
                  <div class="space-y-3">
                    <div class="flex items-start gap-3">
                      <span class="material-symbols-outlined text-primary"
                        >location_on</span
                      >
                      <div>
                        <p class="font-medium">Địa chỉ</p>
                        <p class="text-subtext-light dark:text-subtext-dark">
                          {{ outlet.address }}, {{ outlet.district?.name }},
                          {{ outlet.province?.name }}
                        </p>

                        <div
                          v-if="outlet.latitude && outlet.longitude"
                          class="mt-1"
                        >
                          <a
                            :href="`https://www.google.com/maps/search/?api=1&query=${outlet.latitude},${outlet.longitude}`"
                            target="_blank"
                            class="text-primary text-sm"
                            >Xem trên bản đồ</a
                          >
                        </div>
                      </div>
                    </div>

                    <div
                      v-if="outlet.phoneNumber"
                      class="flex items-start gap-3"
                    >
                      <span class="material-symbols-outlined text-primary"
                        >call</span
                      >
                      <div>
                        <p class="font-medium">Số điện thoại</p>
                        <p class="text-subtext-light dark:text-subtext-dark">
                          {{ outlet.phoneNumber }}
                        </p>
                      </div>
                    </div>

                    <div v-if="outlet.email" class="flex items-start gap-3">
                      <span class="material-symbols-outlined text-primary"
                        >mail</span
                      >
                      <div>
                        <p class="font-medium">Email</p>
                        <p class="text-subtext-light dark:text-subtext-dark">
                          {{ outlet.email }}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Operating Hours -->
                <div
                  v-if="
                    outlet.operatingHours && outlet.operatingHours.length > 0
                  "
                >
                  <h3 class="text-lg font-semibold mb-3">Giờ mở cửa</h3>
                  <div class="space-y-2">
                    <div
                      v-for="hours in outlet.operatingHours"
                      :key="hours.dayOfWeek"
                      class="flex justify-between items-center py-2 border-b border-border-light dark:border-border-dark last:border-0"
                    >
                      <span class="font-medium">{{
                        getDayName(hours.dayOfWeek)
                      }}</span>
                      <span class="text-subtext-light dark:text-subtext-dark">
                        {{ hours.openTime }} - {{ hours.closeTime }}
                      </span>
                    </div>
                  </div>
                </div>

                <!-- Features -->
                <div v-if="outlet.features && outlet.features.length > 0">
                  <h3 class="text-lg font-semibold mb-3">Tiện ích</h3>
                  <div class="flex flex-wrap gap-2">
                    <span
                      v-for="feature in outlet.features"
                      :key="feature.id"
                      class="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm font-medium"
                    >
                      {{ feature.name }}
                    </span>
                  </div>
                </div>
              </div>

              <!-- Menu Tab -->
              <div v-else-if="activeTab === 'menu'" class="space-y-4">
                <!-- Loading state for menu -->
                <div v-if="isLoadingMenu" class="flex justify-center py-12">
                  <div
                    class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary"
                  ></div>
                </div>

                <!-- Menu items -->
                <div
                  v-else-if="menuItems && menuItems.length > 0"
                  class="grid grid-cols-1 md:grid-cols-2 gap-4"
                >
                  <div
                    v-for="item in menuItems"
                    :key="item.id"
                    class="flex gap-4 p-4 border border-border-light dark:border-border-dark rounded-lg hover:shadow-lg transition-shadow"
                    :class="{
                      'opacity-50':
                        item.isAvailable === false ||
                        item.is_available === false,
                    }"
                  >
                    <div class="w-24 h-24 flex-shrink-0">
                      <ImageDisplay
                        :image-url="item.image || item.imageUrl || item.image_url"
                        :alt="item.name"
                        :lazy="true"
                        placeholder-icon="restaurant_menu"
                        :icon-size="'32px'"
                        container-class="w-full h-full rounded-lg"
                        image-class="w-full h-full object-cover rounded-lg"
                      />
                    </div>
                    <div class="flex-1">
                      <div class="flex items-start justify-between mb-1">
                        <h4 class="font-semibold">{{ item.name }}</h4>
                        <span
                          v-if="
                            item.isAvailable === false ||
                            item.is_available === false
                          "
                          class="text-xs text-red-500 ml-2"
                        >
                          Hết món
                        </span>
                      </div>
                      <p
                        class="text-sm text-subtext-light dark:text-subtext-dark mb-2 line-clamp-2"
                      >
                        {{ item.description || "Chưa có mô tả" }}
                      </p>
                      <p class="text-primary font-bold">
                        {{ formatPrice(item.price) }}
                      </p>
                    </div>
                  </div>
                </div>

                <!-- Empty state -->
                <div
                  v-else
                  class="text-center py-12 text-subtext-light dark:text-subtext-dark"
                >
                  <span
                    class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600 mb-4"
                    >restaurant_menu</span
                  >
                  <p>Chưa có thực đơn</p>
                </div>
              </div>

              <!-- Reviews Tab -->
              <div v-else-if="activeTab === 'reviews'" class="space-y-4">
                <!-- Loading state for reviews -->
                <div v-if="isLoadingReviews" class="flex justify-center py-12">
                  <div
                    class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary"
                  ></div>
                </div>

                <!-- Reviews list -->
                <div
                  v-else-if="reviews && reviews.length > 0"
                  class="space-y-6"
                >
                  <div
                    v-for="review in reviews"
                    :key="review.id"
                    class="p-4 border border-border-light dark:border-border-dark rounded-lg"
                  >
                    <div class="flex items-start gap-4">
                      <div
                        class="w-10 h-10 rounded-full bg-primary/20 flex items-center justify-center"
                      >
                        <span class="material-symbols-outlined text-primary"
                          >person</span
                        >
                      </div>
                      <div class="flex-1">
                        <div class="flex items-center justify-between mb-2">
                          <h4 class="font-semibold">
                            {{
                              review.userFullName ||
                              review.userName ||
                              "Anonymous"
                            }}
                          </h4>
                          <div class="flex items-center gap-2">
                            <div class="flex items-center">
                              <template v-for="i in 5" :key="i">
                                <span
                                  class="material-symbols-outlined text-sm"
                                  :class="i <= (review.rating ?? review.overallRating ?? 0) ? 'text-yellow-500' : 'text-gray-300'">
                                  {{ i <= (review.rating ?? review.overallRating ?? 0) ? 'star' : 'star_border' }}
                                </span>
                              </template>
                            </div>
                            <span class="text-sm font-medium ml-2">{{ review.rating ?? review.overallRating ?? 0 }}/5</span>
                          </div>
                        </div>
                        <p
                          class="text-subtext-light dark:text-subtext-dark mb-2"
                        >
                          {{ review.comment || review.reviewText }}
                        </p>
                        <p
                          class="text-xs text-subtext-light dark:text-subtext-dark mb-3"
                        >
                          {{ formatDate(review.createdAt) }}
                        </p>
                        
                        <!-- Owner Reply -->
                        <div
                          v-if="review.reply"
                          class="mt-3 p-3 bg-primary/5 dark:bg-primary/10 rounded-lg border border-primary/20"
                        >
                          <div class="flex items-center gap-2 mb-2">
                            <span class="material-symbols-outlined text-primary text-sm">reply</span>
                            <div class="text-sm font-medium text-text-light dark:text-text-dark">
                              Phản hồi từ chủ quán
                            </div>
                          </div>
                          <p class="text-sm text-subtext-light dark:text-subtext-dark whitespace-pre-wrap mb-2">
                            {{ review.reply.replyText }}
                          </p>
                          <div class="flex items-center justify-between">
                            <p class="text-xs text-subtext-light dark:text-subtext-dark">
                              - {{ review.reply.ownerName }}
                            </p>
                            <p class="text-xs text-subtext-light dark:text-subtext-dark">
                              {{ formatDate(review.reply.createdAt) }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Empty state -->
                <div
                  v-else
                  class="text-center py-12 text-subtext-light dark:text-subtext-dark"
                >
                  <span
                    class="material-symbols-outlined text-6xl text-gray-300 dark:text-gray-600 mb-4"
                    >rate_review</span
                  >
                  <p>Chưa có đánh giá</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column - Booking Card -->
          <div class="lg:col-span-1">
            <div
              class="top-20 bg-white dark:bg-surface-dark border border-border-light dark:border-border-dark rounded-xl p-6 shadow-lg"
            >
              <div class="mb-6">
                <div class="text-3xl font-bold text-primary mb-2">
                  {{ displayPrice }}
                </div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark">
                  Giá trung bình / người
                </p>
              </div>

              <div class="flex gap-3 mb-4">
                <button
                  @click="handleBookingClick"
                  :disabled="isBookingDisabled || isLoading"
                  :title="
                    isBookingDisabled
                      ? 'Bạn không thể đặt bàn tại chính quán của mình'
                      : !authStore.isAuthenticated
                      ? 'Vui lòng đăng nhập để đặt bàn'
                      : !authStore.user?.membershipIsActive
                      ? 'Bạn cần đăng ký gói membership để đặt bàn'
                      : ''
                  "
                  :class="[
                    'flex-1 text-center font-bold py-3 rounded-lg transition-colors',
                    !authStore.isAuthenticated
                      ? 'bg-gray-400 text-white cursor-pointer hover:bg-gray-500'
                      : isBookingDisabled || isLoading
                      ? 'bg-primary text-white opacity-50 cursor-not-allowed'
                      : 'bg-primary text-white hover:bg-opacity-90'
                  ]"
                >
                  <span v-if="!authStore.isAuthenticated">Đăng nhập để đặt bàn</span>
                  <span v-else-if="!authStore.user?.membershipIsActive">Cần gói membership</span>
                  <span v-else>Đặt bàn ngay</span>
                </button>

                <button
                  type="button"
                  @click="activeTab = 'menu'"
                  class="px-4 py-3 border border-border-light dark:border-border-dark rounded-lg text-sm hover:bg-gray-100 dark:hover:bg-gray-800"
                >
                  Xem thực đơn
                </button>
              </div>

              <div
                class="space-y-3 pt-4 border-t border-border-light dark:border-border-dark"
              >
                <div class="flex items-center gap-2 text-sm">
                  <span class="material-symbols-outlined text-primary"
                    >schedule</span
                  >
                  <span>Đặt bàn trực tuyến</span>
                </div>
                <div class="flex items-center gap-2 text-sm">
                  <span class="material-symbols-outlined text-primary"
                    >verified</span
                  >
                  <span>Xác nhận ngay lập tức</span>
                </div>
                <div class="flex items-center gap-2 text-sm">
                  <span class="material-symbols-outlined text-primary"
                    >cancel</span
                  >
                  <span>Hủy miễn phí</span>
                </div>
              </div>

              <!-- Share -->
              <div
                class="mt-6 pt-6 border-t border-border-light dark:border-border-dark"
              >
                <p class="text-sm font-medium mb-3">Chia sẻ</p>
                <div class="flex gap-2">
                  <button
                    class="p-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                  >
                    <span class="material-symbols-outlined">share</span>
                  </button>
                  <button
                    class="p-2 border border-border-light dark:border-border-dark rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
                  >
                    <span class="material-symbols-outlined">link</span>
                  </button>
                </div>
              </div>
            </div>

            <!-- Review Form Section (moved to Bookings List) -->
            <!-- Removed: review per outlet; reviews are now per-booking in Lịch sử đặt bàn -->
            <div style="display: none">
              <div class="flex items-center justify-between mb-4">
                <h3
                  class="text-lg font-bold text-text-light dark:text-text-dark"
                >
                  Đánh giá nhà hàng
                </h3>
                <span
                  class="text-xs px-2 py-1 rounded-full"
                  :class="
                    completedBookingsCount > 0
                      ? 'bg-green-100 text-green-700'
                      : 'bg-gray-100 text-gray-600'
                  "
                >
                  {{ completedBookingsCount }} lượt đặt bàn hoàn thành
                </span>
              </div>

              <p
                v-if="completedBookingsCount === 0"
                class="text-sm text-red-500 mb-4"
              >
                Bạn cần có ít nhất 1 đơn đặt bàn hoàn thành để đánh giá
              </p>

              <!-- Per-aspect Rating Stars -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                <!-- Food -->
                <div>
                  <label class="block text-sm font-medium mb-2"
                    >Đồ ăn <span class="text-red-500">*</span></label
                  >
                  <div class="flex gap-2">
                    <button
                      v-for="star in 5"
                      :key="`food-${star}`"
                      @click="reviewForm.foodRating = star"
                      type="button"
                      class="text-3xl transition-colors cursor-pointer hover:scale-110"
                      :class="star <= reviewForm.foodRating ? 'text-yellow-500' : 'text-gray-300 dark:text-gray-600'"
                    >
                      <span class="material-symbols-outlined">{{
                        star <= reviewForm.foodRating ? "star" : "star_border"
                      }}</span>
                    </button>
                  </div>
                </div>

                <!-- Service -->
                <div>
                  <label class="block text-sm font-medium mb-2"
                    >Phục vụ <span class="text-red-500">*</span></label
                  >
                  <div class="flex gap-2">
                    <button
                      v-for="star in 5"
                      :key="`service-${star}`"
                      @click="reviewForm.serviceRating = star"
                      type="button"
                      class="text-3xl transition-colors cursor-pointer hover:scale-110"
                      :class="star <= reviewForm.serviceRating ? 'text-yellow-500' : 'text-gray-300 dark:text-gray-600'"
                    >
                      <span class="material-symbols-outlined">{{
                        star <= reviewForm.serviceRating
                          ? "star"
                          : "star_border"
                      }}</span>
                    </button>
                  </div>
                </div>

                <!-- Ambiance -->
                <div>
                  <label class="block text-sm font-medium mb-2"
                    >Không gian <span class="text-red-500">*</span></label
                  >
                  <div class="flex gap-2">
                    <button
                      v-for="star in 5"
                      :key="`amb-${star}`"
                      @click="reviewForm.ambianceRating = star"
                      type="button"
                      class="text-3xl transition-colors cursor-pointer hover:scale-110"
                      :class="star <= reviewForm.ambianceRating ? 'text-yellow-500' : 'text-gray-300 dark:text-gray-600'"
                    >
                      <span class="material-symbols-outlined">{{
                        star <= reviewForm.ambianceRating
                          ? "star"
                          : "star_border"
                      }}</span>
                    </button>
                  </div>
                </div>

                <!-- Price -->
                <div>
                  <label class="block text-sm font-medium mb-2"
                    >Giá cả <span class="text-red-500">*</span></label
                  >
                  <div class="flex gap-2">
                    <button
                      v-for="star in 5"
                      :key="`price-${star}`"
                      @click="reviewForm.priceRating = star"
                      type="button"
                      class="text-3xl transition-colors cursor-pointer hover:scale-110"
                      :class="star <= reviewForm.priceRating ? 'text-yellow-500' : 'text-gray-300 dark:text-gray-600'"
                    >
                      <span class="material-symbols-outlined">{{
                        star <= reviewForm.priceRating ? "star" : "star_border"
                      }}</span>
                    </button>
                  </div>
                </div>
              </div>

              <!-- Review Text -->
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">
                  Đánh giá của bạn <span class="text-red-500">*</span>
                </label>
                <textarea
                  v-model="reviewForm.comment"
                  :disabled="completedBookingsCount === 0"
                  rows="4"
                  placeholder="Chia sẻ trải nghiệm của bạn về nhà hàng..."
                  class="w-full px-4 py-3 rounded-lg border border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark focus:ring-2 focus:ring-primary/50 focus:border-primary resize-none disabled:cursor-not-allowed disabled:opacity-50"
                ></textarea>
              </div>

              <!-- Error Message -->
              <div
                v-if="reviewError"
                class="mb-4 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg text-sm"
              >
                {{ reviewError }}
              </div>

              <!-- Success Message -->
              <div
                v-if="reviewSuccess"
                class="mb-4 bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg text-sm"
              >
                {{ reviewSuccess }}
              </div>

              <!-- Submit Button -->
              <button
                @click="submitReview"
                :disabled="
                  !canReview ||
                  isSubmittingReview ||
                  !reviewForm.foodRating ||
                  !reviewForm.serviceRating ||
                  !reviewForm.ambianceRating ||
                  !reviewForm.priceRating ||
                  !reviewForm.comment.trim()
                "
                class="w-full px-4 py-3 bg-primary text-white rounded-lg font-bold hover:bg-opacity-90 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {{ isSubmittingReview ? "Đang gửi..." : "Gửi đánh giá" }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {outletApi} from "@/api";
import {menuApi} from "@/api/menu";
import {reviewApi} from "@/api/review";
import {bookingApi} from "@/api/booking";
import {useAuthStore} from "@/stores/auth";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

// State
const outlet = ref(null);
const menuItems = ref([]);
const reviews = ref([]);
const isLoading = ref(false);
const isLoadingMenu = ref(false);
const isLoadingReviews = ref(false);
const error = ref(null);
const errorMessage = ref("");
const successMessage = ref("");
const activeTab = ref("overview");

// Review state
const completedBookingsCount = ref(0);
const eligibleBookingId = ref(null); // booking id used to create review
const canReview = ref(false);
const reviewForm = ref({
  foodRating: 0,
  serviceRating: 0,
  ambianceRating: 0,
  priceRating: 0,
  comment: "",
});
const isSubmittingReview = ref(false);
const reviewError = ref("");
const reviewSuccess = ref("");

// Tabs configuration
const tabs = [
  {id: "overview", label: "Tổng quan"},
  {id: "menu", label: "Thực đơn"},
  {id: "reviews", label: "Đánh giá"},
];

// Fetch outlet details
const fetchOutletDetail = async () => {
  isLoading.value = true;
  error.value = null;

  try {
    console.log("🔍 Fetching outlet detail for ID:", route.params.id);
    const data = await outletApi.getOutletDetail(route.params.id);
    console.log("✅ Outlet data received:", data);
    console.log(
      "📊 Full outlet object keys:",
      data ? Object.keys(data) : "null"
    );
    outlet.value = data;

    // Check if menu items or reviews are already included in outlet response
    if (data?.menuItems && Array.isArray(data.menuItems)) {
      console.log("📋 Menu items found in outlet data:", data.menuItems.length);
      console.log("📋 First menu item:", data.menuItems[0]);
      menuItems.value = data.menuItems;
    } else {
      console.log(
        "⚠️ No menuItems in outlet data. Available fields:",
        data ? Object.keys(data) : "null"
      );
    }

    if (data?.reviews && Array.isArray(data.reviews)) {
      console.log("⭐ Reviews found in outlet data:", data.reviews.length);
      console.log("⭐ First review:", data.reviews[0]);
      reviews.value = data.reviews;
    } else {
      console.log(
        "⚠️ No reviews in outlet data. Available fields:",
        data ? Object.keys(data) : "null"
      );
      console.log("🔍 Checking alternative field names...");
      // Check for alternative field names
      if (data?.reviewList) {
        console.log("✅ Found reviewList instead!", data.reviewList.length);
        reviews.value = data.reviewList;
      }
    }

    console.log(
      "📊 Final state - menuItems count:",
      menuItems.value.length,
      "reviews count:",
      reviews.value.length
    );

    // Re-evaluate review eligibility now that outlet is loaded
    try {
    } catch (err) {
      console.error(
        "❌ Error while checking review eligibility after outlet load:",
        err
      );
    }
  } catch (err) {
    console.error("❌ Error fetching outlet:", err);
    error.value = err.message || "Không thể tải thông tin địa điểm";
  } finally {
    isLoading.value = false;
  }
};

// Fetch menu items
const fetchMenuItems = async () => {
  if (menuItems.value.length > 0) return; // Already loaded

  // Check if already in outlet data
  if (outlet.value?.menuItems && Array.isArray(outlet.value.menuItems)) {
    console.log("✅ Using menu items from outlet data");
    menuItems.value = outlet.value.menuItems;
    return;
  }

  isLoadingMenu.value = true;
  try {
    console.log("🍽️ Fetching menu items for outlet:", route.params.id);
    const data = await menuApi.getOutletMenuItems(route.params.id, {
      page: 0,
      size: 20,
    });
    console.log("✅ Menu items API response:", data);
    console.log("📊 Response type:", typeof data);
    console.log("📊 Is array?", Array.isArray(data));
    console.log("📊 Has data property?", data?.data);
    console.log("📊 Response keys:", data ? Object.keys(data) : null);

    // Check if response is PageResponse
    const items = data?.data || data || [];
    console.log("📦 Extracted items:", items);
    console.log(
      "📦 Items count:",
      Array.isArray(items) ? items.length : "not array"
    );

    if (items.length > 0) {
      console.log("🔍 First menu item structure:", items[0]);
      console.log("🔍 First menu item keys:", Object.keys(items[0]));
    }

    menuItems.value = items;
  } catch (err) {
    console.warn("⚠️ Could not fetch menu items:", err.message);
    console.error("⚠️ Full error:", err);
    console.log("💡 Menu endpoint may not be available yet or returning 500");
    // Don't throw, just leave empty
    menuItems.value = [];
  } finally {
    isLoadingMenu.value = false;
    console.log("🏁 Final menuItems.value:", menuItems.value);
  }
};

// Fetch reviews
const fetchReviews = async () => {
  if (reviews.value.length > 0) return; // Already loaded

  // Check if already in outlet data
  if (outlet.value?.reviews && Array.isArray(outlet.value.reviews)) {
    console.log("✅ Using reviews from outlet data");
    reviews.value = outlet.value.reviews;
    return;
  }

  isLoadingReviews.value = true;
  try {
    console.log("⭐ Fetching reviews for outlet:", route.params.id);
    const data = await reviewApi.getOutletReviews(route.params.id, {
      page: 0,
      size: 10,
    });
    console.log("✅ Reviews received:", data);

    // Check if response is PageResponse
    reviews.value = data?.data || data || [];
  } catch (err) {
    console.warn("⚠️ Could not fetch reviews:", err.message);
    console.log("💡 Reviews endpoint may not be available yet");
    // Don't throw, just leave empty
    reviews.value = [];
  } finally {
    isLoadingReviews.value = false;
  }
};

// Watch tab changes to load data
watch(activeTab, (newTab) => {
  console.log("📑 Tab changed to:", newTab);

  // Re-enabled API calls with graceful error handling
  // Will show empty state if backend endpoints return 500
  if (newTab === "menu" && menuItems.value.length === 0) {
    fetchMenuItems();
  } else if (newTab === "reviews" && reviews.value.length === 0) {
    fetchReviews();
  }
});

// Helper functions
const getDayName = (dayOfWeek) => {
  const days = {
    MONDAY: "Thứ Hai",
    TUESDAY: "Thứ Ba",
    WEDNESDAY: "Thứ Tư",
    THURSDAY: "Thứ Năm",
    FRIDAY: "Thứ Sáu",
    SATURDAY: "Thứ Bảy",
    SUNDAY: "Chủ Nhật",
  };
  return days[dayOfWeek] || dayOfWeek;
};

const formatPrice = (price) => {
  if (!price || price === 0) return "Liên hệ";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(price);
};

const formatDate = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("vi-VN", {
    year: "numeric",
    month: "long",
    day: "numeric",
  }).format(date);
};

// Number of reviews (prefer outlet.totalReviews if backend provides it)
const numReviews = computed(() => {
  return (
    outlet.value?.totalReviews ??
    (Array.isArray(reviews.value) ? reviews.value.length : 0)
  );
});

// Compute aspect averages from reviews as fallback when backend doesn't provide per-aspect aggregates
const aspectAverages = computed(() => {
  if (!reviews.value || reviews.value.length === 0) return {};

  const sum = {food: 0, service: 0, ambiance: 0, price: 0, overall: 0};
  let count = 0;
  reviews.value.forEach((r) => {
    // support different field names in review payload
    const food = Number(r.foodRating ?? r.food_rating ?? 0);
    const service = Number(r.serviceRating ?? r.service_rating ?? 0);
    const ambiance = Number(r.ambianceRating ?? r.ambiance_rating ?? 0);
    const price = Number(r.priceRating ?? r.price_rating ?? 0);
    const overall = Number(
      r.overallRating ?? r.overall_rating ?? r.rating ?? 0
    );

    sum.food += food;
    sum.service += service;
    sum.ambiance += ambiance;
    sum.price += price;
    sum.overall += overall;
    count++;
  });

  const toStr = (n) => (count ? (n / count).toFixed(1) : "0.0");

  return {
    Food: toStr(sum.food),
    Service: toStr(sum.service),
    Ambiance: toStr(sum.ambiance),
    Price: toStr(sum.price),
    Overall: toStr(sum.overall),
  };
});

// Disable booking button when the current user is the owner of this outlet
const isBookingDisabled = computed(() => {
  return authStore.isOwner && outlet.value?.owner?.id === authStore.user?.id;
});

// Computed helpers for rating and price
const ratingDisplay = computed(() => {
  const r = outlet.value?.averageRating;
  if (r === undefined || r === null) return "0.0";
  // Ensure numeric and show one decimal
  const num = Number(r);
  if (Number.isNaN(num)) return "0.0";
  return num.toFixed(1);
});

const displayPrice = computed(() => {
  // Prefer priceRange (string like "₫ - ₫") if provided by backend
  if (outlet.value?.priceRange) return outlet.value.priceRange;

  // If backend provides averagePrice, format it
  if (outlet.value?.averagePrice) return formatPrice(outlet.value.averagePrice);

  // Fallback: compute average from menu items
  const prices = (menuItems.value || [])
    .map((i) => Number(i?.price || i?.priceAmount || 0))
    .filter((p) => p > 0);
  if (prices.length > 0) {
    const avg = prices.reduce((s, v) => s + v, 0) / prices.length;
    return formatPrice(avg);
  }

  return "Liên hệ";
});

// Handle booking click with membership check
const handleBookingClick = () => {
  errorMessage.value = "";
  successMessage.value = "";

  // Check authentication - redirect to login if not authenticated
  if (!authStore.isAuthenticated) {
    errorMessage.value = "Vui lòng đăng nhập để đặt bàn. Đang chuyển hướng...";
    setTimeout(() => {
      router.push("/auth/login");
    }, 1500);
    return;
  }

  // Check membership requirement: require active membership (package), regardless of role
  if (!authStore.user?.membershipIsActive) {
    errorMessage.value =
      "Bạn cần đăng ký gói membership để đặt bàn. Đang chuyển hướng...";
    setTimeout(() => {
      router.push("/membership");
    }, 1500);
    return;
  }

  // Owners cannot book at their own outlet
  if (authStore.isOwner && outlet.value?.owner?.id === authStore.user?.id) {
    errorMessage.value =
      "Bạn không thể đặt bàn tại chính quán của mình. Vui lòng chọn quán khác.";
    return;
  }

  // Navigate to booking form
  router.push(`/booking/${outlet.value.id}`);
};

// (Removed) Check completed bookings logic moved to BookingHistory.vue
// function checkCanReview removed - reviews are managed per-booking now
/*

  try {
    // Fetch user's bookings and count COMPLETED ones for this outlet
    const response = await bookingApi.getMyBookings({
      page: 0,
      size: 100, // Get enough to check
    });

    // Normalize bookings array: support PageResponse (content/data) or raw array
    let bookingsList = [];
    if (Array.isArray(response?.content)) bookingsList = response.content;
    else if (Array.isArray(response?.data)) bookingsList = response.data;
    else if (Array.isArray(response)) bookingsList = response;

    console.log("📋 User bookings (normalized):", bookingsList);
    console.log("🏠 Current outlet ID:", outlet.value.id);

    // Convert both IDs to lowercase string for comparison
    const currentOutletId = String(outlet.value.id).toLowerCase();

    // Find reviewable bookings for this outlet
    const reviewableBookings = bookingsList.filter((booking) => {
      const bookingOutletId = String(
        booking.outlet?.id || booking.outletId || ""
      ).toLowerCase();
      const status = String(booking.status || "").toUpperCase();
      const isCompleted = status === "COMPLETED";
      const bothCheckedIn =
        Boolean(booking.userCheckedInAt) && Boolean(booking.ownerCheckedInAt);
      const isMatch = bookingOutletId === currentOutletId;

      const isReviewable = isMatch && (isCompleted || bothCheckedIn);

      console.log("🔍 Checking booking:", {
        bookingOutletId,
        currentOutletId,
        status: booking.status,
        isCompleted,
        bothCheckedIn,
        isMatch,
        isReviewable,
      });

      return isReviewable;
    });

    // Count
    completedBookingsCount.value = reviewableBookings.length;

    // Choose an eligible booking id (most recent by date/time) for the review payload
    if (reviewableBookings.length > 0) {
      reviewableBookings.sort((a, b) => {
        const atime = new Date(
          (a.bookingDate || "") + "T" + (a.bookingTime || "00:00:00")
        );
        const btime = new Date(
          (b.bookingDate || "") + "T" + (b.bookingTime || "00:00:00")
        );
        return btime - atime; // newest first
      });
      eligibleBookingId.value =
        reviewableBookings[0].id || reviewableBookings[0].bookingId;
      canReview.value = true;
    } else {
      eligibleBookingId.value = null;
      canReview.value = false;
    }

    console.log(
      "✅ Completed/reviewable bookings count:",
      completedBookingsCount.value,
      "eligibleBookingId:",
      eligibleBookingId.value
    );
  } catch (err) {
    console.error("❌ Error checking review eligibility:", err);
    completedBookingsCount.value = 0;
  }
*/

// Open review dialog
const openReviewDialog = () => {
  if (!authStore.isAuthenticated) {
    errorMessage.value = "Vui lòng đăng nhập để đánh giá";
    setTimeout(() => {
      router.push("/auth/login");
    }, 2000);
    return;
  }

  reviewForm.value = {
    foodRating: 0,
    serviceRating: 0,
    ambianceRating: 0,
    priceRating: 0,
    comment: "",
  };
  reviewError.value = "";
  reviewSuccess.value = "";
  showReviewDialog.value = true;
};

// Close review dialog
const closeReviewDialog = () => {
  showReviewDialog.value = false;
  reviewForm.value = {
    foodRating: 0,
    serviceRating: 0,
    ambianceRating: 0,
    priceRating: 0,
    comment: "",
  };
  reviewError.value = "";
  reviewSuccess.value = "";
};

// Submit review
const submitReview = async () => {
  // Ensure all per-aspect ratings are provided
  if (
    !reviewForm.value.foodRating ||
    !reviewForm.value.serviceRating ||
    !reviewForm.value.ambianceRating ||
    !reviewForm.value.priceRating ||
    !reviewForm.value.comment.trim()
  ) {
    reviewError.value = "Vui lòng nhập đầy đủ thông tin";
    return;
  }

  isSubmittingReview.value = true;
  reviewError.value = "";
  reviewSuccess.value = "";

  try {
    if (!eligibleBookingId.value) {
      reviewError.value = "Không tìm thấy đơn hợp lệ để đánh giá.";
      return;
    }

    const reviewData = {
      bookingId: eligibleBookingId.value,
      foodRating: reviewForm.value.foodRating,
      serviceRating: reviewForm.value.serviceRating,
      ambianceRating: reviewForm.value.ambianceRating,
      priceRating: reviewForm.value.priceRating,
      comment: reviewForm.value.comment.trim(),
    };

    // Debug: log payload
    console.log("🧾 Review payload:", reviewData);

    console.log("📝 Submitting review:", reviewData);
    await reviewApi.createReview(reviewData);
    console.log("✅ Review submitted successfully");

    reviewSuccess.value = "Đánh giá của bạn đã được gửi thành công!";

    // Refresh reviews after 1.5 seconds
    setTimeout(() => {
      closeReviewDialog();
      fetchReviews();
      // Can't review again after submitting
      canReview.value = false;
    }, 1500);
  } catch (err) {
    console.error("❌ Error submitting review:", err);
    reviewError.value = err.message || "Đánh giá thất bại. Vui lòng thử lại.";
  } finally {
    isSubmittingReview.value = false;
  }
};

// Helper function to get outlet image URL
const getOutletImageUrl = (outlet) => {
  // Backend now returns images as List<String>
  if (!outlet?.images || !Array.isArray(outlet.images) || outlet.images.length === 0) {
    return null;
  }
  // images is now a simple array of strings
  return outlet.images[0] || null;
};

// Lifecycle
onMounted(() => {
  fetchOutletDetail();
});
</script>
