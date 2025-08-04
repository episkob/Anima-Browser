package it.r2u.animar2u.core.storage;

import java.time.LocalDateTime;

/**
 * Represents a history entry in the browser
 */
public class HistoryEntry {
    private String title;
    private String url;
    private LocalDateTime visitTime;
    private int visitCount;
    
    public HistoryEntry(String title, String url) {
        this.title = title;
        this.url = url;
        this.visitTime = LocalDateTime.now();
        this.visitCount = 1;
    }
    
    public HistoryEntry(String title, String url, LocalDateTime visitTime, int visitCount) {
        this.title = title;
        this.url = url;
        this.visitTime = visitTime;
        this.visitCount = visitCount;
    }
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public LocalDateTime getVisitTime() { return visitTime; }
    public void setVisitTime(LocalDateTime visitTime) { this.visitTime = visitTime; }
    
    public int getVisitCount() { return visitCount; }
    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }
    
    public void incrementVisitCount() {
        this.visitCount++;
        this.visitTime = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return title + " - " + url + " (" + visitCount + " visits)";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HistoryEntry that = (HistoryEntry) obj;
        return url.equals(that.url);
    }
    
    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
