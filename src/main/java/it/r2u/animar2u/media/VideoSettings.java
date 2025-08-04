package it.r2u.animar2u.media;

/**
 * Video settings and optimization configuration for Anima Browser
 */
public class VideoSettings {
    
    // Video quality settings
    public static final String HIGH_QUALITY = "high";
    public static final String MEDIUM_QUALITY = "medium";
    public static final String LOW_QUALITY = "low";
    public static final String AUTO_QUALITY = "auto";
    
    // Supported video formats
    public static final String[] SUPPORTED_VIDEO_FORMATS = {
        "mp4", "webm", "ogg", "avi", "mov", "wmv", "flv", "m4v"
    };
    
    // Supported audio formats
    public static final String[] SUPPORTED_AUDIO_FORMATS = {
        "mp3", "aac", "ogg", "wav", "flac", "m4a"
    };
    
    // Video codec priorities (preferred order)
    public static final String[] VIDEO_CODEC_PRIORITY = {
        "h264", "vp9", "vp8", "av1"
    };
    
    private static String currentVideoQuality = AUTO_QUALITY;
    private static boolean hardwareAccelerationEnabled = true;
    private static boolean adaptiveStreamingEnabled = true;
    
    /**
     * Get current video quality setting
     */
    public static String getCurrentVideoQuality() {
        return currentVideoQuality;
    }
    
    /**
     * Set video quality
     */
    public static void setVideoQuality(String quality) {
        if (isValidQuality(quality)) {
            currentVideoQuality = quality;
            applyVideoQualitySettings();
        }
    }
    
    /**
     * Check if hardware acceleration is enabled
     */
    public static boolean isHardwareAccelerationEnabled() {
        return hardwareAccelerationEnabled;
    }
    
    /**
     * Enable or disable hardware acceleration
     */
    public static void setHardwareAcceleration(boolean enabled) {
        hardwareAccelerationEnabled = enabled;
        applyHardwareAccelerationSettings();
    }
    
    /**
     * Check if adaptive streaming is enabled
     */
    public static boolean isAdaptiveStreamingEnabled() {
        return adaptiveStreamingEnabled;
    }
    
    /**
     * Enable or disable adaptive streaming
     */
    public static void setAdaptiveStreaming(boolean enabled) {
        adaptiveStreamingEnabled = enabled;
    }
    
    /**
     * Check if a video format is supported
     */
    public static boolean isVideoFormatSupported(String format) {
        for (String supportedFormat : SUPPORTED_VIDEO_FORMATS) {
            if (supportedFormat.equalsIgnoreCase(format)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if an audio format is supported
     */
    public static boolean isAudioFormatSupported(String format) {
        for (String supportedFormat : SUPPORTED_AUDIO_FORMATS) {
            if (supportedFormat.equalsIgnoreCase(format)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get optimal video codec for current system
     */
    public static String getOptimalVideoCodec() {
        for (String codec : VIDEO_CODEC_PRIORITY) {
            if (MediaCodecManager.isCodecSupported(codec)) {
                return codec;
            }
        }
        return "h264"; // fallback
    }
    
    /**
     * Initialize video settings with optimal defaults
     */
    public static void initializeOptimalSettings() {
        System.out.println("Initializing optimal video settings...");
        
        // Detect system capabilities and set optimal settings
        detectSystemCapabilities();
        
        // Apply settings
        applyVideoQualitySettings();
        applyHardwareAccelerationSettings();
        
        System.out.println("Video settings initialized:");
        System.out.println("- Quality: " + currentVideoQuality);
        System.out.println("- Hardware Acceleration: " + hardwareAccelerationEnabled);
        System.out.println("- Adaptive Streaming: " + adaptiveStreamingEnabled);
        System.out.println("- Optimal Codec: " + getOptimalVideoCodec());
    }
    
    private static boolean isValidQuality(String quality) {
        return HIGH_QUALITY.equals(quality) || 
               MEDIUM_QUALITY.equals(quality) || 
               LOW_QUALITY.equals(quality) || 
               AUTO_QUALITY.equals(quality);
    }
    
    private static void applyVideoQualitySettings() {
        switch (currentVideoQuality) {
            case HIGH_QUALITY:
                System.setProperty("javafx.media.quality", "high");
                System.setProperty("webkit.media.quality", "1080p");
                break;
            case MEDIUM_QUALITY:
                System.setProperty("javafx.media.quality", "medium");
                System.setProperty("webkit.media.quality", "720p");
                break;
            case LOW_QUALITY:
                System.setProperty("javafx.media.quality", "low");
                System.setProperty("webkit.media.quality", "480p");
                break;
            case AUTO_QUALITY:
            default:
                System.setProperty("javafx.media.quality", "auto");
                System.setProperty("webkit.media.quality", "auto");
                break;
        }
    }
    
    private static void applyHardwareAccelerationSettings() {
        if (hardwareAccelerationEnabled) {
            System.setProperty("prism.order", "d3d,sw");
            System.setProperty("prism.allowhidpi", "true");
            System.setProperty("javafx.animation.fullspeed", "true");
        } else {
            System.setProperty("prism.order", "sw");
        }
    }
    
    private static void detectSystemCapabilities() {
        try {
            // Simple system capability detection
            String osName = System.getProperty("os.name").toLowerCase();
            String osArch = System.getProperty("os.arch").toLowerCase();
            
            // Enable hardware acceleration by default on modern systems
            if (osName.contains("windows") && (osArch.contains("64") || osArch.contains("amd64"))) {
                hardwareAccelerationEnabled = true;
                currentVideoQuality = AUTO_QUALITY;
            }
            
        } catch (Exception e) {
            System.err.println("Could not detect system capabilities: " + e.getMessage());
            // Use conservative defaults
            hardwareAccelerationEnabled = false;
            currentVideoQuality = MEDIUM_QUALITY;
        }
    }
}
