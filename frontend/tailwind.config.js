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
        DEFAULT: "0.5rem",
        lg: "1rem",
        xl: "1.5rem",
        full: "9999px",
      },
    },
  },
  plugins: [require("@tailwindcss/forms")],
};
