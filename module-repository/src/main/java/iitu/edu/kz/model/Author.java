// module-repository/src/main/java/iitu/edu/kz/model/Author.java
package iitu.edu.kz.model;

public class Author {
    private Long id;
    private String name;

    // Constructors
    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Author{id=" + id + ", name='" + name + "'}";
    }
}
