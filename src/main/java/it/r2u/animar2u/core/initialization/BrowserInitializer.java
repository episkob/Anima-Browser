package it.r2u.animar2u.core.initialization;

import it.r2u.animar2u.core.config.SystemPropertyManager;
import it.r2u.animar2u.media.MediaCodecManager;

/**
 * Manages the initialization sequence for Anima Browser
 * Ensures all components are initialized in the correct order
 */
public class BrowserInitializer {
    
    /**
     * Initialize all browser components in the correct order
     */
    public static void initializeAll() {
        System.out.println("=== Initializing Anima Browser ===");
        
        try {
            // Step 1: Initialize system properties first
            System.out.println("1. Initializing system properties...");
            SystemPropertyManager.initializeSystemProperties();
            
            // Step 2: Initialize media codecs
            System.out.println("2. Initializing media codecs...");
            MediaCodecManager.initializeMediaCodecs();
            
            // Step 3: Additional browser initialization can be added here
            // (e.g., extension manager, plugin loader, etc.)
            
            System.out.println("=== Browser initialization complete ===");
            
        } catch (Exception e) {
            System.err.println("Error during browser initialization: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize browser components", e);
        }
    }
    
    /**
     * Initialize minimal browser components for quick startup
     */
    public static void initializeMinimal() {
        System.out.println("=== Minimal Browser Initialization ===");
        
        try {
            // Only initialize essential components
            SystemPropertyManager.initializeSystemProperties();
            
            System.out.println("=== Minimal initialization complete ===");
            
        } catch (Exception e) {
            System.err.println("Error during minimal initialization: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize minimal browser components", e);
        }
    }
}
