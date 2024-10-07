// module-service/src/main/java/iitu/edu/kz/service/NewsService.java
package iitu.edu.kz.service;

import iitu.edu.kz.dto.NewsDTO;
import iitu.edu.kz.model.News;
import iitu.edu.kz.repository.NewsRepository;
import iitu.edu.kz.exception.CustomException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NewsService {
    private NewsRepository newsRepository = new NewsRepository();

    // Create News
    public NewsDTO createNews(NewsDTO newsDTO) {
        validateNewsDTO(newsDTO);
        News news = newsRepository.createNews(
                newsDTO.getTitle(),
                newsDTO.getContent(),
                newsDTO.getAuthorId()
        );
        return mapToDTO(news);
    }

    // Get All News
    public List<NewsDTO> getAllNews() {
        return newsRepository.getAllNews()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get News by ID
    public NewsDTO getNewsById(Long id) {
        Optional<News> newsOpt = newsRepository.getNewsById(id);
        if (newsOpt.isPresent()) {
            return mapToDTO(newsOpt.get());
        } else {
            throw new CustomException("News not found", 404);
        }
    }

    // Update News
    public NewsDTO updateNews(Long id, NewsDTO newsDTO) {
        validateNewsDTO(newsDTO);
        News updatedNews = newsRepository.updateNews(id, newsDTO.getTitle(), newsDTO.getContent(), newsDTO.getAuthorId());
        if (updatedNews != null) {
            return mapToDTO(updatedNews);
        } else {
            throw new CustomException("Unable to update news. News not found.", 404);
        }
    }

    // Delete News
    public boolean deleteNews(Long id) {
        if (!newsRepository.deleteNews(id)) {
            throw new CustomException("Unable to delete news. News not found.", 404);
        }
        return true;
    }

    // Validate NewsDTO fields
    private void validateNewsDTO(NewsDTO newsDTO) {
        if (newsDTO.getTitle() == null || newsDTO.getTitle().length() < 5 || newsDTO.getTitle().length() > 30) {
            throw new CustomException("Title length must be between 5 and 30 characters.", 400);
        }
        if (newsDTO.getContent() == null || newsDTO.getContent().length() < 5 || newsDTO.getContent().length() > 255) {
            throw new CustomException("Content length must be between 5 and 255 characters.", 400);
        }
        if (newsDTO.getAuthorId() == null) {
            throw new CustomException("Author ID cannot be null.", 400);
        }
    }

    // Mapping from Model to DTO
    private NewsDTO mapToDTO(News news) {
        return new NewsDTO(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getAuthorId(),
                news.getCreatedDate(),
                news.getLastUpdatedDate()
        );
    }

    // Mapping from DTO to Model (if needed)
    private News mapToModel(NewsDTO newsDTO) {
        return new News(
                newsDTO.getId(),
                newsDTO.getTitle(),
                newsDTO.getContent(),
                newsDTO.getAuthorId(),
                newsDTO.getCreatedDate(),
                newsDTO.getLastUpdatedDate()
        );
    }
}
