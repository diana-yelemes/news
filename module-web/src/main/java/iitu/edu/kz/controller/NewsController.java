// module-web/src/main/java/iitu/edu/kz/controller/NewsController.java
package iitu.edu.kz.controller;

import iitu.edu.kz.dto.ApiResponse;
import iitu.edu.kz.dto.NewsDTO;
import iitu.edu.kz.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private NewsService newsService = new NewsService();

    // Create News
    @PostMapping
    public ResponseEntity<ApiResponse<NewsDTO>> createNews(@RequestBody NewsDTO newsDTO) {
        NewsDTO createdNews = newsService.createNews(newsDTO);
        return new ResponseEntity<>(new ApiResponse<>(createdNews, "News created successfully."), HttpStatus.CREATED);
    }

    // Get All News
    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsDTO>>> getAllNews() {
        List<NewsDTO> newsList = newsService.getAllNews();
        return new ResponseEntity<>(new ApiResponse<>(newsList, "List of all news fetched successfully."), HttpStatus.OK);
    }

    // Get News by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsDTO>> getNewsById(@PathVariable Long id) {
        NewsDTO news = newsService.getNewsById(id);
        return new ResponseEntity<>(new ApiResponse<>(news, "News fetched successfully."), HttpStatus.OK);
    }

    // Update News
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsDTO>> updateNews(@PathVariable Long id, @RequestBody NewsDTO newsDTO) {
        NewsDTO updatedNews = newsService.updateNews(id, newsDTO);
        return new ResponseEntity<>(new ApiResponse<>(updatedNews, "News updated successfully."), HttpStatus.OK);
    }

    // Delete News
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteNews(@PathVariable Long id) {
        boolean isDeleted = newsService.deleteNews(id);
        return new ResponseEntity<>(new ApiResponse<>(isDeleted, "News deleted successfully."), HttpStatus.OK);
    }
}
