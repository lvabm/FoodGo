import * as functions from "firebase-functions";
import * as express from "express";
import * as cors from "cors";
import axios, { AxiosRequestConfig } from "axios";

const app = express();

// CORS configuration - Allow all origins from Firebase Hosting
app.use(cors({ 
  origin: true,
  credentials: true 
}));

// Parse JSON bodies
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Backend URL from environment variable
// Set via: firebase functions:config:set backend.url="https://your-backend-url"
const BACKEND_URL = functions.config().backend?.url || 
  process.env.BACKEND_URL || 
  "https://your-backend-url.up.railway.app";

console.log("Backend URL:", BACKEND_URL);

// Health check endpoint
app.get("/health", (req, res) => {
  res.json({ 
    status: "ok", 
    service: "firebase-functions-api-gateway",
    backend: BACKEND_URL 
  });
});

// Proxy all requests to backend
app.use("*", async (req, res) => {
  try {
    const targetUrl = `${BACKEND_URL}${req.originalUrl}`;
    
    console.log(`Proxying ${req.method} ${req.originalUrl} -> ${targetUrl}`);

    // Prepare axios config
    const axiosConfig: AxiosRequestConfig = {
      method: req.method as any,
      url: targetUrl,
      data: req.body,
      headers: {
        ...req.headers,
        host: undefined, // Remove host header
        "x-forwarded-for": req.ip,
        "x-forwarded-proto": req.protocol,
      },
      params: req.query,
      timeout: 30000, // 30 seconds timeout
    };

    // Forward Authorization header if present
    if (req.headers.authorization) {
      axiosConfig.headers = {
        ...axiosConfig.headers,
        authorization: req.headers.authorization,
      };
    }

    const response = await axios(axiosConfig);

    // Forward response
    res.status(response.status).json(response.data);
  } catch (error: any) {
    console.error("Proxy error:", error.message);
    
    if (error.response) {
      // Backend responded with error
      res.status(error.response.status).json(error.response.data);
    } else if (error.request) {
      // Request made but no response
      res.status(503).json({
        error: "Backend service unavailable",
        message: "Cannot connect to backend server",
      });
    } else {
      // Something else happened
      res.status(500).json({
        error: "Internal server error",
        message: error.message,
      });
    }
  }
});

// Export function
export const api = functions.https.onRequest(app);

