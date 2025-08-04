package it.r2u.animar2u.core.storage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages browser downloads
 */
public class DownloadManager {
    private Map<String, DownloadItem> downloads;
    private String defaultDownloadPath;
    
    public DownloadManager() {
        this.downloads = new ConcurrentHashMap<>();
        this.defaultDownloadPath = System.getProperty("user.home") + File.separator + "Downloads";
        
        // Create downloads directory if it doesn't exist
        File downloadDir = new File(defaultDownloadPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
    }
    
    /**
     * Start a download
     */
    public String startDownload(String url, String fileName) {
        return startDownload(url, fileName, defaultDownloadPath);
    }
    
    /**
     * Start a download to specific path
     */
    public String startDownload(String url, String fileName, String downloadPath) {
        String downloadId = UUID.randomUUID().toString();
        String fullPath = downloadPath + File.separator + fileName;
        
        DownloadItem item = new DownloadItem(fileName, url, fullPath);
        item.setStatus(DownloadItem.DownloadStatus.PENDING);
        
        downloads.put(downloadId, item);
        
        // Start download simulation (placeholder implementation)
        simulateDownload(downloadId);
        
        return downloadId;
    }
    
    /**
     * Simulate download for demo purposes
     */
    private void simulateDownload(String downloadId) {
        new Thread(() -> {
            DownloadItem item = downloads.get(downloadId);
            if (item != null) {
                item.setStatus(DownloadItem.DownloadStatus.IN_PROGRESS);
                item.setFileSize(1024 * 1024); // 1MB fake size
                
                // Simulate progress
                for (int i = 0; i <= 100; i += 10) {
                    try {
                        Thread.sleep(100); // Simulate download time
                        item.setDownloadedSize((long) (item.getFileSize() * i / 100.0));
                        
                        if (i == 100) {
                            item.setStatus(DownloadItem.DownloadStatus.COMPLETED);
                            item.setEndTime(LocalDateTime.now());
                        }
                    } catch (InterruptedException e) {
                        item.setStatus(DownloadItem.DownloadStatus.FAILED);
                        break;
                    }
                }
            }
        }).start();
    }
    
    /**
     * Cancel a download
     */
    public boolean cancelDownload(String downloadId) {
        DownloadItem item = downloads.get(downloadId);
        if (item != null && item.getStatus() == DownloadItem.DownloadStatus.IN_PROGRESS) {
            item.setStatus(DownloadItem.DownloadStatus.CANCELLED);
            return true;
        }
        return false;
    }
    
    /**
     * Remove a download from list
     */
    public boolean removeDownload(String downloadId) {
        return downloads.remove(downloadId) != null;
    }
    
    /**
     * Get all downloads
     */
    public List<DownloadItem> getAllDownloads() {
        return new ArrayList<>(downloads.values());
    }
    
    /**
     * Get active downloads
     */
    public List<DownloadItem> getActiveDownloads() {
        return downloads.values().stream()
                .filter(item -> item.getStatus() == DownloadItem.DownloadStatus.IN_PROGRESS ||
                               item.getStatus() == DownloadItem.DownloadStatus.PENDING)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get completed downloads
     */
    public List<DownloadItem> getCompletedDownloads() {
        return downloads.values().stream()
                .filter(item -> item.getStatus() == DownloadItem.DownloadStatus.COMPLETED)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get download by ID
     */
    public DownloadItem getDownload(String downloadId) {
        return downloads.get(downloadId);
    }
    
    /**
     * Clear completed downloads
     */
    public void clearCompletedDownloads() {
        downloads.entrySet().removeIf(entry -> 
            entry.getValue().getStatus() == DownloadItem.DownloadStatus.COMPLETED);
    }
    
    /**
     * Get default download path
     */
    public String getDefaultDownloadPath() {
        return defaultDownloadPath;
    }
    
    /**
     * Set default download path
     */
    public void setDefaultDownloadPath(String path) {
        this.defaultDownloadPath = path;
        
        // Create directory if it doesn't exist
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Get download count
     */
    public int getDownloadCount() {
        return downloads.size();
    }
    
    /**
     * Get active download count
     */
    public int getActiveDownloadCount() {
        return (int) downloads.values().stream()
                .filter(item -> item.getStatus() == DownloadItem.DownloadStatus.IN_PROGRESS ||
                               item.getStatus() == DownloadItem.DownloadStatus.PENDING)
                .count();
    }
}
