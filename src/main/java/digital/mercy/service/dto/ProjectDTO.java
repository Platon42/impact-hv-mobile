package digital.mercy.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    private String user;

    private String description;

    private Long requiredAmt;

    private String projectName;

    private String currency;

    private String country;

    private String region;

    private String author;

    private String imgRef;


    private Long projectNameId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequiredAmt() {
        return requiredAmt;
    }

    public void setRequiredAmt(Long requiredAmt) {
        this.requiredAmt = requiredAmt;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgRef() {
        return imgRef;
    }

    public void setImgRef(String imgRef) {
        this.imgRef = imgRef;
    }

    public Long getProjectNameId() {
        return projectNameId;
    }

    public void setProjectNameId(Long favoriteProjectId) {
        this.projectNameId = favoriteProjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (projectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", description='" + getDescription() + "'" +
            ", requiredAmt=" + getRequiredAmt() +
            ", projectName='" + getProjectName() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", country='" + getCountry() + "'" +
            ", region='" + getRegion() + "'" +
            ", author='" + getAuthor() + "'" +
            ", imgRef='" + getImgRef() + "'" +
            ", projectName=" + getProjectNameId() +
            "}";
    }
}
