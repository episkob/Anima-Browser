package it.r2u.animar2u.core.navigation;

import it.r2u.animar2u.core.storage.HistoryEntry;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Manages browser history
 */
public class HistoryManager {
    private Map<String, HistoryEntry> history;
    private List<HistoryEntry> chronologicalHistory;
    private int maxHistorySize = 10000;
    
    public HistoryManager() {
        this.history = new ConcurrentHashMap<>();
        this.chronologicalHistory = Collections.synchronizedList(new ArrayList<>());
    }
    
    /**
     * Add a page to history
     */
    public void addToHistory(String title, String url) {
        if (url == null || url.trim().isEmpty()) {
            return;
        }
        
        // Clean title
        if (title == null || title.trim().isEmpty()) {
            title = url;
        }
        
        HistoryEntry existingEntry = history.get(url);
        if (existingEntry != null) {
            // Update existing entry
            existingEntry.setTitle(title);
            existingEntry.incrementVisitCount();
            
            // Move to end of chronological history
            chronologicalHistory.remove(existingEntry);
            chronologicalHistory.add(existingEntry);
        } else {
            // Create new entry
            HistoryEntry newEntry = new HistoryEntry(title, url);
            history.put(url, newEntry);
            chronologicalHistory.add(newEntry);
            
            // Maintain size limit
            if (chronologicalHistory.size() > maxHistorySize) {
                HistoryEntry removed = chronologicalHistory.remove(0);
                history.remove(removed.getUrl());
            }
        }
    }
    
    /**
     * Get recent history entries
     */
    public List<HistoryEntry> getRecentHistory(int limit) {
        return chronologicalHistory.stream()
                .sorted((a, b) -> b.getVisitTime().compareTo(a.getVisitTime()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all history entries
     */
    public List<HistoryEntry> getAllHistory() {
        return new ArrayList<>(chronologicalHistory);
    }
    
    /**
     * Search history
     */
    public List<HistoryEntry> searchHistory(String query) {
        String lowerQuery = query.toLowerCase();
        return chronologicalHistory.stream()
                .filter(entry -> 
                    entry.getTitle().toLowerCase().contains(lowerQuery) ||
                    entry.getUrl().toLowerCase().contains(lowerQuery))
                .sorted((a, b) -> b.getVisitTime().compareTo(a.getVisitTime()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get history for today
     */
    public List<HistoryEntry> getTodayHistory() {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        return chronologicalHistory.stream()
                .filter(entry -> entry.getVisitTime().isAfter(today))
                .sorted((a, b) -> b.getVisitTime().compareTo(a.getVisitTime()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get most visited pages
     */
    public List<HistoryEntry> getMostVisited(int limit) {
        return chronologicalHistory.stream()
                .sorted((a, b) -> Integer.compare(b.getVisitCount(), a.getVisitCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Remove entry from history
     */
    public boolean removeFromHistory(String url) {
        HistoryEntry entry = history.remove(url);
        if (entry != null) {
            chronologicalHistory.remove(entry);
            return true;
        }
        return false;
    }
    
    /**
     * Clear all history
     */
    public void clearHistory() {
        history.clear();
        chronologicalHistory.clear();
    }
    
    /**
     * Clear history for specific date range
     */
    public void clearHistory(LocalDateTime from, LocalDateTime to) {
        List<HistoryEntry> toRemove = chronologicalHistory.stream()
                .filter(entry -> entry.getVisitTime().isAfter(from) && entry.getVisitTime().isBefore(to))
                .collect(Collectors.toList());
        
        for (HistoryEntry entry : toRemove) {
            history.remove(entry.getUrl());
            chronologicalHistory.remove(entry);
        }
    }
    
    /**
     * Get history count
     */
    public int getHistoryCount() {
        return chronologicalHistory.size();
    }
    
    /**
     * Check if URL is in history
     */
    public boolean isInHistory(String url) {
        return history.containsKey(url);
    }
    
    /**
     * Get visit count for URL
     */
    public int getVisitCount(String url) {
        HistoryEntry entry = history.get(url);
        return entry != null ? entry.getVisitCount() : 0;
    }
}
