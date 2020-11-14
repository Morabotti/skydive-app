package fi.morabotti.skydive.view.club;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ClubAccountRole;

@EasyValue
@JsonDeserialize(builder = ClubMemberRequest.Builder.class)
public abstract class ClubMemberRequest {
    public abstract ClubAccountRole getRole();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubMemberRequest.Builder {

    }
}
