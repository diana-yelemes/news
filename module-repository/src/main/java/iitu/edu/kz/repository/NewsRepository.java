// module-repository/src/main/java/iitu/edu/kz/repository/NewsRepository.java
package iitu.edu.kz.repository;

import iitu.edu.kz.model.News;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsRepository {
    private List<News> newsList = new ArrayList<>();
    private Long currentId = 1L;

    // Initializing with default data (could be loaded from files)
    public NewsRepository() {
        // Pre-fill with default data as per specification
        newsList.add(new News(currentId++, "Sample Title 1", "Sample Content 1", 1L, LocalDateTime.now(), LocalDateTime.now()));
        newsList.add(new News(currentId++, "Sample Title 2", "Sample Content 2", 2L, LocalDateTime.now(), LocalDateTime.now()));
    }

    // CRUD Operations
    public News createNews(String title, String content, Long authorId) {
        News news = new News(currentId++, title, content, authorId, LocalDateTime.now(), LocalDateTime.now());
        newsList.add(news);
        return news;
    }

    public List<News> getAllNews() {
        return newsList;
    }

    public Optional<News> getNewsById(Long id) {
        return newsList.stream().filter(news -> news.getId().equals(id)).findFirst();
    }

    public News updateNews(Long id, String title, String content, Long authorId) {
        Optional<News> newsOpt = getNewsById(id);
        if (newsOpt.isPresent()) {
            News news = newsOpt.get();
            news.setTitle(title);
            news.setContent(content);
            news.setAuthorId(authorId);
            news.setLastUpdatedDate(LocalDateTime.now());
            return news;
        }
        return null;
    }

    public boolean deleteNews(Long id) {
        return newsList.removeIf(news -> news.getId().equals(id));
    }
}
