package it.r2u.animar2u;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.r2u.animar2u.core.initialization.BrowserInitializer;

import java.io.IOException;

/**
 * Main application class for Anima Browser.
 * Entry point for the browser application, initializes the main window and UI.
 */
public class AnimaApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Initialize all browser components
            BrowserInitializer.initializeAll();
            
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
        // System properties are now initialized in BrowserInitializer
        // during the start() method for better organization
        launch(args);
    }
}
