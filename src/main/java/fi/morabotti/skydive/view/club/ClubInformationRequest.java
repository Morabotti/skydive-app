package fi.morabotti.skydive.view.club;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

@EasyValue
@JsonDeserialize(builder = ClubInformationRequest.Builder.class)
public abstract class ClubInformationRequest {
    public abstract String getName();

    public abstract String getSlug();

    public abstract Boolean getIsPublic();

    public abstract String getDescription();

    public abstract String getAddress();

    public abstract String getCity();

    public abstract String getZipCode();

    public abstract String getPhone();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubInformationRequest.Builder {

    }
}