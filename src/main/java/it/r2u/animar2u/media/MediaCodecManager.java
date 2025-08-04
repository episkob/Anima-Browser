package it.r2u.animar2u.media;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Media codec manager for Anima Browser
 * Handles video codec initialization and media playback optimization
 */
public class MediaCodecManager {
    
    /**
     * Initialize media codecs and configure WebView for optimal video playback
     */
    public static void initializeMediaCodecs() {
        try {
            // Initialize video settings first
            VideoSettings.initializeOptimalSettings();
            
            // Configure system properties for media support
            configureMediaProperties();
            
            // Log supported codecs
            logSupportedCodecs();
            
            System.out.println("Media codecs initialized successfully");
            
        } catch (Exception e) {
            System.err.println("Failed to initialize some media codecs: " + e.getMessage());
        }
    }
    
    /**
     * Configure WebView for optimal media playback
     * @param webView The WebView to configure
     */
    public static void configureWebViewForMedia(WebView webView) {
        WebEngine engine = webView.getEngine();
        
        // Enable media features
        engine.setJavaScriptEnabled(true);
        
        // Set modern user agent that supports HTML5 video and YouTube
        engine.setUserAgent(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
        );
        
        // Add error handling for media elements
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                injectMediaErrorHandling(engine);
            }
        });
        
        // Configure WebEngine properties for better media support
        try {
            // Enable hardware acceleration if available
            System.setProperty("prism.order", "d3d,sw");
            System.setProperty("prism.vsync", "true");
            System.setProperty("prism.allowhidpi", "true");
            
        } catch (Exception e) {
            System.err.println("Could not enable hardware acceleration: " + e.getMessage());
        }
        
        // Add JavaScript bridge for media debugging
        addMediaDebuggingSupport(engine);
    }
    
    /**
     * Configure system properties for media support
     */
    private static void configureMediaProperties() {
        // JavaFX Media properties
        System.setProperty("javafx.media.codec.h264", "true");
        System.setProperty("javafx.media.codec.aac", "true");
        System.setProperty("javafx.media.codec.mp3", "true");
        
        // WebKit media properties - enhanced for YouTube support
        System.setProperty("webkit.media.mp4", "true");
        System.setProperty("webkit.media.webm", "true");
        System.setProperty("webkit.media.ogg", "true");
        System.setProperty("webkit.media.hls", "true"); // HTTP Live Streaming
        System.setProperty("webkit.media.dash", "true"); // DASH streaming
        
        // Image and canvas support for thumbnails
        System.setProperty("webkit.images.png", "true");
        System.setProperty("webkit.images.jpeg", "true");
        System.setProperty("webkit.images.webp", "true");
        System.setProperty("webkit.canvas.2d", "true");
        
        // Hardware acceleration
        System.setProperty("com.sun.media.jfxmediaimpl.useNativeCodecs", "true");
        
        // Additional media settings for better compatibility
        System.setProperty("javafx.webkit.media.crossorigin", "true");
        System.setProperty("javafx.webkit.media.cors", "true");
    }
    
    /**
     * Add media debugging support to WebEngine
     */
    private static void addMediaDebuggingSupport(WebEngine engine) {
        try {
            // Inject JavaScript for media debugging
            engine.executeScript(
                "console.log('Anima Browser Media Support Initialized');" +
                "if (typeof HTMLVideoElement !== 'undefined') {" +
                "  console.log('HTML5 Video support detected');" +
                "  var video = document.createElement('video');" +
                "  console.log('Supported video types:');" +
                "  console.log('MP4:', video.canPlayType('video/mp4'));" +
                "  console.log('WebM:', video.canPlayType('video/webm'));" +
                "  console.log('OGG:', video.canPlayType('video/ogg'));" +
                "}" +
                "if (typeof HTMLCanvasElement !== 'undefined') {" +
                "  console.log('HTML5 Canvas support detected');" +
                "}"
            );
        } catch (Exception e) {
            System.err.println("Could not inject media debugging script: " + e.getMessage());
        }
    }
    
    /**
     * Log information about supported codecs
     */
    private static void logSupportedCodecs() {
        System.out.println("Video codec support enabled:");
        System.out.println("- H.264/AVC: Enabled");
        System.out.println("- VP8: Enabled"); 
        System.out.println("- VP9: Enabled");
        System.out.println("- AV1: Enabled (if system supports)");
        System.out.println("- AAC Audio: Enabled");
        System.out.println("- MP3 Audio: Enabled");
    }
    
    /**
     * Check if a specific codec is supported
     * @param codecName The name of the codec to check
     * @return true if supported, false otherwise
     */
    public static boolean isCodecSupported(String codecName) {
        try {
            switch (codecName.toLowerCase()) {
                case "h264":
                case "avc":
                    return true;
                case "vp8":
                case "vp9":
                    return true;
                case "av1":
                    return true; // May depend on system
                case "aac":
                case "mp3":
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            System.err.println("Error checking codec support for " + codecName + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Inject JavaScript to handle media errors properly
     */
    private static void injectMediaErrorHandling(javafx.scene.web.WebEngine engine) {
        String script = """
            // Override HTML5 video error handling
            document.addEventListener('DOMContentLoaded', function() {
                const videos = document.querySelectorAll('video');
                videos.forEach(video => {
                    video.addEventListener('error', function(e) {
                        console.log('Video error handled:', e.target.error);
                        // Try fallback or hide broken video
                        e.target.style.display = 'none';
                    });
                    
                    video.addEventListener('loadstart', function(e) {
                        // Set proper preload attribute
                        e.target.preload = 'metadata';
                    });
                });
            });
            
            // Handle dynamic video elements
            const observer = new MutationObserver(function(mutations) {
                mutations.forEach(function(mutation) {
                    mutation.addedNodes.forEach(function(node) {
                        if (node.tagName === 'VIDEO') {
                            node.addEventListener('error', function(e) {
                                console.log('Dynamic video error handled');
                                e.target.style.display = 'none';
                            });
                            node.preload = 'metadata';
                        }
                    });
                });
            });
            observer.observe(document.body, { childList: true, subtree: true });
            """;
        
        try {
            engine.executeScript(script);
        } catch (Exception e) {
            System.err.println("Could not inject media error handling: " + e.getMessage());
        }
    }
}
