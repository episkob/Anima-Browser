package it.r2u.animar2u;

/**
 * JavaFX Launcher class to avoid "JavaFX runtime components are missing" error
 * This class launches the main JavaFX application
 */
public class Launcher {
    public static void main(String[] args) {
        // Set system properties for JavaFX
        System.setProperty("javafx.preloader", "");
        System.setProperty("java.awt.headless", "false");
        
        try {
            // Launch the main application
            AnimaApplication.main(args);
        } catch (Exception e) {
            System.err.println("Error starting Anima Browser: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
