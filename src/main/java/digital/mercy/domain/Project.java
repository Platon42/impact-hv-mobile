package digital.mercy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "description")
    private String description;

    @Column(name = "required_amt")
    private Long requiredAmt;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "currency")
    private String currency;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "author")
    private String author;

    @Column(name = "img_ref")
    private String imgRef;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Challenge> projectNames = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("projects")
    private FavoriteProject projectName;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Investment> projectNames = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public Project user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequiredAmt() {
        return requiredAmt;
    }

    public Project requiredAmt(Long requiredAmt) {
        this.requiredAmt = requiredAmt;
        return this;
    }

    public void setRequiredAmt(Long requiredAmt) {
        this.requiredAmt = requiredAmt;
    }

    public String getProjectName() {
        return projectName;
    }

    public Project projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCurrency() {
        return currency;
    }

    public Project currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public Project country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public Project region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAuthor() {
        return author;
    }

    public Project author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgRef() {
        return imgRef;
    }

    public Project imgRef(String imgRef) {
        this.imgRef = imgRef;
        return this;
    }

    public void setImgRef(String imgRef) {
        this.imgRef = imgRef;
    }

    public Set<Challenge> getProjectNames() {
        return projectNames;
    }

    public Project projectNames(Set<Challenge> challenges) {
        this.projectNames = challenges;
        return this;
    }

    public Project addProjectName(Challenge challenge) {
        this.projectNames.add(challenge);
        challenge.setProject(this);
        return this;
    }

    public Project removeProjectName(Challenge challenge) {
        this.projectNames.remove(challenge);
        challenge.setProject(null);
        return this;
    }

    public void setProjectNames(Set<Challenge> challenges) {
        this.projectNames = challenges;
    }

    public FavoriteProject getProjectName() {
        return projectName;
    }

    public Project projectName(FavoriteProject favoriteProject) {
        this.projectName = favoriteProject;
        return this;
    }

    public void setProjectName(FavoriteProject favoriteProject) {
        this.projectName = favoriteProject;
    }

    public Set<Investment> getProjectNames() {
        return projectNames;
    }

    public Project projectNames(Set<Investment> investments) {
        this.projectNames = investments;
        return this;
    }

    public Project addProjectName(Investment investment) {
        this.projectNames.add(investment);
        investment.setProject(this);
        return this;
    }

    public Project removeProjectName(Investment investment) {
        this.projectNames.remove(investment);
        investment.setProject(null);
        return this;
    }

    public void setProjectNames(Set<Investment> investments) {
        this.projectNames = investments;
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
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
            "}";
    }
}
