package it.r2u.animar2u.core.storage;

import java.time.LocalDateTime;

/**
 * Represents a bookmark entry in the browser
 */
public class Bookmark {
    private String title;
    private String url;
    private LocalDateTime dateAdded;
    private String folder;
    
    public Bookmark(String title, String url) {
        this.title = title;
        this.url = url;
        this.dateAdded = LocalDateTime.now();
        this.folder = "Default";
    }
    
    public Bookmark(String title, String url, String folder) {
        this.title = title;
        this.url = url;
        this.dateAdded = LocalDateTime.now();
        this.folder = folder;
    }
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }
    
    public String getFolder() { return folder; }
    public void setFolder(String folder) { this.folder = folder; }
    
    @Override
    public String toString() {
        return title + " - " + url;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bookmark bookmark = (Bookmark) obj;
        return url.equals(bookmark.url);
    }
    
    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
