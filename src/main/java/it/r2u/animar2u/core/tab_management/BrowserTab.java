package it.r2u.animar2u.core.tab_management;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import it.r2u.animar2u.ui.controllers.MainBrowserController;
import it.r2u.animar2u.media.MediaCodecManager;
import it.r2u.animar2u.media.VideoStreamingOptimizer;

/**
 * Represents a browser tab with WebView functionality
 */
public class BrowserTab {
    
    private WebView webView;
    private WebEngine webEngine;
    private String title;
    private String currentUrl;
    private MainBrowserController controller;
    
    /**
     * Creates a new browser tab
     * @param controller The main browser controller
     */
    public BrowserTab(MainBrowserController controller) {
        this.controller = controller;
        this.title = "New Tab";
        this.currentUrl = "";
        
        // Initialize WebView and WebEngine
        webView = new WebView();
        webEngine = webView.getEngine();
        
        // Configure web engine
        configureWebEngine();
        
        // Setup event handlers
        setupEventHandlers();
    }
    
    /**
     * Configure the WebEngine with optimal settings
     */
    private void configureWebEngine() {
        // Configure WebView for optimal media playback
        MediaCodecManager.configureWebViewForMedia(webView);
        
        // Apply video streaming optimizations
        VideoStreamingOptimizer.optimizeForVideoStreaming(webView);
        
        // Enable JavaScript (already done in MediaCodecManager, but keeping for clarity)
        webEngine.setJavaScriptEnabled(true);
        
        // User agent is set by VideoStreamingOptimizer for optimal compatibility
    }
    
    /**
     * Setup event handlers for tab functionality
     */
    private void setupEventHandlers() {
        // Location change listener
        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                              String oldValue, String newValue) {
                currentUrl = newValue != null ? newValue : "";
                controller.updateUIForTab(BrowserTab.this);
            }
        });
        
        // Title change listener
        webEngine.titleProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                              String oldValue, String newValue) {
                title = newValue != null && !newValue.isEmpty() ? newValue : "Untitled";
                controller.updateUIForTab(BrowserTab.this);
            }
        });
        
        // Loading progress listener
        webEngine.getLoadWorker().progressProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable,
                                  Number oldValue, Number newValue) {
                    controller.updateProgress(newValue.doubleValue());
                }
            }
        );
        
        // Loading state listener
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                                  Worker.State oldState, Worker.State newState) {
                    switch (newState) {
                        case READY:
                            controller.updateStatus("Ready");
                            break;
                        case SCHEDULED:
                            controller.updateStatus("Scheduled");
                            break;
                        case RUNNING:
                            controller.updateStatus("Loading...");
                            break;
                        case SUCCEEDED:
                            controller.updateStatus("Ready");
                            controller.updateProgress(-1); // Hide progress bar
                            
                            // Apply additional optimizations for video platforms
                            if (VideoStreamingOptimizer.isVideoStreamingPlatform(currentUrl)) {
                                System.out.println("Video platform detected: " + currentUrl);
                                // Additional optimizations are already applied via MediaCodecManager
                            }
                            break;
                        case FAILED:
                            controller.updateStatus("Failed to load page");
                            controller.updateProgress(-1); // Hide progress bar
                            break;
                        case CANCELLED:
                            controller.updateStatus("Loading cancelled");
                            controller.updateProgress(-1); // Hide progress bar
                            break;
                    }
                }
            }
        );
        
        // Alert handler (JavaScript alerts)
        webEngine.setOnAlert(event -> {
            // Show JavaScript alert in console (placeholder implementation)
            System.out.println("JavaScript Alert: " + event.getData());
        });
        
        // Confirm handler (JavaScript confirms)
        webEngine.setConfirmHandler(message -> {
            // Show JavaScript confirm in console and return true (placeholder implementation)
            System.out.println("JavaScript Confirm: " + message);
            return true; // Default to OK
        });
        
        // Error handler
        webEngine.setOnError(event -> {
            System.err.println("WebEngine Error: " + event.getMessage());
            controller.updateStatus("Error loading content");
        });
    }
    
    // Navigation methods
    
    /**
     * Navigate to a URL
     * @param url The URL to navigate to
     */
    public void navigateTo(String url) {
        if (url != null && !url.trim().isEmpty()) {
            // Ensure URL has protocol
            if (!url.contains("://")) {
                url = "https://" + url;
            }
            webEngine.load(url);
        }
    }
    
    /**
     * Go back in history
     */
    public void goBack() {
        try {
            webEngine.getHistory().go(-1);
        } catch (IndexOutOfBoundsException e) {
            // No history to go back to
        }
    }
    
    /**
     * Go forward in history
     */
    public void goForward() {
        try {
            webEngine.getHistory().go(1);
        } catch (IndexOutOfBoundsException e) {
            // No history to go forward to
        }
    }
    
    /**
     * Refresh the current page
     */
    public void refresh() {
        webEngine.reload();
    }
    
    /**
     * Stop loading the current page
     */
    public void stop() {
        webEngine.getLoadWorker().cancel();
    }
    
    // Getters
    
    /**
     * Get the WebView node for this tab
     * @return The WebView node
     */
    public Node getWebView() {
        return webView;
    }
    
    /**
     * Get the title of this tab
     * @return The tab title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get the current URL of this tab
     * @return The current URL
     */
    public String getCurrentUrl() {
        return currentUrl;
    }
    
    /**
     * Get the WebEngine for this tab
     * @return The WebEngine instance
     */
    public WebEngine getWebEngine() {
        return webEngine;
    }
    
    /**
     * Check if this tab can go back
     * @return true if can go back
     */
    public boolean canGoBack() {
        return webEngine.getHistory().getCurrentIndex() > 0;
    }
    
    /**
     * Check if this tab can go forward
     * @return true if can go forward
     */
    public boolean canGoForward() {
        return webEngine.getHistory().getCurrentIndex() < 
               webEngine.getHistory().getEntries().size() - 1;
    }
    
    /**
     * Check if this tab is currently loading
     * @return true if loading
     */
    public boolean isLoading() {
        return webEngine.getLoadWorker().getState() == Worker.State.RUNNING;
    }
    
    /**
     * Execute JavaScript in this tab
     * @param script The JavaScript code to execute
     * @return The result of the JavaScript execution
     */
    public Object executeScript(String script) {
        try {
            return webEngine.executeScript(script);
        } catch (Exception e) {
            System.err.println("Error executing JavaScript: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the content (WebView node) for this tab
     * @return The WebView node
     */
    public Node getContent() {
        return webView;
    }
    
    /**
     * Navigate to a URL (alias for navigateTo)
     * @param url The URL to navigate to
     */
    public void navigate(String url) {
        navigateTo(url);
    }
    
    /**
     * Dispose of this tab and clean up resources
     */
    public void dispose() {
        try {
            if (webEngine != null) {
                webEngine.getLoadWorker().cancel();
                webEngine.load(null);
            }
        } catch (Exception e) {
            System.err.println("Error disposing tab: " + e.getMessage());
        }
    }
}
