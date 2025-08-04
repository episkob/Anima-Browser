package it.r2u.animar2u.core.config;

/**
 * Manages system properties for Anima Browser
 * Centralizes all JavaFX and WebKit configuration properties
 */
public class SystemPropertyManager {
    
    /**
     * Initialize all system properties required for optimal browser operation
     */
    public static void initializeSystemProperties() {
        // Core JavaFX and application properties
        initializeJavaFXProperties();
        
        // WebKit media and video properties
        initializeMediaProperties();
        
        // Graphics and rendering properties
        initializeGraphicsProperties();
        
        // Network and security properties
        initializeNetworkProperties();
        
        // Performance and optimization properties
        initializePerformanceProperties();
        
        System.out.println("System properties initialized for optimal browser performance");
    }
    
    /**
     * Initialize core JavaFX properties
     */
    private static void initializeJavaFXProperties() {
        // Set unique user data directory to avoid conflicts
        String userDataDir = System.getProperty("java.io.tmpdir") + 
            "/anima-browser-" + System.currentTimeMillis();
        System.setProperty("javafx.webkit.userDataDirectory", userDataDir);
        System.setProperty("com.sun.webkit.useUserDataDir", "true");
        
        // JavaFX performance and compatibility
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "false");
    }
    
    /**
     * Initialize WebKit media and video properties
     */
    private static void initializeMediaProperties() {
        // Enable WebKit media player with full format support
        System.setProperty("webkit.media.enable", "true");
        System.setProperty("com.sun.webkit.media.enabled", "true");
        System.setProperty("javafx.webkit.media", "true");
        
        // Video container formats
        System.setProperty("webkit.media.mp4", "true");
        System.setProperty("webkit.media.webm", "true");
        System.setProperty("webkit.media.ogg", "true");
        System.setProperty("webkit.media.avi", "true");
        System.setProperty("webkit.media.mov", "true");
        System.setProperty("webkit.media.wmv", "true");
        System.setProperty("webkit.media.flv", "true");
        System.setProperty("webkit.media.mkv", "true");
        System.setProperty("webkit.media.3gp", "true");
        System.setProperty("webkit.media.m4v", "true");
        System.setProperty("webkit.media.asf", "true");
        
        // Streaming protocols
        System.setProperty("webkit.media.hls", "true");
        System.setProperty("webkit.media.dash", "true");
        System.setProperty("webkit.media.rtmp", "true");
        System.setProperty("webkit.media.rtsp", "true");
        System.setProperty("webkit.media.mms", "true");
        
        // HTML5 media elements
        System.setProperty("webkit.html.video", "true");
        System.setProperty("webkit.html.audio", "true");
        System.setProperty("webkit.media.mediaSource", "true");
        System.setProperty("webkit.media.webAudio", "true");
        
        // Core JavaFX media configuration
        System.setProperty("javafx.media.engine", "com.sun.media.jfxmedia.MediaManager");
        System.setProperty("com.sun.media.jfxmediaimpl.platform", "JavaPlatform");
        System.setProperty("javafx.media.logging", "false");
        
        // Video codecs
        initializeVideoCodecs();
        
        // Audio codecs
        initializeAudioCodecs();
        
        // Media player configuration
        initializeMediaPlayerProperties();
    }
    
    /**
     * Initialize video codec properties
     */
    private static void initializeVideoCodecs() {
        System.setProperty("javafx.media.codec.h264", "true");
        System.setProperty("javafx.media.codec.h265", "true");
        System.setProperty("javafx.media.codec.vp8", "true");
        System.setProperty("javafx.media.codec.vp9", "true");
        System.setProperty("javafx.media.codec.av1", "true");
        System.setProperty("javafx.media.codec.xvid", "true");
        System.setProperty("javafx.media.codec.divx", "true");
        System.setProperty("javafx.media.codec.mpeg1", "true");
        System.setProperty("javafx.media.codec.mpeg2", "true");
        System.setProperty("javafx.media.codec.mpeg4", "true");
        System.setProperty("javafx.media.codec.theora", "true");
    }
    
    /**
     * Initialize audio codec properties
     */
    private static void initializeAudioCodecs() {
        System.setProperty("javafx.media.codec.aac", "true");
        System.setProperty("javafx.media.codec.mp3", "true");
        System.setProperty("javafx.media.codec.opus", "true");
        System.setProperty("javafx.media.codec.vorbis", "true");
        System.setProperty("javafx.media.codec.flac", "true");
        System.setProperty("javafx.media.codec.wav", "true");
        System.setProperty("javafx.media.codec.wma", "true");
        System.setProperty("javafx.media.codec.ac3", "true");
        System.setProperty("javafx.media.codec.dts", "true");
        System.setProperty("javafx.media.codec.pcm", "true");
        System.setProperty("javafx.media.codec.amr", "true");
    }
    
    /**
     * Initialize media player specific properties
     */
    private static void initializeMediaPlayerProperties() {
        // Enable media player creation
        System.setProperty("webkit.media.player.disable", "false");
        System.setProperty("com.sun.webkit.media.player.enabled", "true");
        
        // URL protocols for media
        System.setProperty("webkit.media.protocols", "http,https,data,blob,file,ftp");
        System.setProperty("webkit.media.data.protocol", "true");
        System.setProperty("webkit.media.blob.protocol", "true");
        System.setProperty("webkit.media.file.protocol", "true");
        
        // WebKit media framework
        System.setProperty("com.sun.webkit.media.MediaManager.enabled", "true");
        System.setProperty("com.sun.webkit.media.createMediaPlayer", "true");
        System.setProperty("javafx.webkit.media.createPlayer", "true");
        
        // Media MIME types
        System.setProperty("webkit.media.mime.video", "true");
        System.setProperty("webkit.media.mime.audio", "true");
        System.setProperty("webkit.media.mime.all", "true");
        
        // DOM media elements
        System.setProperty("webkit.dom.media.elements", "true");
        System.setProperty("webkit.html.media.tags", "true");
        
        // Advanced media features
        System.setProperty("webkit.media.subtitles", "true");
        System.setProperty("webkit.media.chapters", "true");
        System.setProperty("webkit.media.fullscreen", "true");
        System.setProperty("webkit.media.pictureInPicture", "true");
        System.setProperty("webkit.media.casting", "true");
    }
    
    /**
     * Initialize graphics and rendering properties
     */
    private static void initializeGraphicsProperties() {
        // Keep images enabled
        System.setProperty("webkit.images.enable", "true");
        System.setProperty("webkit.images.png", "true");
        System.setProperty("webkit.images.jpeg", "true");
        System.setProperty("webkit.images.webp", "true");
        System.setProperty("webkit.canvas.enable", "true");
        System.setProperty("webkit.webgl.enable", "false"); // Disabled for stability
        
        // Hardware acceleration and performance
        System.setProperty("webkit.media.hardware.acceleration", "true");
        System.setProperty("webkit.media.gpu.decoding", "true");
        System.setProperty("webkit.media.multithreading", "true");
    }
    
    /**
     * Initialize network and security properties
     */
    private static void initializeNetworkProperties() {
        // Network configuration
        System.setProperty("javafx.webkit.network.useSystemProxies", "true");
        System.setProperty("javafx.webkit.security.allowUniversalAccessFromFileURLs", "false");
        System.setProperty("javafx.webkit.security.allowFileAccessFromFileURLs", "false");
    }
    
    /**
     * Initialize performance and logging properties
     */
    private static void initializePerformanceProperties() {
        // Enhanced error handling and logging
        System.setProperty("webkit.media.errorHandling", "silent");
        System.setProperty("javafx.webkit.logging", "false");
        System.setProperty("com.sun.webkit.logging.level", "OFF");
        System.setProperty("prism.verbose", "false");
    }
}
