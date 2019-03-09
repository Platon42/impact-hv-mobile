package digital.mercy.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Measure entity.
 */
public class MeasureDTO implements Serializable {

    private Long id;

    private String challenge;

    private Long peopleSupportedPoverty;

    private Long peopleReceivedMedicalTreatment;

    private Long kwhCleanEnergyPerYear;

    private Long tonsCo2EmissionsOffset;

    private Long tonsPlasticRecycled;

    private Long tonsWasteRecycled;

    private Long peopleReceivedAccessEducation;

    private Long jobsCreated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public Long getPeopleSupportedPoverty() {
        return peopleSupportedPoverty;
    }

    public void setPeopleSupportedPoverty(Long peopleSupportedPoverty) {
        this.peopleSupportedPoverty = peopleSupportedPoverty;
    }

    public Long getPeopleReceivedMedicalTreatment() {
        return peopleReceivedMedicalTreatment;
    }

    public void setPeopleReceivedMedicalTreatment(Long peopleReceivedMedicalTreatment) {
        this.peopleReceivedMedicalTreatment = peopleReceivedMedicalTreatment;
    }

    public Long getKwhCleanEnergyPerYear() {
        return kwhCleanEnergyPerYear;
    }

    public void setKwhCleanEnergyPerYear(Long kwhCleanEnergyPerYear) {
        this.kwhCleanEnergyPerYear = kwhCleanEnergyPerYear;
    }

    public Long getTonsCo2EmissionsOffset() {
        return tonsCo2EmissionsOffset;
    }

    public void setTonsCo2EmissionsOffset(Long tonsCo2EmissionsOffset) {
        this.tonsCo2EmissionsOffset = tonsCo2EmissionsOffset;
    }

    public Long getTonsPlasticRecycled() {
        return tonsPlasticRecycled;
    }

    public void setTonsPlasticRecycled(Long tonsPlasticRecycled) {
        this.tonsPlasticRecycled = tonsPlasticRecycled;
    }

    public Long getTonsWasteRecycled() {
        return tonsWasteRecycled;
    }

    public void setTonsWasteRecycled(Long tonsWasteRecycled) {
        this.tonsWasteRecycled = tonsWasteRecycled;
    }

    public Long getPeopleReceivedAccessEducation() {
        return peopleReceivedAccessEducation;
    }

    public void setPeopleReceivedAccessEducation(Long peopleReceivedAccessEducation) {
        this.peopleReceivedAccessEducation = peopleReceivedAccessEducation;
    }

    public Long getJobsCreated() {
        return jobsCreated;
    }

    public void setJobsCreated(Long jobsCreated) {
        this.jobsCreated = jobsCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeasureDTO measureDTO = (MeasureDTO) o;
        if (measureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), measureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeasureDTO{" +
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
