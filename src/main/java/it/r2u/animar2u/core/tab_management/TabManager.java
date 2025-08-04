package it.r2u.animar2u.core.tab_management;

import it.r2u.animar2u.ui.controllers.MainBrowserController;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages browser tabs lifecycle and operations.
 * Handles creation, closure, and coordination of BrowserTab instances.
 */
public class TabManager {
    
    private MainBrowserController controller;
    private List<BrowserTab> tabs;
    
    /**
     * Creates a new TabManager
     * @param controller The main browser controller
     */
    public TabManager(MainBrowserController controller) {
        this.controller = controller;
        this.tabs = new ArrayList<>();
    }
    
    /**
     * Create a new browser tab
     * @return newly created BrowserTab
     */
    public BrowserTab createNewTab() {
        BrowserTab newTab = new BrowserTab(controller);
        tabs.add(newTab);
        
        return newTab;
    }
    
    /**
     * Close a specific tab
     * @param tab The tab to close
     */
    public void closeTab(BrowserTab tab) {
        if (tab != null && tabs.contains(tab)) {
            tab.dispose();
            tabs.remove(tab);
        }
    }
    
    /**
     * Close all tabs
     */
    public void closeAllTabs() {
        for (BrowserTab tab : tabs) {
            tab.dispose();
        }
        tabs.clear();
    }
    
    /**
     * Get all active tabs
     * @return list of active tabs
     */
    public List<BrowserTab> getAllTabs() {
        return new ArrayList<>(tabs);
    }
    
    /**
     * Get number of active tabs
     * @return tab count
     */
    public int getTabCount() {
        return tabs.size();
    }
    
    /**
     * Find tab by index
     * @param index tab index
     * @return BrowserTab at specified index, or null if not found
     */
    public BrowserTab getTabByIndex(int index) {
        if (index >= 0 && index < tabs.size()) {
            return tabs.get(index);
        }
        return null;
    }
}
