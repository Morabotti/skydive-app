package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.ActivityDao;
import fi.morabotti.skydive.dao.ActivityParticipationDao;
import fi.morabotti.skydive.dao.ClubAccountDao;
import fi.morabotti.skydive.dao.PlaneDao;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.domain.ActivityDomain;
import fi.morabotti.skydive.exception.NotFoundException;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.model.ActivityParticipation;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.model.PilotActivityParticipation;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.ActivityInformationRequest;
import fi.morabotti.skydive.view.activity.ActivityParticipationQuery;
import fi.morabotti.skydive.view.activity.ActivityParticipationView;
import fi.morabotti.skydive.view.activity.ActivityQuery;
import fi.morabotti.skydive.view.activity.ActivityView;
import fi.morabotti.skydive.view.activity.PersonalActivityView;
import fi.morabotti.skydive.view.club.ClubAccountQuery;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class ActivityController {
    private final ActivityDomain activityDomain;

    private final ActivityDao activityDao;
    private final ClubAccountDao clubAccountDao;
    private final ActivityParticipationDao participationDao;
    private final PlaneDao planeDao;

    @Inject
    public ActivityController(
            ActivityDomain activityDomain,
            ActivityDao activityDao,
            ClubAccountDao clubAccountDao,
            ActivityParticipationDao participationDao,
            PlaneDao planeDao
    ) {
        this.activityDomain = activityDomain;
        this.activityDao = activityDao;
        this.clubAccountDao = clubAccountDao;
        this.participationDao = participationDao;
        this.planeDao = planeDao;
    }

    /**
     * Fetches activities.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @param activityQuery ActivityQuery used to define filters
     * @param account Account of the requesting user
     * @return PaginationResponse of ActivityView
     * */
    public PaginationResponse<ActivityView> getAllActivities(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery,
            ActivityQuery activityQuery,
            Account account
    ) {
        return getActivitiesWithQueries(
                paginationQuery,
                dateRangeQuery,
                activityQuery,
                account
        );
    }

    /**
     * Fetches certain club activities.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @param activityQuery ActivityQuery used to define filters
     * @param clubId Long used to
     * @param account Account of the requesting user
     * @return PaginationResponse of ActivityView
     * */
    public PaginationResponse<ActivityView> getClubActivities(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery,
            ActivityQuery activityQuery,
            Long clubId,
            Account account
    ) {
        return getActivitiesWithQueries(
                paginationQuery,
                dateRangeQuery,
                activityQuery.withClubId(clubId),
                account
        );
    }

    /**
     * Creates participation as pilot to activity.
     * @param account Account of the participant
     * @param activityId Long of the activity
     * @param planeId Long of the plane used
     * @param hasInvite Boolean if has invite. Should defaults to false
     * @return PilotActivityParticipation
     * */
    public PilotActivityParticipation joinActivityAsPilot(
            Account account,
            Long activityId,
            Long planeId,
            Boolean hasInvite
    ) {
        Activity activity = checkActivityAuth(
                activityId,
                account.getId(),
                false,
                true
        );

        return planeDao.getById(planeId)
                .flatMap(plane -> participationDao.create(
                        activityDomain.createPilotParticipation(
                                activity,
                                account,
                                true,
                                plane.orElseThrow(NotFoundException::new)
                        )
                ))
                .flatMap(participationDao::getPilotParticipation)
                .get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Removes participation or interest.
     * @param account Account of the participant
     * @param activityId Long of the activity
     * @return Void
     * */
    public Void removeUserParticipation(
            Account account,
            Long activityId
    ) {
        ActivityParticipation participation = participationDao.getParticipants(
                new ActivityParticipationQuery()
                        .withAccountId(account.getId())
                        .withActivityId(activityId),
                new DateRangeQuery()
        )
                .stream()
                .findFirst()
                .orElseThrow(NotFoundException::new);

        return participationDao.deleteParticipation(participation.getId())
                .get();
    }

    /**
     * Removes pilot participation or interest.
     * @param account Account of the participant
     * @param activityId Long of the activity
     * @return Void
     * */
    public Void removePilotParticipation(
            Account account,
            Long activityId
    ) {
        PilotActivityParticipation participation = participationDao.getPilotParticipants(
                new ActivityParticipationQuery()
                        .withAccountId(account.getId())
                        .withActivityId(activityId),
                new DateRangeQuery()
        )
                .stream()
                .findFirst()
                .orElseThrow(NotFoundException::new);

        return participationDao.deletePilotParticipation(participation.getId())
                .get();
    }

    /**
     * Creates participation as pilot to activity.
     * @param account Account of the participant
     * @param activityId Long of the activity
     * @param hasInvite Boolean if has invite. Should defaults to false
     * @return ActivityParticipation
     * */
    public ActivityParticipation joinActivityAsUser(
            Account account,
            Long activityId,
            Boolean hasInvite
    ) {
        Activity activity = checkActivityAuth(
                activityId,
                account.getId(),
                hasInvite,
                false
        );

        return participationDao.create(
                activityDomain.createParticipation(
                        activity,
                        account,
                        true
                )
        )
                .flatMap(participationDao::getParticipation)
                .get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Creates activity interest. Everyone who has access can interest activity.
     * @param account Account of the participant
     * @param activityId Long of the activity
     * @return ActivityParticipation
     * */
    public ActivityParticipation activityInterest(
            Account account,
            Long activityId
    ) {
        Activity activity = checkActivityAuth(
                activityId,
                account.getId(),
                false,
                null
        );

        return participationDao.create(
                activityDomain.createParticipation(
                        activity,
                        account,
                        false
                )
        )
                .flatMap(participationDao::getParticipation)
                .get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Fetches activities for certain account id.
     * @param rangeQuery DateRangeQuery of activity start & end date
     * @param accountId Long id of the account
     * @return PersonalActivityView with activity data
     * */
    public PersonalActivityView getAccountsActivities(
            DateRangeQuery rangeQuery,
            Long accountId
    ) {
        ActivityParticipationQuery query = new ActivityParticipationQuery()
                .withAccountId(accountId);

        return PersonalActivityView.of(
                activityDao.fetchActivities(
                        new PaginationQuery(512, 0),
                        rangeQuery,
                        new ActivityQuery().withAccountId(accountId),
                        false,
                        true
                )
                        .stream()
                        .map(ActivityView::of)
                        .collect(Collectors.toList()),
                Stream.concat(
                        participationDao.getParticipates(
                                query,
                                rangeQuery
                        )
                                .stream()
                                .map(ActivityParticipationView::of),
                        participationDao.getPilotParticipates(
                                query,
                                rangeQuery
                        )
                                .stream()
                                .map(ActivityParticipationView::of)
                ).collect(Collectors.toList())
        );
    }

    /**
     * Fetches participants for certain activity.
     * @param activityId Long id of the activity
     * @return List of ActivityParticipationView with account data
     * */
    public List<ActivityParticipationView> getParticipants(
            Long activityId
    ) {
        ActivityParticipationQuery query = new ActivityParticipationQuery()
                .withActivityId(activityId);

        return Stream.concat(
                participationDao.getParticipants(
                        query,
                        new DateRangeQuery()
                )
                        .stream()
                        .map(ActivityParticipationView::of),
                participationDao.getPilotParticipants(
                        query,
                        new DateRangeQuery()
                )
                        .stream()
                        .map(ActivityParticipationView::of)
        ).collect(Collectors.toList());
    }

    /**
     * Fetches activity by token.
     * @param token UUID identifies wanted activity
     * @return ActivityView
     * @throws NotFoundException if club not found
     * */
    public ActivityView getActivity(UUID token) {
        return ActivityView.of(
                activityDao.getByToken(token)
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Fetches activity by id.
     * @param activityId Long identifies wanted activity
     * @return ActivityView
     * @throws NotFoundException if club not found
     * */
    public ActivityView getActivity(Long activityId) {
        return ActivityView.of(
                activityDao.getById(activityId).get()
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Creates new activity based on CreateActivityRequest.
     * @param informationRequest ActivityInformationRequest used to initialize activity
     * @param account Account of user creating activity
     * @return ActivityView that is created
     * @throws InternalServerErrorException if club is not created
     * @throws NotAuthorizedException not user is not correct role in club
     * */
    public ActivityView createActivity(
            ActivityInformationRequest informationRequest,
            Account account
    ) {
        if (!account.getAccountRole().equals(AccountRole.admin)) {
            isAuthorized(informationRequest.getClub().getId(), account.getId());
        }

        return ActivityView.of(
                activityDao.create(activityDomain.createActivity(informationRequest))
                        .flatMap(activityDao::getById)
                        .get()
                        .orElseThrow(InternalServerErrorException::new)
        );
    }

    /**
     * Deletes activity and all the related data.
     * @param id Long used to identify activity in question
     * @param account Account of user updating activity
     * @return ActivityView that is updated
     * @throws InternalServerErrorException if something went wrong
     * @throws NotAuthorizedException not user is not correct role in club
     * @throws NotFoundException if club not found by Id
     * */
    public Void deleteActivity(
            Long id,
            Account account
    ) {
        Activity activity = activityDao.getById(id).get()
                .orElseThrow(NotFoundException::new);

        if (!account.getAccountRole().equals(AccountRole.admin)) {
            Club club = Optional.ofNullable(activity.getClub())
                    .orElseThrow(InternalServerErrorException::new);

            isAuthorized(club.getId(), account.getId());
        }

        return activityDao.delete(id)
                .peekMap(ignored -> participationDao.deleteActivityParticipation(id))
                .peekMap(ignored -> participationDao.deleteActivityPilotParticipation(id))
                .get();
    }

    /**
     * Creates new activity based on CreateActivityRequest.
     * @param id Long used to identify activity in question
     * @param informationRequest ActivityInformationRequest used to initialize activity
     * @param account Account of user updating activity
     * @return ActivityView that is updated
     * @throws InternalServerErrorException if something went wrong :)
     * @throws NotAuthorizedException not user is not correct role in club
     * */
    public ActivityView updateActivity(
            Long id,
            ActivityInformationRequest informationRequest,
            Account account
    ) {
        if (!account.getAccountRole().equals(AccountRole.admin)) {
            isAuthorized(informationRequest.getClub().getId(), account.getId());
        }

        return ActivityView.of(
                activityDao.getById(id)
                        .flatMap(activity ->
                                activityDao.update(
                                        activityDomain.updateActivity(
                                                activity.orElseThrow(NotFoundException::new),
                                                informationRequest
                                        )
                                )
                        )
                        .get()
                        .orElseThrow(InternalServerErrorException::new)
        );
    }

    /**
     * Fetches activities based on queries. Always returns based on current Account.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @param activityQuery ActivityQuery used to define selection
     * @param account Account of the requesting user
     * @return PaginationResponse of ActivityView
     * */
    private PaginationResponse<ActivityView> getActivitiesWithQueries(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery,
            ActivityQuery activityQuery,
            Account account
    ) {
        return PaginationResponse.create(
                activityDao.fetchActivities(
                        paginationQuery,
                        dateRangeQuery,
                        activityQuery.withAccountId(account.getId()),
                        account.getAccountRole().equals(AccountRole.admin),
                        false
                ).stream()
                        .map(ActivityView::of)
                        .collect(Collectors.toList()),
                activityDao.fetchActivitiesLength(
                        activityQuery.withAccountId(account.getId()),
                        dateRangeQuery,
                        account.getAccountRole().equals(AccountRole.admin),
                        false
                )
        );
    }

    /**
     * Checks whether user is owner of the club.
     * @param activityId Long id of the activity
     * @param accountId Long id of the account
     * @param hasInvite Boolean whether user has invite
     * @return ClubAccount
     * */
    private Activity checkActivityAuth(
            Long activityId,
            Long accountId,
            Boolean hasInvite,
            @Nullable Boolean isPilotOnly
    ) {
        Activity activity = activityDao.getById(activityId)
                .get()
                .orElseThrow(NotFoundException::new);

        if (!hasInvite && activity.getAccess().equals(ActivityAccess.invite)) {
            throw new NotAuthorizedException("User is not authorized to join");
        }

        if (!activity.getAccess().equals(ActivityAccess.open)) {
            ClubAccount clubAccount = clubAccountDao.findAccount(
                    ClubAccountQuery.of(
                            Optional.ofNullable(activity.getClub())
                                    .orElseThrow(InternalServerErrorException::new)
                                    .getId(),
                            accountId
                    )
            )
                    .get()
                    .orElseThrow(() -> new NotAuthorizedException("Not member of club."));

            if (isPilotOnly != null
                    && isPilotOnly
                    && !clubAccount.getRole().equals(ClubAccountRole.pilot)
            ) {
                throw new BadRequestException("Wrong method");
            }
        }

        return activity;
    }

    /**
     * Checks whether user is owner of the club.
     * @param clubId Long id of the club
     * @param accountId Long id of the account
     * @return ClubAccount
     * */
    private ClubAccount isAuthorized(
            Long clubId,
            Long accountId
    ) {
        Optional<ClubAccount> clubAccount = clubAccountDao
                .findAccount(ClubAccountQuery.of(clubId, accountId))
                .get();

        if (!clubAccount.isPresent() || !clubAccount.get().getRole().equals(ClubAccountRole.club)) {
            throw new NotAuthorizedException("User is not authorized.");
        }

        return clubAccount.get();
    }
}
