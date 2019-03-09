package digital.mercy.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Challenge.
 */
@Entity
@Table(name = "challenge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "project")
    private String project;

    @Column(name = "measure")
    private String measure;

    @Column(name = "required_quantity")
    private Long requiredQuantity;

    @Column(name = "challenge_desc")
    private String challengeDesc;

    @OneToOne
    @JoinColumn(unique = true)
    private Measure measure;

    @ManyToOne
    @JsonIgnoreProperties("projectNames")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public Challenge project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMeasure() {
        return measure;
    }

    public Challenge measure(String measure) {
        this.measure = measure;
        return this;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Long getRequiredQuantity() {
        return requiredQuantity;
    }

    public Challenge requiredQuantity(Long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
        return this;
    }

    public void setRequiredQuantity(Long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getChallengeDesc() {
        return challengeDesc;
    }

    public Challenge challengeDesc(String challengeDesc) {
        this.challengeDesc = challengeDesc;
        return this;
    }

    public void setChallengeDesc(String challengeDesc) {
        this.challengeDesc = challengeDesc;
    }

    public Measure getMeasure() {
        return measure;
    }

    public Challenge measure(Measure measure) {
        this.measure = measure;
        return this;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Project getProject() {
        return project;
    }

    public Challenge project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Challenge challenge = (Challenge) o;
        if (challenge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), challenge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Challenge{" +
            "id=" + getId() +
            ", project='" + getProject() + "'" +
            ", measure='" + getMeasure() + "'" +
            ", requiredQuantity=" + getRequiredQuantity() +
            ", challengeDesc='" + getChallengeDesc() + "'" +
            "}";
    }
}
