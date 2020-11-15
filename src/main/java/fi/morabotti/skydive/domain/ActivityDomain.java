package fi.morabotti.skydive.domain;

import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.view.activity.ActivityInformationRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Singleton
public class ActivityDomain {
    private final ClubDomain clubDomain;

    @Inject
    public ActivityDomain(ClubDomain clubDomain) {
        this.clubDomain = clubDomain;
    }

    public Activity updateActivity(
            Activity activity,
            ActivityInformationRequest informationRequest
    ) {
        return activity.toBuilder()
                .setAccess(informationRequest.getAccess())
                .setType(informationRequest.getType())
                .setTitle(informationRequest.getTitle())
                .setDescription(informationRequest.getDescription())
                .setVisible(informationRequest.getVisibility())
                .setStartDate(informationRequest
                        .getStartDate()
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC)
                )
                .setEndDate(informationRequest
                        .getEndDate()
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC)
                )
                .build();
    }

    public Activity createActivity(ActivityInformationRequest informationRequest) {
        return Activity.builder()
                .setId(0L)
                .setAccess(informationRequest.getAccess())
                .setType(informationRequest.getType())
                .setToken(UUID.randomUUID())
                .setTitle(informationRequest.getTitle())
                .setDescription(informationRequest.getDescription())
                .setVisible(informationRequest.getVisibility())
                .setClub(clubDomain.viewToClub(informationRequest.getClub()))
                .setStartDate(informationRequest
                        .getStartDate()
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC)
                )
                .setEndDate(informationRequest
                        .getEndDate()
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC)
                )
                .setCreatedAt(Instant.now())
                .setDeletedAt(null)
                .build();
    }
}
