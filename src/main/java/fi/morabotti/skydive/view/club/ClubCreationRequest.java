package fi.morabotti.skydive.view.club;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

@EasyValue
@JsonDeserialize(builder = ClubCreationRequest.Builder.class)
public abstract class ClubCreationRequest {
    public abstract String getName();

    public abstract String getSlug();

    public abstract Boolean getIsPublic();

    public abstract String getDescription();

    public abstract String getAddress();

    public abstract String getCity();

    public abstract Long getCreatorId();

    public abstract String getZipCode();

    public abstract String getPhone();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubCreationRequest.Builder {

    }
}