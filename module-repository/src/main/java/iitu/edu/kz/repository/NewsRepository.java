package iitu.edu.kz.repository;

import iitu.edu.kz.model.Author;
import iitu.edu.kz.model.News;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    // Use an external path for news and author files, outside of resources
    private static final String NEWS_FILE_PATH = "/Users/dianayelemes/IdeaProjects/news/data/news.txt";
    private static final String AUTHOR_FILE_PATH = "/Users/dianayelemes/IdeaProjects/news/data/author.txt";

    public NewsRepository() {
        createDataDirectoryIfNotExists();
        loadDataFromFiles();
    }

    // Create the 'data' directory if it doesn't exist
    private void createDataDirectoryIfNotExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Created data directory for storing files.");
            } else {
                System.err.println("Failed to create data directory.");
            }
        }
    }

    // Load data from files located outside of the resources folder
    private void loadDataFromFiles() {
        try {
            // Load authors from external file
            if (Files.exists(Paths.get(AUTHOR_FILE_PATH))) {
                Files.lines(Paths.get(AUTHOR_FILE_PATH)).forEach(line -> {
                    String[] parts = line.split(",");
                    authorList.add(new Author(Long.parseLong(parts[0]), parts[1]));
                });
                System.out.println("Authors loaded successfully from file.");
            } else {
                System.err.println("Author file not found: " + AUTHOR_FILE_PATH);
            }

            // Load news articles from external file
            if (Files.exists(Paths.get(NEWS_FILE_PATH))) {
                Files.lines(Paths.get(NEWS_FILE_PATH)).forEach(line -> {
                    String[] parts = line.split(",");
                    Long id = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String content = parts[2];
                    Long authorId = Long.parseLong(parts[3]);
                    newsList.add(new News(id, title, content, authorId, LocalDateTime.now(), LocalDateTime.now()));
                });
                System.out.println("News loaded successfully from file.");
            } else {
                System.err.println("News file not found: " + NEWS_FILE_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save the current news list to the external news.txt file
    private void saveNewsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NEWS_FILE_PATH))) {
            for (News news : newsList) {
                String newsLine = news.getId() + "," + news.getTitle() + "," + news.getContent() + "," + news.getAuthorId();
                writer.write(newsLine);
                writer.newLine(); // Write each news entry in a new line
            }
            System.out.println("News data saved successfully to file!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CRUD Operations

    public News createNews(String title, String content, Long authorId) {
        News news = new News(currentId++, title, content, authorId, LocalDateTime.now(), LocalDateTime.now());
        newsList.add(news);
        saveNewsToFile(); // Persist the changes to the file
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
            saveNewsToFile(); // Persist the changes to the file
            return news;
        }
        return null;
    }

    public boolean deleteNews(Long id) {
        boolean isRemoved = newsList.removeIf(news -> news.getId().equals(id));
        if (isRemoved) {
            saveNewsToFile(); // Persist the changes to the file
        }
        return isRemoved;
    }
}
