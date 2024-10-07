// module-repository/src/main/java/iitu/edu/kz/model/News.java
package iitu.edu.kz.model;

import java.time.LocalDateTime;

public class News {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    // Constructors
    public News(Long id, String title, String content, Long authorId, LocalDateTime createdDate, LocalDateTime lastUpdatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

    @Override
    public String toString() {
        return "News{id=" + id + ", title='" + title + "', content='" + content + "', authorId=" + authorId +
                ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + "}";
    }
}
