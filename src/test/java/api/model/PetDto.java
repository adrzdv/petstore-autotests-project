package api.model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDto {
    private Long id;
    private CategoryDto category;
    private String name;
    private List<String> photoUrls;
    private List<TagDto> tags;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetDto petDto)) return false;
        return Objects.equals(id, petDto.id)
                && Objects.equals(category, petDto.category)
                && Objects.equals(name, petDto.name)
                && Objects.equals(photoUrls, petDto.photoUrls)
                && Objects.equals(tags, petDto.tags)
                && Objects.equals(status, petDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, photoUrls, tags, status);
    }

    @Override
    public String toString() {
        return "PetDto{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }
}
