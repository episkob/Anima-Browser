package it.r2u.animar2u.core.storage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Manages browser bookmarks
 */
public class BookmarkManager {
    private Map<String, Bookmark> bookmarks;
    private Set<String> folders;
    
    public BookmarkManager() {
        this.bookmarks = new ConcurrentHashMap<>();
        this.folders = new HashSet<>();
        this.folders.add("Default");
        this.folders.add("Bookmarks Bar");
        this.folders.add("Other Bookmarks");
    }
    
    /**
     * Add a bookmark
     */
    public boolean addBookmark(String title, String url) {
        return addBookmark(title, url, "Default");
    }
    
    /**
     * Add a bookmark to specific folder
     */
    public boolean addBookmark(String title, String url, String folder) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        folders.add(folder);
        Bookmark bookmark = new Bookmark(title, url, folder);
        bookmarks.put(url, bookmark);
        return true;
    }
    
    /**
     * Remove a bookmark
     */
    public boolean removeBookmark(String url) {
        return bookmarks.remove(url) != null;
    }
    
    /**
     * Check if URL is bookmarked
     */
    public boolean isBookmarked(String url) {
        return bookmarks.containsKey(url);
    }
    
    /**
     * Get bookmark by URL
     */
    public Bookmark getBookmark(String url) {
        return bookmarks.get(url);
    }
    
    /**
     * Get all bookmarks
     */
    public List<Bookmark> getAllBookmarks() {
        return new ArrayList<>(bookmarks.values());
    }
    
    /**
     * Get bookmarks by folder
     */
    public List<Bookmark> getBookmarksByFolder(String folder) {
        return bookmarks.values().stream()
                .filter(bookmark -> bookmark.getFolder().equals(folder))
                .collect(Collectors.toList());
    }
    
    /**
     * Get all folders
     */
    public Set<String> getAllFolders() {
        return new HashSet<>(folders);
    }
    
    /**
     * Create new folder
     */
    public void createFolder(String folderName) {
        folders.add(folderName);
    }
    
    /**
     * Search bookmarks by title or URL
     */
    public List<Bookmark> searchBookmarks(String query) {
        String lowerQuery = query.toLowerCase();
        return bookmarks.values().stream()
                .filter(bookmark -> 
                    bookmark.getTitle().toLowerCase().contains(lowerQuery) ||
                    bookmark.getUrl().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
    
    /**
     * Get bookmark count
     */
    public int getBookmarkCount() {
        return bookmarks.size();
    }
    
    /**
     * Clear all bookmarks
     */
    public void clearAllBookmarks() {
        bookmarks.clear();
    }
}
