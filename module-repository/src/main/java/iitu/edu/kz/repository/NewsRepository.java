// module-repository/src/main/java/iitu/edu/kz/repository/NewsRepository.java
package iitu.edu.kz.repository;

import iitu.edu.kz.model.News;
import iitu.edu.kz.model.Author;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsRepository {
    private List<News> newsList = new ArrayList<>();
    private List<Author> authorList = new ArrayList<>();
    private Long currentId = 1L;

    // File paths for data sources
    private final String AUTHOR_FILE_PATH = "module-repository/src/main/resources/data/author.txt";
    private final String NEWS_FILE_PATH = "module-repository/src/main/resources/data/news.txt";

    // Initializing with default data (could be loaded from files)
    public NewsRepository() {
        loadDataFromFiles();

        // Setup a file watcher to auto-update data when files change
        FileWatcher fileWatcher = new FileWatcher("module-repository/src/main/resources/data", this::loadDataFromFiles);
        fileWatcher.startWatching();
    }
    // Loads data from files and updates in-memory collections
    private void loadDataFromFiles() {
        try {
            // Load authors from file
            authorList.clear();
            Files.lines(Paths.get(AUTHOR_FILE_PATH)).forEach(line -> {
                String[] parts = line.split(",");
                authorList.add(new Author(Long.parseLong(parts[0]), parts[1]));
            });

            // Load news articles from file
            newsList.clear();
            Files.lines(Paths.get(NEWS_FILE_PATH)).forEach(line -> {
                String[] parts = line.split(",");
                Long id = Long.parseLong(parts[0]);
                String title = parts[1];
                String content = parts[2];
                Long authorId = Long.parseLong(parts[3]);
                newsList.add(new News(id, title, content, authorId, LocalDateTime.now(), LocalDateTime.now()));
            });

            System.out.println("Data loaded from files successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
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