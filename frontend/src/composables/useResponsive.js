import {ref, onMounted, onUnmounted} from "vue";

/**
 * Composable for responsive utilities
 */
export function useResponsive() {
  const width = ref(window.innerWidth);
  const height = ref(window.innerHeight);
  const isMobile = ref(window.innerWidth < 640);
  const isTablet = ref(window.innerWidth >= 640 && window.innerWidth < 1024);
  const isDesktop = ref(window.innerWidth >= 1024);

  const updateSize = () => {
    width.value = window.innerWidth;
    height.value = window.innerHeight;
    isMobile.value = window.innerWidth < 640;
    isTablet.value = window.innerWidth >= 640 && window.innerWidth < 1024;
    isDesktop.value = window.innerWidth >= 1024;
  };

  onMounted(() => {
    window.addEventListener("resize", updateSize);
    updateSize();
  });

  onUnmounted(() => {
    window.removeEventListener("resize", updateSize);
  });

  return {
    width,
    height,
    isMobile,
    isTablet,
    isDesktop,
  };
}


