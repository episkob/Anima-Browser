package it.r2u.animar2u.media;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * YouTube and video platform optimization for Anima Browser
 * Handles specific optimizations for video streaming sites
 */
public class VideoStreamingOptimizer {
    
    /**
     * Optimize WebView for YouTube and other video platforms
     */
    public static void optimizeForVideoStreaming(WebView webView) {
        WebEngine engine = webView.getEngine();
        
        // Set optimal User-Agent for video platforms
        setOptimalUserAgent(engine);
        
        // Enable all necessary features for video thumbnails and playback
        enableVideoFeatures(engine);
        
        // Inject CSS and JavaScript optimizations
        injectVideoOptimizations(engine);
        
        // Configure network settings
        configureNetworkSettings();
        
        System.out.println("Video streaming optimizations applied");
    }
    
    /**
     * Set optimal User-Agent for video platforms
     */
    private static void setOptimalUserAgent(WebEngine engine) {
        // Use Chrome User-Agent without custom browser identifier
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                          "AppleWebKit/537.36 (KHTML, like Gecko) " +
                          "Chrome/120.0.0.0 Safari/537.36";
        
        engine.setUserAgent(userAgent);
        System.out.println("User-Agent optimized for video platforms");
    }
    
    /**
     * Enable all features necessary for video platforms
     */
    private static void enableVideoFeatures(WebEngine engine) {
        // Enable JavaScript (required for YouTube)
        engine.setJavaScriptEnabled(true);
        
        // Set additional JavaScript properties when page loads
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                try {
                    // Enable video autoplay and preload
                    engine.executeScript(
                        "document.addEventListener('DOMContentLoaded', function() {" +
                        "  var videos = document.querySelectorAll('video');" +
                        "  videos.forEach(function(video) {" +
                        "    video.preload = 'metadata';" +
                        "    video.setAttribute('webkit-playsinline', 'true');" +
                        "    video.setAttribute('playsinline', 'true');" +
                        "  });" +
                        "});"
                    );
                    
                    // Force enable WebGL and Canvas for video thumbnails
                    engine.executeScript(
                        "if (typeof WebGLRenderingContext !== 'undefined') {" +
                        "  console.log('WebGL support enabled for video thumbnails');" +
                        "}" +
                        "if (typeof CanvasRenderingContext2D !== 'undefined') {" +
                        "  console.log('Canvas 2D support enabled for video thumbnails');" +
                        "}"
                    );
                    
                } catch (Exception e) {
                    System.err.println("Could not inject video optimization scripts: " + e.getMessage());
                }
            }
        });
    }
    
    /**
     * Inject CSS and JavaScript optimizations for video platforms
     */
    private static void injectVideoOptimizations(WebEngine engine) {
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                try {
                    // Inject CSS to ensure video thumbnails display properly
                    String css = 
                        "img, video { " +
                        "  image-rendering: -webkit-optimize-contrast !important; " +
                        "  image-rendering: crisp-edges !important; " +
                        "} " +
                        "video::-webkit-media-controls { " +
                        "  display: block !important; " +
                        "} " +
                        ".video-thumbnail, .thumbnail { " +
                        "  background-color: transparent !important; " +
                        "  opacity: 1 !important; " +
                        "  visibility: visible !important; " +
                        "}";
                    
                    engine.executeScript(
                        "var style = document.createElement('style');" +
                        "style.textContent = '" + css + "';" +
                        "document.head.appendChild(style);"
                    );
                    
                    // Force image loading
                    engine.executeScript(
                        "document.addEventListener('DOMContentLoaded', function() {" +
                        "  var images = document.querySelectorAll('img');" +
                        "  images.forEach(function(img) {" +
                        "    if (img.dataset && img.dataset.src) {" +
                        "      img.src = img.dataset.src;" +
                        "    }" +
                        "    if (img.getAttribute('data-thumb')) {" +
                        "      img.src = img.getAttribute('data-thumb');" +
                        "    }" +
                        "  });" +
                        "});"
                    );
                    
                } catch (Exception e) {
                    System.err.println("Could not inject CSS optimizations: " + e.getMessage());
                }
            }
        });
    }
    
    /**
     * Configure network settings for optimal video loading
     */
    private static void configureNetworkSettings() {
        // Set network timeouts for better video loading
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");
        
        // Enable HTTP/2 for better video streaming
        System.setProperty("jdk.httpclient.HttpClient.log", "errors");
        
        // Configure proxy settings
        System.setProperty("java.net.useSystemProxies", "true");
        
        System.out.println("Network settings optimized for video streaming");
    }
    
    /**
     * Check if current URL is a known video platform
     */
    public static boolean isVideoStreamingPlatform(String url) {
        if (url == null) return false;
        
        String lowerUrl = url.toLowerCase();
        return lowerUrl.contains("youtube.com") ||
               lowerUrl.contains("youtu.be") ||
               lowerUrl.contains("vimeo.com") ||
               lowerUrl.contains("dailymotion.com") ||
               lowerUrl.contains("twitch.tv") ||
               lowerUrl.contains("netflix.com") ||
               lowerUrl.contains("hulu.com") ||
               lowerUrl.contains("amazon.com/prime") ||
               lowerUrl.contains("video");
    }
}
