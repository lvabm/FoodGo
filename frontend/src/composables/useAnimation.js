import {ref, onMounted} from "vue";

/**
 * Composable for animation utilities
 */
export function useAnimation() {
  const isVisible = ref(false);

  const fadeIn = (element, delay = 0) => {
    setTimeout(() => {
      element.style.opacity = "0";
      element.style.transform = "translateY(20px)";
      element.style.transition = "opacity 0.6s ease-out, transform 0.6s ease-out";
      
      requestAnimationFrame(() => {
        element.style.opacity = "1";
        element.style.transform = "translateY(0)";
      });
    }, delay);
  };

  const slideIn = (element, direction = "left", delay = 0) => {
    const directions = {
      left: "translateX(-100%)",
      right: "translateX(100%)",
      up: "translateY(100%)",
      down: "translateY(-100%)",
    };

    setTimeout(() => {
      element.style.opacity = "0";
      element.style.transform = directions[direction];
      element.style.transition = "opacity 0.5s ease-out, transform 0.5s ease-out";
      
      requestAnimationFrame(() => {
        element.style.opacity = "1";
        element.style.transform = "translate(0, 0)";
      });
    }, delay);
  };

  const staggerChildren = (container, delay = 100) => {
    const children = container.children;
    Array.from(children).forEach((child, index) => {
      fadeIn(child, index * delay);
    });
  };

  const useIntersectionObserver = (element, callback, options = {}) => {
    const defaultOptions = {
      threshold: 0.1,
      rootMargin: "0px",
      ...options,
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          callback(entry.target);
          observer.unobserve(entry.target);
        }
      });
    }, defaultOptions);

    if (element.value) {
      observer.observe(element.value);
    }

    return () => {
      if (element.value) {
        observer.unobserve(element.value);
      }
    };
  };

  return {
    isVisible,
    fadeIn,
    slideIn,
    staggerChildren,
    useIntersectionObserver,
  };
}


