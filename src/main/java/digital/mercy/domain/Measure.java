package digital.mercy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Measure.
 */
@Entity
@Table(name = "measure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Measure implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "challenge")
    private String challenge;

    @Column(name = "people_supported_poverty")
    private Long peopleSupportedPoverty;

    @Column(name = "people_received_medical_treatment")
    private Long peopleReceivedMedicalTreatment;

    @Column(name = "kwh_clean_energy_per_year")
    private Long kwhCleanEnergyPerYear;

    @Column(name = "tons_co_2_emissions_offset")
    private Long tonsCo2EmissionsOffset;

    @Column(name = "tons_plastic_recycled")
    private Long tonsPlasticRecycled;

    @Column(name = "tons_waste_recycled")
    private Long tonsWasteRecycled;

    @Column(name = "people_received_access_education")
    private Long peopleReceivedAccessEducation;

    @Column(name = "jobs_created")
    private Long jobsCreated;

    @OneToOne(mappedBy = "measure")
    @JsonIgnore
    private Challenge challenge;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChallenge() {
        return challenge;
    }

    public Measure challenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public Long getPeopleSupportedPoverty() {
        return peopleSupportedPoverty;
    }

    public Measure peopleSupportedPoverty(Long peopleSupportedPoverty) {
        this.peopleSupportedPoverty = peopleSupportedPoverty;
        return this;
    }

    public void setPeopleSupportedPoverty(Long peopleSupportedPoverty) {
        this.peopleSupportedPoverty = peopleSupportedPoverty;
    }

    public Long getPeopleReceivedMedicalTreatment() {
        return peopleReceivedMedicalTreatment;
    }

    public Measure peopleReceivedMedicalTreatment(Long peopleReceivedMedicalTreatment) {
        this.peopleReceivedMedicalTreatment = peopleReceivedMedicalTreatment;
        return this;
    }

    public void setPeopleReceivedMedicalTreatment(Long peopleReceivedMedicalTreatment) {
        this.peopleReceivedMedicalTreatment = peopleReceivedMedicalTreatment;
    }

    public Long getKwhCleanEnergyPerYear() {
        return kwhCleanEnergyPerYear;
    }

    public Measure kwhCleanEnergyPerYear(Long kwhCleanEnergyPerYear) {
        this.kwhCleanEnergyPerYear = kwhCleanEnergyPerYear;
        return this;
    }

    public void setKwhCleanEnergyPerYear(Long kwhCleanEnergyPerYear) {
        this.kwhCleanEnergyPerYear = kwhCleanEnergyPerYear;
    }

    public Long getTonsCo2EmissionsOffset() {
        return tonsCo2EmissionsOffset;
    }

    public Measure tonsCo2EmissionsOffset(Long tonsCo2EmissionsOffset) {
        this.tonsCo2EmissionsOffset = tonsCo2EmissionsOffset;
        return this;
    }

    public void setTonsCo2EmissionsOffset(Long tonsCo2EmissionsOffset) {
        this.tonsCo2EmissionsOffset = tonsCo2EmissionsOffset;
    }

    public Long getTonsPlasticRecycled() {
        return tonsPlasticRecycled;
    }

    public Measure tonsPlasticRecycled(Long tonsPlasticRecycled) {
        this.tonsPlasticRecycled = tonsPlasticRecycled;
        return this;
    }

    public void setTonsPlasticRecycled(Long tonsPlasticRecycled) {
        this.tonsPlasticRecycled = tonsPlasticRecycled;
    }

    public Long getTonsWasteRecycled() {
        return tonsWasteRecycled;
    }

    public Measure tonsWasteRecycled(Long tonsWasteRecycled) {
        this.tonsWasteRecycled = tonsWasteRecycled;
        return this;
    }

    public void setTonsWasteRecycled(Long tonsWasteRecycled) {
        this.tonsWasteRecycled = tonsWasteRecycled;
    }

    public Long getPeopleReceivedAccessEducation() {
        return peopleReceivedAccessEducation;
    }

    public Measure peopleReceivedAccessEducation(Long peopleReceivedAccessEducation) {
        this.peopleReceivedAccessEducation = peopleReceivedAccessEducation;
        return this;
    }

    public void setPeopleReceivedAccessEducation(Long peopleReceivedAccessEducation) {
        this.peopleReceivedAccessEducation = peopleReceivedAccessEducation;
    }

    public Long getJobsCreated() {
        return jobsCreated;
    }

    public Measure jobsCreated(Long jobsCreated) {
        this.jobsCreated = jobsCreated;
        return this;
    }

    public void setJobsCreated(Long jobsCreated) {
        this.jobsCreated = jobsCreated;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public Measure challenge(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
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
        Measure measure = (Measure) o;
        if (measure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), measure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Measure{" +
            "id=" + getId() +
            ", challenge='" + getChallenge() + "'" +
            ", peopleSupportedPoverty=" + getPeopleSupportedPoverty() +
            ", peopleReceivedMedicalTreatment=" + getPeopleReceivedMedicalTreatment() +
            ", kwhCleanEnergyPerYear=" + getKwhCleanEnergyPerYear() +
            ", tonsCo2EmissionsOffset=" + getTonsCo2EmissionsOffset() +
            ", tonsPlasticRecycled=" + getTonsPlasticRecycled() +
            ", tonsWasteRecycled=" + getTonsWasteRecycled() +
            ", peopleReceivedAccessEducation=" + getPeopleReceivedAccessEducation() +
            ", jobsCreated=" + getJobsCreated() +
            "}";
    }
}
