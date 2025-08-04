package it.r2u.animar2u;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.r2u.animar2u.media.MediaCodecManager;

import java.io.IOException;

/**
 * Main application class for Anima Browser.
 * Entry point for the browser application, initializes the main window and UI.
 */
public class AnimaApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Initialize media codecs before loading UI
            MediaCodecManager.initializeMediaCodecs();
            
            // Load the main browser FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                AnimaApplication.class.getResource("/fxml/browser-main.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            
            // Apply CSS styling
            scene.getStylesheets().add(
                AnimaApplication.class.getResource("/css/browser-style.css").toExternalForm()
            );
            
            // Configure main window
            stage.setTitle("Anima Browser - Secure Web Browser with Video Support");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            
            // Center window on screen
            stage.centerOnScreen();
            
            // Show the window
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Error starting Anima Browser: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Application entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Set unique user data directory to avoid conflicts
        String userDataDir = System.getProperty("java.io.tmpdir") + 
            "/anima-browser-" + System.currentTimeMillis();
        System.setProperty("javafx.webkit.userDataDirectory", userDataDir);
        System.setProperty("com.sun.webkit.useUserDataDir", "true");
        
        // Set system properties for better JavaFX performance and compatibility
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "false");
        
        // Enable WebKit media player with full format support
        System.setProperty("webkit.media.enable", "true");
        System.setProperty("com.sun.webkit.media.enabled", "true");
        System.setProperty("javafx.webkit.media", "true");
        
        // Enable all video container formats
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
        
        // Enable streaming protocol support
        System.setProperty("webkit.media.hls", "true");
        System.setProperty("webkit.media.dash", "true");
        System.setProperty("webkit.media.rtmp", "true");
        System.setProperty("webkit.media.rtsp", "true");
        System.setProperty("webkit.media.mms", "true");
        
        // Enable audio/video HTML elements in WebKit
        System.setProperty("webkit.html.video", "true");
        System.setProperty("webkit.html.audio", "true");
        System.setProperty("webkit.media.mediaSource", "true");
        System.setProperty("webkit.media.webAudio", "true");
        
        // Core JavaFX media configuration with enhanced support
        System.setProperty("javafx.media.engine", "com.sun.media.jfxmedia.MediaManager");
        System.setProperty("com.sun.media.jfxmediaimpl.platform", "JavaPlatform");
        System.setProperty("javafx.media.logging", "false");
        
        // Enable all video codecs
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
        
        // Enable all audio codecs
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
        
        // Graphics and rendering support (keep images enabled)
        System.setProperty("webkit.images.enable", "true");
        System.setProperty("webkit.images.png", "true");
        System.setProperty("webkit.images.jpeg", "true");
        System.setProperty("webkit.images.webp", "true");
        System.setProperty("webkit.canvas.enable", "true");
        System.setProperty("webkit.webgl.enable", "false");
        
        // Network configuration
        System.setProperty("javafx.webkit.network.useSystemProxies", "true");
        System.setProperty("javafx.webkit.security.allowUniversalAccessFromFileURLs", "false");
        System.setProperty("javafx.webkit.security.allowFileAccessFromFileURLs", "false");
        
        // Enhanced error handling and logging
        System.setProperty("webkit.media.errorHandling", "silent");
        System.setProperty("javafx.webkit.logging", "false");
        System.setProperty("com.sun.webkit.logging.level", "OFF");
        System.setProperty("prism.verbose", "false");
        
        // Enable media player creation
        System.setProperty("webkit.media.player.disable", "false");
        System.setProperty("com.sun.webkit.media.player.enabled", "true");
        
        // Enable all URL protocols for media
        System.setProperty("webkit.media.protocols", "http,https,data,blob,file,ftp");
        System.setProperty("webkit.media.data.protocol", "true");
        System.setProperty("webkit.media.blob.protocol", "true");
        System.setProperty("webkit.media.file.protocol", "true");
        
        // Enable WebKit media framework
        System.setProperty("com.sun.webkit.media.MediaManager.enabled", "true");
        System.setProperty("com.sun.webkit.media.createMediaPlayer", "true");
        System.setProperty("javafx.webkit.media.createPlayer", "true");
        
        // Enable all media MIME types in WebKit
        System.setProperty("webkit.media.mime.video", "true");
        System.setProperty("webkit.media.mime.audio", "true");
        System.setProperty("webkit.media.mime.all", "true");
        
        // Enable media element creation in DOM
        System.setProperty("webkit.dom.media.elements", "true");
        System.setProperty("webkit.html.media.tags", "true");
        
        // Advanced media features
        System.setProperty("webkit.media.subtitles", "true");
        System.setProperty("webkit.media.chapters", "true");
        System.setProperty("webkit.media.fullscreen", "true");
        System.setProperty("webkit.media.pictureInPicture", "true");
        System.setProperty("webkit.media.casting", "true");
        
        // Hardware acceleration and performance
        System.setProperty("webkit.media.hardware.acceleration", "true");
        System.setProperty("webkit.media.gpu.decoding", "true");
        System.setProperty("webkit.media.multithreading", "true");
        
        launch(args);
    }
}
