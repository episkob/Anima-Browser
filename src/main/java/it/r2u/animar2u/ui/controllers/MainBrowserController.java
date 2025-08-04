package it.r2u.animar2u.ui.controllers;

import it.r2u.animar2u.core.tab_management.TabManager;
import it.r2u.animar2u.core.tab_management.BrowserTab;
import it.r2u.animar2u.core.storage.BookmarkManager;
import it.r2u.animar2u.core.storage.Bookmark;
import it.r2u.animar2u.core.storage.DownloadManager;
import it.r2u.animar2u.core.navigation.HistoryManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller for the Anima Browser interface.
 * Manages the overall browser UI including tabs, navigation, and menu actions.
 */
public class MainBrowserController implements Initializable {
    
    // FXML injected components
    @FXML private MenuBar menuBar;
    @FXML private Button backButton;
    @FXML private Button forwardButton;
    @FXML private Button refreshButton;
    @FXML private Button homeButton;
    @FXML private TextField addressBar;
    @FXML private Button bookmarkButton;
    @FXML private MenuButton menuButton;
    @FXML private TabPane tabPane;
    @FXML private Label statusLabel;
    @FXML private Label progressLabel;
    @FXML private ProgressBar progressBar;
    
    // Core components
    private TabManager tabManager;
    private BookmarkManager bookmarkManager;
    private HistoryManager historyManager;
    private DownloadManager downloadManager;
    private String homeUrl = "https://www.google.com";
    private Tab newTabPlusTab; // Special "+" tab
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComponents();
        setupEventHandlers();
        createInitialTab();
    }
    
    /**
     * Initialize core components and managers
     */
    private void initializeComponents() {
        tabManager = new TabManager(this);
        bookmarkManager = new BookmarkManager();
        historyManager = new HistoryManager();
        downloadManager = new DownloadManager();
        
        // Set initial button states
        backButton.setDisable(true);
        forwardButton.setDisable(true);
        progressBar.setVisible(false);
        
        // Setup tab pane
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        
        // Create special "+" tab
        createPlusTab();
    }
    
    /**
     * Setup event handlers for UI components
     */
    private void setupEventHandlers() {
        // Address bar handler
        addressBar.setOnAction(e -> onNavigate());
        
        // Tab selection change handler
        tabPane.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldTab, newTab) -> onTabChanged(oldTab, newTab)
        );
    }
    
    /**
     * Create special "+" tab for adding new tabs
     */
    private void createPlusTab() {
        newTabPlusTab = new Tab("+");
        newTabPlusTab.setClosable(false);
        newTabPlusTab.setTooltip(new Tooltip("Click to open new tab (Ctrl+T)"));
        
        // Add empty content (just a placeholder)
        Label placeholder = new Label("Click the + tab to create a new tab");
        placeholder.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        javafx.scene.layout.VBox placeholderBox = new javafx.scene.layout.VBox();
        placeholderBox.getChildren().add(placeholder);
        placeholderBox.setAlignment(javafx.geometry.Pos.CENTER);
        placeholderBox.setStyle("-fx-background-color: #f8f8f8;");
        newTabPlusTab.setContent(placeholderBox);
        
        tabPane.getTabs().add(newTabPlusTab);
    }
    
    /**
     * Create the initial tab when browser starts
     */
    private void createInitialTab() {
        onNewTab();
        navigateToUrl(homeUrl);
    }
    
    // Navigation Actions
    
    @FXML
    private void onBack() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null && currentTab.canGoBack()) {
            currentTab.goBack();
        }
    }
    
    @FXML
    private void onForward() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null && currentTab.canGoForward()) {
            currentTab.goForward();
        }
    }
    
    @FXML
    private void onRefresh() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null) {
            currentTab.refresh();
        }
    }
    
    @FXML
    private void onHome() {
        navigateToUrl(homeUrl);
    }
    
    @FXML
    private void onNavigate() {
        String url = addressBar.getText().trim();
        if (!url.isEmpty()) {
            navigateToUrl(url);
        }
    }
    
    // Tab Management Actions
    
    @FXML
    private void onNewTab() {
        BrowserTab newBrowserTab = tabManager.createNewTab();
        Tab tab = new Tab("New Tab");
        tab.setContent(newBrowserTab.getContent());
        tab.setUserData(newBrowserTab);
        
        // Add close handler for this specific tab
        tab.setOnCloseRequest(event -> {
            onCloseTab(tab);
        });
        
        // Insert the new tab before the "+" tab
        int insertIndex = tabPane.getTabs().size() - 1; // Before the "+" tab
        tabPane.getTabs().add(insertIndex, tab);
        tabPane.getSelectionModel().select(tab);
        
        // Focus address bar for new tab
        Platform.runLater(() -> addressBar.requestFocus());
    }
    
    private void onCloseTab(Tab tab) {
        // Don't close the "+" tab
        if (tab == newTabPlusTab) {
            return;
        }
        
        BrowserTab browserTab = (BrowserTab) tab.getUserData();
        if (browserTab != null) {
            tabManager.closeTab(browserTab);
        }
        
        // Count actual browser tabs (excluding the "+" tab)
        long browserTabCount = tabPane.getTabs().stream()
            .filter(t -> t != newTabPlusTab)
            .count();
            
        // If no browser tabs left, create a new one
        if (browserTabCount == 0) {
            onNewTab();
        }
    }
    
    private void onTabChanged(Tab oldTab, Tab newTab) {
        if (newTab != null) {
            // If user clicked on the "+" tab, create a new tab
            if (newTab == newTabPlusTab) {
                Platform.runLater(() -> {
                    onNewTab();
                });
                return;
            }
            
            BrowserTab browserTab = (BrowserTab) newTab.getUserData();
            if (browserTab != null) {
                updateUIForTab(browserTab);
            }
        }
    }
    
    // Menu Actions
    
    @FXML
    private void onNewWindow() {
        // New window creation placeholder - currently shows status message
        statusLabel.setText("New window feature coming soon...");
    }
    
    @FXML
    private void onExit() {
        // Clean shutdown
        tabManager.closeAllTabs();
        Platform.exit();
    }
    
    @FXML
    private void onCopy() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null) {
            // Execute JavaScript to get selected text
            Object selectedText = currentTab.executeScript("window.getSelection().toString()");
            if (selectedText != null && !selectedText.toString().trim().isEmpty()) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedText.toString());
                clipboard.setContent(content);
                updateStatus("Text copied to clipboard");
            } else {
                updateStatus("No text selected");
            }
        }
    }
    
    @FXML
    private void onPaste() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            if (clipboard.hasString()) {
                String text = clipboard.getString();
                // Focus on address bar and paste there if it's focused, otherwise try to paste in page
                if (addressBar.isFocused()) {
                    addressBar.paste();
                    updateStatus("Text pasted");
                } else {
                    // Try to paste in the currently focused element on the page
                    String jsCode = String.format(
                        "var activeElement = document.activeElement; " +
                        "if (activeElement && (activeElement.tagName === 'INPUT' || activeElement.tagName === 'TEXTAREA')) {" +
                        "  activeElement.value += '%s';" +
                        "} else {" +
                        "  document.execCommand('insertText', false, '%s');" +
                        "}", text.replace("'", "\\'"), text.replace("'", "\\'"));
                    currentTab.executeScript(jsCode);
                    updateStatus("Text pasted to page");
                }
            } else {
                updateStatus("Clipboard is empty");
            }
        }
    }
    
    @FXML
    private void onFind() {
        // Create a simple find dialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find on page");
        dialog.setHeaderText("Search for text on current page");
        dialog.setContentText("Enter search text:");
        
        dialog.showAndWait().ifPresent(searchText -> {
            if (!searchText.trim().isEmpty()) {
                BrowserTab currentTab = getCurrentBrowserTab();
                if (currentTab != null) {
                    // Use JavaScript to highlight search results
                    String jsCode = String.format(
                        "window.find('%s', false, false, true, false, true, false);",
                        searchText.replace("'", "\\'"));
                    currentTab.executeScript(jsCode);
                    updateStatus("Searching for: " + searchText);
                }
            }
        });
    }
    
    @FXML
    private void onReload() {
        onRefresh();
    }
    
    @FXML
    private void onDevTools() {
        // Developer tools placeholder - currently shows status message
        statusLabel.setText("Developer tools coming soon...");
    }
    
    @FXML
    private void onAddBookmark() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null) {
            String url = currentTab.getCurrentUrl();
            String title = currentTab.getTitle();
            
            if (url != null && !url.isEmpty()) {
                // Create dialog for bookmark details
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Add Bookmark");
                dialog.setHeaderText("Add current page to bookmarks");
                
                // Create form
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
                
                TextField titleField = new TextField(title);
                TextField urlField = new TextField(url);
                urlField.setEditable(false);
                
                grid.add(new Label("Title:"), 0, 0);
                grid.add(titleField, 1, 0);
                grid.add(new Label("URL:"), 0, 1);
                grid.add(urlField, 1, 1);
                
                dialog.getDialogPane().setContent(grid);
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                
                dialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        String bookmarkTitle = titleField.getText().trim();
                        if (bookmarkTitle.isEmpty()) {
                            bookmarkTitle = url;
                        }
                        
                        if (bookmarkManager.addBookmark(bookmarkTitle, url)) {
                            updateStatus("Bookmark added: " + bookmarkTitle);
                            updateBookmarkButton();
                        } else {
                            updateStatus("Failed to add bookmark");
                        }
                    }
                });
            } else {
                updateStatus("No page to bookmark");
            }
        }
    }
    
    @FXML
    private void onShowBookmarks() {
        // Create bookmarks window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Bookmarks Manager");
        dialog.setHeaderText("Your Bookmarks (" + bookmarkManager.getBookmarkCount() + " total)");
        
        // Create main layout
        javafx.scene.layout.VBox mainBox = new javafx.scene.layout.VBox(10);
        mainBox.setPadding(new javafx.geometry.Insets(10));
        
        // Search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search bookmarks...");
        searchField.setPrefWidth(500);
        
        // Bookmarks list
        ListView<Bookmark> bookmarkList = new ListView<>();
        bookmarkList.setPrefSize(500, 300);
        
        // Buttons
        javafx.scene.layout.HBox buttonBox = new javafx.scene.layout.HBox(10);
        Button navigateBtn = new Button("Open");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        Button closeBtn = new Button("Close");
        
        navigateBtn.setDisable(true);
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);
        
        buttonBox.getChildren().addAll(navigateBtn, editBtn, deleteBtn, closeBtn);
        
        // Load bookmarks
        Runnable loadBookmarks = () -> {
            bookmarkList.getItems().clear();
            String searchText = searchField.getText().trim();
            if (searchText.isEmpty()) {
                bookmarkList.getItems().addAll(bookmarkManager.getAllBookmarks());
            } else {
                bookmarkList.getItems().addAll(bookmarkManager.searchBookmarks(searchText));
            }
        };
        
        loadBookmarks.run();
        
        // Search handler
        searchField.textProperty().addListener((obs, old, newVal) -> loadBookmarks.run());
        
        // Selection handler
        bookmarkList.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            boolean hasSelection = newVal != null;
            navigateBtn.setDisable(!hasSelection);
            editBtn.setDisable(!hasSelection);
            deleteBtn.setDisable(!hasSelection);
        });
        
        // Button handlers
        navigateBtn.setOnAction(e -> {
            Bookmark selected = bookmarkList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                navigateToUrl(selected.getUrl());
                dialog.close();
            }
        });
        
        editBtn.setOnAction(e -> {
            Bookmark selected = bookmarkList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                editBookmark(selected, loadBookmarks);
            }
        });
        
        deleteBtn.setOnAction(e -> {
            Bookmark selected = bookmarkList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Delete Bookmark");
                confirmAlert.setHeaderText("Delete bookmark: " + selected.getTitle());
                confirmAlert.setContentText("Are you sure you want to delete this bookmark?");
                
                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        bookmarkManager.removeBookmark(selected.getUrl());
                        loadBookmarks.run();
                        updateStatus("Bookmark deleted: " + selected.getTitle());
                        updateBookmarkButton();
                        dialog.setHeaderText("Your Bookmarks (" + bookmarkManager.getBookmarkCount() + " total)");
                    }
                });
            }
        });
        
        closeBtn.setOnAction(e -> dialog.close());
        
        // Double-click to navigate
        bookmarkList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Bookmark selected = bookmarkList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    navigateToUrl(selected.getUrl());
                    dialog.close();
                }
            }
        });
        
        // Assemble layout
        mainBox.getChildren().addAll(searchField, bookmarkList, buttonBox);
        dialog.getDialogPane().setContent(mainBox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
    
    /**
     * Edit bookmark dialog
     */
    private void editBookmark(Bookmark bookmark, Runnable refreshCallback) {
        Dialog<ButtonType> editDialog = new Dialog<>();
        editDialog.setTitle("Edit Bookmark");
        editDialog.setHeaderText("Edit bookmark details");
        
        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        TextField titleField = new TextField(bookmark.getTitle());
        TextField urlField = new TextField(bookmark.getUrl());
        ComboBox<String> folderCombo = new ComboBox<>();
        folderCombo.getItems().addAll(bookmarkManager.getAllFolders());
        folderCombo.setValue(bookmark.getFolder());
        folderCombo.setEditable(true); // Allow creating new folders
        
        titleField.setPrefWidth(300);
        urlField.setPrefWidth(300);
        folderCombo.setPrefWidth(300);
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("URL:"), 0, 1);
        grid.add(urlField, 1, 1);
        grid.add(new Label("Folder:"), 0, 2);
        grid.add(folderCombo, 1, 2);
        
        editDialog.getDialogPane().setContent(grid);
        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        editDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String newTitle = titleField.getText().trim();
                String newUrl = urlField.getText().trim();
                String newFolder = folderCombo.getValue();
                
                if (!newTitle.isEmpty() && !newUrl.isEmpty() && newFolder != null) {
                    // Remove old bookmark
                    bookmarkManager.removeBookmark(bookmark.getUrl());
                    
                    // Add updated bookmark
                    if (bookmarkManager.addBookmark(newTitle, newUrl, newFolder)) {
                        updateStatus("Bookmark updated: " + newTitle);
                        refreshCallback.run();
                        updateBookmarkButton();
                    } else {
                        updateStatus("Failed to update bookmark");
                        // Re-add original bookmark if update failed
                        bookmarkManager.addBookmark(bookmark.getTitle(), bookmark.getUrl(), bookmark.getFolder());
                    }
                } else {
                    updateStatus("Please fill in all fields");
                }
            }
        });
    }
    
    @FXML
    private void onToggleBookmark() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null) {
            String url = currentTab.getCurrentUrl();
            if (url != null && !url.isEmpty()) {
                if (bookmarkManager.isBookmarked(url)) {
                    bookmarkManager.removeBookmark(url);
                    updateStatus("Bookmark removed");
                } else {
                    String title = currentTab.getTitle();
                    bookmarkManager.addBookmark(title != null ? title : url, url);
                    updateStatus("Bookmark added");
                }
                updateBookmarkButton();
            }
        }
    }
    
    @FXML
    private void onShowHistory() {
        // Create history window
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("History");
        alert.setHeaderText("Browsing History (" + historyManager.getHistoryCount() + " entries)");
        
        ListView<String> historyList = new ListView<>();
        historyManager.getRecentHistory(50).forEach(entry -> 
            historyList.getItems().add(entry.getTitle() + " - " + entry.getUrl() + 
                " (visited " + entry.getVisitCount() + " times)"));
        
        if (historyList.getItems().isEmpty()) {
            historyList.getItems().add("No history yet. Start browsing!");
        }
        
        historyList.setPrefSize(600, 400);
        
        // Handle double-click to navigate
        historyList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = historyList.getSelectionModel().getSelectedItem();
                if (selected != null && !selected.startsWith("No history")) {
                    String url = selected.split(" - ")[1].split(" \\(visited")[0];
                    navigateToUrl(url);
                    alert.close();
                }
            }
        });
        
        alert.getDialogPane().setContent(historyList);
        alert.showAndWait();
    }
    
    @FXML
    private void onShowDownloads() {
        // Create downloads window
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Downloads");
        alert.setHeaderText("Download Manager (" + downloadManager.getDownloadCount() + " downloads)");
        
        ListView<String> downloadList = new ListView<>();
        downloadManager.getAllDownloads().forEach(download -> 
            downloadList.getItems().add(download.toString()));
        
        if (downloadList.getItems().isEmpty()) {
            downloadList.getItems().add("No downloads yet.");
            // Add a sample download for demonstration
            String sampleId = downloadManager.startDownload("https://example.com/sample.pdf", "sample.pdf");
            downloadList.getItems().add("Sample download added: " + downloadManager.getDownload(sampleId).toString());
        }
        
        downloadList.setPrefSize(500, 300);
        alert.getDialogPane().setContent(downloadList);
        alert.showAndWait();
    }
    
    @FXML
    private void onShowSettings() {
        // Create settings window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.setHeaderText("Anima Browser Settings");
        
        // Create settings form
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        // Home page setting
        TextField homePageField = new TextField(homeUrl);
        homePageField.setPrefWidth(300);
        grid.add(new Label("Home Page:"), 0, 0);
        grid.add(homePageField, 1, 0);
        
        // Download location
        TextField downloadPathField = new TextField(downloadManager.getDefaultDownloadPath());
        downloadPathField.setPrefWidth(300);
        grid.add(new Label("Download Location:"), 0, 1);
        grid.add(downloadPathField, 1, 1);
        
        // Statistics
        Label statsLabel = new Label(String.format(
            "Statistics:\n• History entries: %d\n• Bookmarks: %d\n• Downloads: %d\n• Active tabs: %d",
            historyManager.getHistoryCount(),
            bookmarkManager.getBookmarkCount(),
            downloadManager.getDownloadCount(),
            tabManager.getTabCount()
        ));
        grid.add(statsLabel, 0, 2, 2, 1);
        
        // Clear data buttons
        Button clearHistoryBtn = new Button("Clear History");
        clearHistoryBtn.setOnAction(e -> {
            historyManager.clearHistory();
            updateStatus("History cleared");
        });
        
        Button clearBookmarksBtn = new Button("Clear Bookmarks");
        clearBookmarksBtn.setOnAction(e -> {
            bookmarkManager.clearAllBookmarks();
            updateStatus("Bookmarks cleared");
            updateBookmarkButton();
        });
        
        javafx.scene.layout.HBox buttonBox = new javafx.scene.layout.HBox(10);
        buttonBox.getChildren().addAll(clearHistoryBtn, clearBookmarksBtn);
        grid.add(buttonBox, 0, 3, 2, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Apply settings
                String newHomePage = homePageField.getText().trim();
                if (!newHomePage.isEmpty()) {
                    homeUrl = newHomePage;
                    updateStatus("Settings saved");
                }
                
                String newDownloadPath = downloadPathField.getText().trim();
                if (!newDownloadPath.isEmpty()) {
                    downloadManager.setDefaultDownloadPath(newDownloadPath);
                }
            }
        });
    }
    
    // Helper Methods
    
    /**
     * Navigate to specified URL
     */
    public void navigateToUrl(String url) {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null) {
            // Add protocol if missing
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("file://")) {
                if (url.contains(".") && !url.contains(" ")) {
                    url = "https://" + url;
                } else {
                    // Treat as search query
                    url = "https://www.google.com/search?q=" + url.replace(" ", "+");
                }
            }
            
            currentTab.navigate(url);
            addressBar.setText(url);
            
            // Add to history
            historyManager.addToHistory(currentTab.getTitle(), url);
        }
    }
    
    /**
     * Update bookmark button appearance based on current page
     */
    private void updateBookmarkButton() {
        BrowserTab currentTab = getCurrentBrowserTab();
        if (currentTab != null && bookmarkButton != null) {
            String url = currentTab.getCurrentUrl();
            if (url != null && bookmarkManager.isBookmarked(url)) {
                bookmarkButton.setText("★"); // Filled star
                bookmarkButton.setTooltip(new Tooltip("Remove bookmark"));
            } else {
                bookmarkButton.setText("☆"); // Empty star
                bookmarkButton.setTooltip(new Tooltip("Add bookmark"));
            }
        }
    }
    
    /**
     * Get currently active browser tab
     */
    private BrowserTab getCurrentBrowserTab() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            return (BrowserTab) selectedTab.getUserData();
        }
        return null;
    }
    
    /**
     * Update UI elements based on current tab state
     */
    public void updateUIForTab(BrowserTab browserTab) {
        if (browserTab != null) {
            // Update navigation buttons
            backButton.setDisable(!browserTab.canGoBack());
            forwardButton.setDisable(!browserTab.canGoForward());
            
            // Update address bar
            String currentUrl = browserTab.getCurrentUrl();
            if (currentUrl != null && !currentUrl.isEmpty()) {
                addressBar.setText(currentUrl);
            }
            
            // Update tab title
            Tab tab = getTabForBrowserTab(browserTab);
            if (tab != null) {
                String title = browserTab.getTitle();
                tab.setText(title != null && !title.isEmpty() ? title : "Loading...");
            }
            
            // Update bookmark button
            updateBookmarkButton();
        }
    }
    
    /**
     * Find Tab object for given BrowserTab
     */
    private Tab getTabForBrowserTab(BrowserTab browserTab) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getUserData() == browserTab) {
                return tab;
            }
        }
        return null;
    }
    
    /**
     * Update loading progress
     */
    public void updateProgress(double progress) {
        Platform.runLater(() -> {
            if (progress < 0 || progress >= 1.0) {
                progressBar.setVisible(false);
                progressLabel.setText("");
            } else {
                progressBar.setVisible(true);
                progressBar.setProgress(progress);
                progressLabel.setText(String.format("Loading... %.0f%%", progress * 100));
            }
        });
    }
    
    /**
     * Update status message
     */
    public void updateStatus(String message) {
        Platform.runLater(() -> statusLabel.setText(message));
    }
}
