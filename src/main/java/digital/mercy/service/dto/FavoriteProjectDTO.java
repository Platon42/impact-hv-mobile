package digital.mercy.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FavoriteProject entity.
 */
public class FavoriteProjectDTO implements Serializable {

    private Long id;

    private String user;

    private String project;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long memberId) {
        this.userId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FavoriteProjectDTO favoriteProjectDTO = (FavoriteProjectDTO) o;
        if (favoriteProjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favoriteProjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FavoriteProjectDTO{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", project='" + getProject() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
