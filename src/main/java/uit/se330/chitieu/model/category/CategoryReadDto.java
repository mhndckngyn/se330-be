package uit.se330.chitieu.model.category;

import uit.se330.chitieu.entity.Category;

import java.util.UUID;

public class CategoryReadDto {
    public UUID id;
    public String name;

    public CategoryReadDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
