package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.ActivityDao;
import fi.morabotti.skydive.dao.ClubAccountDao;
import fi.morabotti.skydive.dao.ClubDao;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.domain.ActivityDomain;
import fi.morabotti.skydive.exception.NotFoundException;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.ActivityQuery;
import fi.morabotti.skydive.view.activity.ActivityView;
import fi.morabotti.skydive.view.activity.CreateActivityRequest;
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

    @Inject
    public ActivityController(
            ActivityDomain activityDomain,
            ActivityDao activityDao,
            ClubAccountDao clubAccountDao,
            ClubDao clubDao
    ) {
        this.activityDomain = activityDomain;
        this.activityDao = activityDao;
        this.clubAccountDao = clubAccountDao;
        this.clubDao = clubDao;
    }

    /**
     * Fetches activities.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param dateRangeQuery DateRangeQuery used to define range
     * @return PaginationResponse of ActivityView
     * */
    public PaginationResponse<ActivityView> getActivities(
            PaginationQuery paginationQuery,
            DateRangeQuery dateRangeQuery
    ) {
        ActivityQuery activityQuery = new ActivityQuery();

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
     * @param activityRequest CreateActivityRequest used to initialize activity
     * @return ActivityView that is created
     * @throws InternalServerErrorException if club is not created
     * @throws NotAuthorizedException not user is not correct role in club
     * */
    public ActivityView createActivity(
            CreateActivityRequest activityRequest,
            Account account
    ) {
        if (!account.getAccountRole().equals(AccountRole.admin)) {
            isAuthorized(activityRequest.getClub().getId(), account.getId());
        }

        return ActivityView.of(
                activityDao.create(activityDomain.createActivity(activityRequest))
                        .flatMap(activityDao::getById)
                        .get()
                        .orElseThrow(InternalServerErrorException::new)
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
