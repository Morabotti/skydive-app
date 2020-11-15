package fi.morabotti.skydive.domain;

import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.view.activity.CreateActivityRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Singleton
public class ActivityDomain {

    @Inject
    public ActivityDomain() {

    }

    public Activity createActivity(CreateActivityRequest activityRequest) {
        return Activity.builder()
                .setId(0L)
                .setAccess(activityRequest.getAccess())
                .setType(activityRequest.getType())
                .setToken(UUID.randomUUID())
                .setDescription(activityRequest.getDescription())
                .setVisible(activityRequest.getVisibility())
                .setClub(activityRequest.getClub())
                .setStartDate(activityRequest
                        .getStartDate()
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC)
                )
                .setEndDate(activityRequest
                        .getStartDate()
                        .atStartOfDay()
                        .toInstant(ZoneOffset.UTC)
                )
                .setCreatedAt(Instant.now())
                .setDeletedAt(null)
                .build();
    }
}
