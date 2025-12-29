/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        primary: "#f56e3d",
        "background-light": "#f8f6f5",
        "background-dark": "#221510",
        "surface-light": "#ffffff",
        "surface-dark": "#2a1a14",
        "text-light": "#1c110d",
        "text-dark": "#f8f6f5",
        "subtext-light": "#9c5f49",
        "subtext-dark": "#a88e86",
        "border-light": "#e8d5ce",
        "border-dark": "#4a352d",
        positive: "#07880b",
        "positive-dark": "#50c878",
        "owner-primary": "#2A9D8F",
        "owner-chart": "#F4A261",
      },
      fontFamily: {
        display: ["Be Vietnam Pro", "sans-serif"],
        manrope: ["Manrope", "sans-serif"],
        inter: ["Inter", "sans-serif"],
      },
      borderRadius: {
        DEFAULT: "0.875rem", // 14px - iOS style
        sm: "0.625rem", // 10px
        md: "0.875rem", // 14px
        lg: "1.25rem", // 20px - iOS card style
        xl: "1.5rem", // 24px
        "2xl": "2rem", // 32px
        full: "9999px",
      },
      boxShadow: {
        "ios": "0 2px 8px rgba(0, 0, 0, 0.04), 0 1px 2px rgba(0, 0, 0, 0.06)",
        "ios-lg": "0 8px 24px rgba(0, 0, 0, 0.08), 0 2px 4px rgba(0, 0, 0, 0.08)",
        "ios-xl": "0 12px 32px rgba(0, 0, 0, 0.1), 0 4px 8px rgba(0, 0, 0, 0.1)",
      },
    },
  },
  plugins: [require("@tailwindcss/forms")],
};
