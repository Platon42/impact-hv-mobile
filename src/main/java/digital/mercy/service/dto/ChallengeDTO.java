package digital.mercy.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Challenge entity.
 */
public class ChallengeDTO implements Serializable {

    private Long id;

    private String project;

    private String measure;

    private Long requiredQuantity;

    private String challengeDesc;


    private Long measureId;

    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Long getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getChallengeDesc() {
        return challengeDesc;
    }

    public void setChallengeDesc(String challengeDesc) {
        this.challengeDesc = challengeDesc;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChallengeDTO challengeDTO = (ChallengeDTO) o;
        if (challengeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), challengeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
            "id=" + getId() +
            ", project='" + getProject() + "'" +
            ", measure='" + getMeasure() + "'" +
            ", requiredQuantity=" + getRequiredQuantity() +
            ", challengeDesc='" + getChallengeDesc() + "'" +
            ", measure=" + getMeasureId() +
            ", project=" + getProjectId() +
            "}";
    }
}
