/**
 * Utility functions for handling image URLs
 */

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1";
const IMAGE_BASE_URL = import.meta.env.VITE_IMAGE_BASE_URL || API_BASE_URL.replace("/api/v1", "");

/**
 * Process image URL - handles relative and absolute URLs
 * @param {string} imageUrl - The image URL from backend
 * @returns {string} - Processed image URL
 */
export function processImageUrl(imageUrl) {
  if (!imageUrl) return null;

  // If already a full URL, return as is
  if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
    return imageUrl;
  }

  // If starts with /, it's an absolute path from server root
  if (imageUrl.startsWith("/")) {
    const base = IMAGE_BASE_URL.endsWith("/") ? IMAGE_BASE_URL.slice(0, -1) : IMAGE_BASE_URL;
    return `${base}${imageUrl}`;
  }

  // Otherwise, it's a relative path
  const base = IMAGE_BASE_URL.endsWith("/") ? IMAGE_BASE_URL : `${IMAGE_BASE_URL}/`;
  return `${base}${imageUrl}`;
}

/**
 * Get placeholder image URL
 * @param {string} type - Type of placeholder (user, outlet, etc.)
 * @returns {string} - Placeholder URL or null
 */
export function getPlaceholderImage(type = "default") {
  const placeholders = {
    user: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='100'%3E%3Crect fill='%23ddd' width='100' height='100'/%3E%3Ctext fill='%23999' font-family='sans-serif' font-size='50' dy='.3em' x='50%25' y='50%25' text-anchor='middle'%3E👤%3C/text%3E%3C/svg%3E",
    outlet: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='100'%3E%3Crect fill='%23ddd' width='100' height='100'/%3E%3Ctext fill='%23999' font-family='sans-serif' font-size='50' dy='.3em' x='50%25' y='50%25' text-anchor='middle'%3E🏪%3C/text%3E%3C/svg%3E",
    default: "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='100'%3E%3Crect fill='%23ddd' width='100' height='100'/%3E%3Ctext fill='%23999' font-family='sans-serif' font-size='50' dy='.3em' x='50%25' y='50%25' text-anchor='middle'%3E📷%3C/text%3E%3C/svg%3E",
  };

  return placeholders[type] || placeholders.default;
}

/**
 * Check if image URL is valid
 * @param {string} url - Image URL to check
 * @returns {boolean} - True if URL is valid
 */
export function isValidImageUrl(url) {
  if (!url) return false;
  return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("data:") || url.startsWith("/");
}

