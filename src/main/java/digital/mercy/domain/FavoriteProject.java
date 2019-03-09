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
 * A FavoriteProject.
 */
@Entity
@Table(name = "favorite_project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FavoriteProject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "project")
    private String project;

    @OneToMany(mappedBy = "projectName")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("logins")
    private Member user;

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

    public FavoriteProject user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProject() {
        return project;
    }

    public FavoriteProject project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public FavoriteProject projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public FavoriteProject addProject(Project project) {
        this.projects.add(project);
        project.setProjectName(this);
        return this;
    }

    public FavoriteProject removeProject(Project project) {
        this.projects.remove(project);
        project.setProjectName(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Member getUser() {
        return user;
    }

    public FavoriteProject user(Member member) {
        this.user = member;
        return this;
    }

    public void setUser(Member member) {
        this.user = member;
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
        FavoriteProject favoriteProject = (FavoriteProject) o;
        if (favoriteProject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favoriteProject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FavoriteProject{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", project='" + getProject() + "'" +
            "}";
    }
}
