package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.ActivityDao;
import fi.morabotti.skydive.dao.ActivityParticipationDao;
import fi.morabotti.skydive.dao.ClubAccountDao;
import fi.morabotti.skydive.dao.ClubDao;
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
import fi.morabotti.skydive.view.club.ClubAccountQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import java.time.LocalDate;
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
    private final ClubDao clubDao;
    private final ActivityParticipationDao participationDao;
    private final PlaneDao planeDao;

    @Inject
    public ActivityController(
            ActivityDomain activityDomain,
            ActivityDao activityDao,
            ClubAccountDao clubAccountDao,
            ClubDao clubDao,
            ActivityParticipationDao participationDao,
            PlaneDao planeDao
    ) {
        this.activityDomain = activityDomain;
        this.activityDao = activityDao;
        this.clubAccountDao = clubAccountDao;
        this.clubDao = clubDao;
        this.participationDao = participationDao;
        this.planeDao = planeDao;
    }

    /**
     * Fetches activities.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @return PaginationResponse of ActivityView
     * */
    public PaginationResponse<ActivityView> getAllActivities(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery
    ) {
        return getActivitiesWithQueries(
                paginationQuery,
                dateRangeQuery,
                new ActivityQuery()
        );
    }

    public List<ActivityParticipationView> getAccountsActivities(
            Long accountId
    ) {
        ActivityParticipationQuery query = new ActivityParticipationQuery()
                .withAccountId(accountId);

        DateRangeQuery rangeQuery = new DateRangeQuery(
                LocalDate.now().minusWeeks(1),
                LocalDate.now().plusWeeks(2)
        );

        return Stream.concat(
                participationDao.getParticipants(
                        query,
                        rangeQuery
                )
                        .stream()
                        .map(ActivityParticipationView::of),
                participationDao.getPilotParticipants(
                        query,
                        rangeQuery
                )
                        .stream()
                        .map(ActivityParticipationView::of)
        ).collect(Collectors.toList());
    }

    /**
     * Fetches certain club activities.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @param clubId Long used to
     * @return PaginationResponse of ActivityView
     * */
    public PaginationResponse<ActivityView> getClubActivities(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery,
            Long clubId
    ) {
        return getActivitiesWithQueries(
                paginationQuery,
                dateRangeQuery,
                new ActivityQuery().withClubId(clubId)
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
        Activity activity = activityDao.getById(activityId).get()
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
                            account.getId()
                    )
            ).get()
                    .orElseThrow(() -> new NotAuthorizedException("Not member of club"));

            if (clubAccount.getRole().equals(ClubAccountRole.user)) {
                throw new BadRequestException("Wrong method");
            }
        }

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
     * Creates activity interest. Everyone who has access can interest activity.
     * @param account Account of the participant
     * @param activityId Long of the activity
     * @return ActivityParticipation
     * */
    public ActivityParticipation activityInterest(
            Account account,
            Long activityId
    ) {
        Activity activity = activityDao.getById(activityId).get()
                .orElseThrow(NotFoundException::new);

        if (activity.getAccess().equals(ActivityAccess.invite)) {
            throw new NotAuthorizedException("Can't have interest in invite");
        }

        if (!activity.getAccess().equals(ActivityAccess.open)) {
            clubAccountDao.findAccount(
                    ClubAccountQuery.of(
                            Optional.ofNullable(activity.getClub())
                                    .orElseThrow(InternalServerErrorException::new)
                                    .getId(),
                            account.getId()
                    )
            ).get()
                    .orElseThrow(() -> new NotAuthorizedException("Not member of club"));
        }

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
        Activity activity = activityDao.getById(activityId).get()
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
                            account.getId()
                    )
            ).get()
                    .orElseThrow(() -> new NotAuthorizedException("Not member of club"));

            if (clubAccount.getRole().equals(ClubAccountRole.pilot)) {
                throw new BadRequestException("Wrong method");
            }
        }

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
     * Fetches participants for certain activity.
     * @param activityId Long id of the activity
     * @return List of ActivityParticipationView
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
     * Fetches activities based on queries.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @param activityQuery ActivityQuery used to define selection
     * @return PaginationResponse of ActivityView
     * */
    private PaginationResponse<ActivityView> getActivitiesWithQueries(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery,
            ActivityQuery activityQuery
    ) {
        return PaginationResponse.create(
                activityDao.fetchActivities(
                        paginationQuery,
                        dateRangeQuery,
                        activityQuery
                ).stream()
                        .map(ActivityView::of)
                        .collect(Collectors.toList()),
                activityDao.fetchActivitiesLength(
                        activityQuery,
                        dateRangeQuery
                )
        );
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
        Optional<ClubAccount> clubAccount = clubAccountDao.findAccount(
                ClubAccountQuery.of(clubId, accountId)
        ).get();

        if (!clubAccount.isPresent() || !clubAccount.get().getRole().equals(ClubAccountRole.club)) {
            throw new NotAuthorizedException("User is not authorized.");
        }

        return clubAccount.get();
    }
}
