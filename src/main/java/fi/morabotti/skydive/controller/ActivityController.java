package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.ActivityDao;
import fi.morabotti.skydive.dao.ActivityParticipationDao;
import fi.morabotti.skydive.dao.ClubAccountDao;
import fi.morabotti.skydive.dao.ClubDao;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.domain.ActivityDomain;
import fi.morabotti.skydive.exception.NotFoundException;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.ActivityInformationRequest;
import fi.morabotti.skydive.view.activity.ActivityParticipationView;
import fi.morabotti.skydive.view.activity.ActivityQuery;
import fi.morabotti.skydive.view.activity.ActivityView;
import fi.morabotti.skydive.view.club.ClubAccountQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ActivityController {
    private final ActivityDomain activityDomain;

    private final ActivityDao activityDao;
    private final ClubAccountDao clubAccountDao;
    private final ClubDao clubDao;
    private final ActivityParticipationDao participationDao;

    @Inject
    public ActivityController(
            ActivityDomain activityDomain,
            ActivityDao activityDao,
            ClubAccountDao clubAccountDao,
            ClubDao clubDao,
            ActivityParticipationDao participationDao
    ) {
        this.activityDomain = activityDomain;
        this.activityDao = activityDao;
        this.clubAccountDao = clubAccountDao;
        this.clubDao = clubDao;
        this.participationDao = participationDao;
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
     * Fetches participants for certain activity.
     * @param activityId Long id of the activity
     * @return ActivityParticipationView
     * */
    public ActivityParticipationView getParticipants(
            Long activityId
    ) {
        return ActivityParticipationView.create(
                participationDao.getParticipants(activityId),
                participationDao.getPilotParticipants(activityId)
        );
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
