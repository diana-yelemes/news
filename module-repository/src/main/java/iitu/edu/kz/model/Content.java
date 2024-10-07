// module-repository/src/main/java/iitu/edu/kz/model/Content.java
package iitu.edu.kz.model;

public class Content {
    private Long id;
    private String description;

    // Constructors
    public Content(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Content{id=" + id + ", description='" + description + "'}";
    }
}
