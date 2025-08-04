package it.r2u.animar2u.core.storage;

import java.time.LocalDateTime;

/**
 * Represents a download item
 */
public class DownloadItem {
    private String fileName;
    private String url;
    private String filePath;
    private long fileSize;
    private long downloadedSize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DownloadStatus status;
    
    public enum DownloadStatus {
        PENDING, IN_PROGRESS, COMPLETED, FAILED, CANCELLED
    }
    
    public DownloadItem(String fileName, String url, String filePath) {
        this.fileName = fileName;
        this.url = url;
        this.filePath = filePath;
        this.startTime = LocalDateTime.now();
        this.status = DownloadStatus.PENDING;
        this.downloadedSize = 0;
        this.fileSize = 0;
    }
    
    // Getters and setters
    public String getFileName() { return fileName; }
    public String getUrl() { return url; }
    public String getFilePath() { return filePath; }
    public long getFileSize() { return fileSize; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public long getDownloadedSize() { return downloadedSize; }
    public void setDownloadedSize(long downloadedSize) { this.downloadedSize = downloadedSize; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public DownloadStatus getStatus() { return status; }
    public void setStatus(DownloadStatus status) { this.status = status; }
    
    public double getProgress() {
        if (fileSize == 0) return 0;
        return (double) downloadedSize / fileSize;
    }
    
    @Override
    public String toString() {
        return fileName + " - " + status + " (" + String.format("%.1f%%", getProgress() * 100) + ")";
    }
}
