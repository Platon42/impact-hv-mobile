package digital.mercy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "jhi_password", nullable = false)
    private String password;

    @Column(name = "project")
    private String project;

    @NotNull
    @Column(name = "jhi_role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FavoriteProject> logins = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Investment> logins = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Member login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Member password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProject() {
        return project;
    }

    public Member project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRole() {
        return role;
    }

    public Member role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<FavoriteProject> getLogins() {
        return logins;
    }

    public Member logins(Set<FavoriteProject> favoriteProjects) {
        this.logins = favoriteProjects;
        return this;
    }

    public Member addLogin(FavoriteProject favoriteProject) {
        this.logins.add(favoriteProject);
        favoriteProject.setUser(this);
        return this;
    }

    public Member removeLogin(FavoriteProject favoriteProject) {
        this.logins.remove(favoriteProject);
        favoriteProject.setUser(null);
        return this;
    }

    public void setLogins(Set<FavoriteProject> favoriteProjects) {
        this.logins = favoriteProjects;
    }

    public Set<Investment> getLogins() {
        return logins;
    }

    public Member logins(Set<Investment> investments) {
        this.logins = investments;
        return this;
    }

    public Member addLogin(Investment investment) {
        this.logins.add(investment);
        investment.setUser(this);
        return this;
    }

    public Member removeLogin(Investment investment) {
        this.logins.remove(investment);
        investment.setUser(null);
        return this;
    }

    public void setLogins(Set<Investment> investments) {
        this.logins = investments;
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
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", project='" + getProject() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
