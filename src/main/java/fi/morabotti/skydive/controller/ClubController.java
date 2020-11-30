package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.ClubAccountDao;
import fi.morabotti.skydive.dao.ClubDao;
import fi.morabotti.skydive.dao.ClubProfileDao;
import fi.morabotti.skydive.dao.PlaneDao;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.domain.ClubDomain;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubProfile;
import fi.morabotti.skydive.model.Plane;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.club.ClubAccountQuery;
import fi.morabotti.skydive.view.club.ClubAccountView;
import fi.morabotti.skydive.view.club.ClubInformationRequest;
import fi.morabotti.skydive.view.club.ClubMemberRequest;
import fi.morabotti.skydive.view.club.ClubQuery;
import fi.morabotti.skydive.view.club.ClubView;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ClubController {
    private final ClubDomain clubDomain;

    private final ClubDao clubDao;
    private final ClubProfileDao clubProfileDao;
    private final ClubAccountDao clubAccountDao;
    private final PlaneDao planeDao;

    @Inject
    public ClubController(
            ClubDomain clubDomain,
            ClubDao clubDao,
            ClubProfileDao clubProfileDao,
            ClubAccountDao clubAccountDao,
            PlaneDao planeDao
    ) {
        this.clubDomain = clubDomain;
        this.clubDao = clubDao;
        this.clubProfileDao = clubProfileDao;
        this.clubAccountDao = clubAccountDao;
        this.planeDao = planeDao;
    }

    /**
     * Fetches clubs.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param clubQuery ClubQuery used to filter results
     * @param account Account used to see whether user is admin
     * @return PaginationResponse of ClubView
     * */
    public PaginationResponse<ClubView> getClubs(
            PaginationQuery paginationQuery,
            ClubQuery clubQuery,
            Account account
    ) {
        return PaginationResponse.create(
                clubDao.fetchClubs(
                        paginationQuery,
                        clubQuery,
                        account.getAccountRole().equals(AccountRole.admin)
                ).stream()
                        .map(ClubView::of)
                        .collect(Collectors.toList()),
                clubDao.fetchClubLength(
                        clubQuery,
                        account.getAccountRole().equals(AccountRole.admin)
                )
        );
    }

    /**
     * Fetches clubs that certain account.
     * @param accountId Long id of wanted account clubs
     * @return List of ClubAccountView with Club set
     * */
    public List<ClubAccountView> getAccountClubs(
            Long accountId
    ) {
        return clubAccountDao.fetchClubs(
                PaginationQuery.of(100, 0),
                ClubAccountQuery.of(null, accountId)
        )
                .stream()
                .map(ClubAccountView::of)
                .collect(Collectors.toList());
    }

    /**
     * Fetches club member.
     * @param clubId Long slug for club
     * @param accountId Long target account id
     * @param account Account of the requester
     * @return ClubAccountView of account if member
     * @throws NotFoundException if club not found
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public ClubAccountView getMember(
            Long clubId,
            Long accountId,
            Account account
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), false);
        }

        clubAccountDao.getClubMembership(club, accountId, true, true);

        return ClubAccountView.of(
                clubAccountDao.findAccount(
                        ClubAccountQuery.of(clubId, accountId)
                )
                        .get()
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Fetches clubs member.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param clubAccountQuery ClubAccountQuery used to filter result
     * @param clubId Long identifies wanted
     * @param account Account of the requester
     * @return PaginationResponse of ClubAccountView
     * @throws NotFoundException if club not found
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public PaginationResponse<ClubAccountView> getMembers(
            PaginationQuery paginationQuery,
            ClubAccountQuery clubAccountQuery,
            Long clubId,
            Account account
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), false);
        }

        ;

        return PaginationResponse.create(
                clubAccountDao.fetchClubAccounts(
                        paginationQuery,
                        clubAccountQuery.withClubId(club.getId())
                ).stream()
                        .map(ClubAccountView::of)
                        .collect(Collectors.toList()),
                clubAccountDao.fetchClubMembersLength(clubAccountQuery.withClubId(club.getId()))
        );
    }

    /**
     * Fetches club by id.
     * @param slug String identifies wanted club
     * @return ClubView
     * @throws NotFoundException if club not found
     * */
    public ClubView getClub(String slug) {
        return ClubView.of(
                clubDao.getClubBySlug(slug).get()
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Fetches club by slug.
     * @param id Long identifies wanted club
     * @return ClubView
     * @throws NotFoundException if club not found
     * */
    public ClubView getClub(Long id) {
        return ClubView.of(
                clubDao.getById(id).get()
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Fetch plane by plane Id.
     * @param clubId Long identifies clubs
     * @param account Account of the requester
     * @return List of Plane
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public List<Plane> getPlanes(Long clubId, Account account) {
        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), false);
        }

        return planeDao.fetchPlanes(clubId);
    }

    /**
     * Fetch plane by plane Id.
     * @param planeId Long identifies wanted plane
     * @param clubId Long club in question
     * @param account Account of the requester
     * @return Plane
     * @throws NotFoundException if plane not found
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public Plane getPlane(Long planeId, Long clubId, Account account) {
        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), false);
        }

        return planeDao.getById(planeId).get()
                .orElseThrow(NotFoundException::new);
    }

    /**
     * Creates new plane for club.
     * @param clubId Long identifies club.
     * @param account Account of the requester
     * @param plane Plane to be created
     * @return newly created Plane
     * @throws InternalServerErrorException if something went wrong
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public Plane createPlane(Long clubId, Account account, Plane plane) {
        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), true);
        }

        return planeDao.create(clubId, plane)
                .flatMap(planeDao::getById)
                .get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Updates plane.
     * @param clubId Long identifies club.
     * @param account Account of the requester
     * @param plane Plane to be updated
     * @return updated Plane
     * @throws InternalServerErrorException if something went wrong
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public Plane updatePlane(Long clubId, Account account, Plane plane) {
        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), true);
        }

        return planeDao.update(clubId, plane).get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Removes plane by Id.
     * @param planeId Long identifies plane.
     * @param account Account of the requester
     * @return Void
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public Void deletePlane(Long planeId, Long clubId, Account account) {
        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), true);
        }

        return planeDao.delete(planeId).get();
    }

    /**
     * Creates new club.
     * @param informationRequest ClubInformationRequest used for information of club
     * @return ClubView that is created
     * @throws InternalServerErrorException if club is not created
     * */
    public ClubView createClub(
            ClubInformationRequest informationRequest
    ) {
        return ClubView.of(
                clubDao.create(clubDomain.createClub(informationRequest))
                        .flatMap(club -> clubProfileDao.create(
                                clubDomain.createClubProfile(
                                        club,
                                        informationRequest
                                )
                        ))
                        .flatMap(clubDao::getById)
                        .get()
                        .orElseThrow(InternalServerErrorException::new)
        );
    }

    /**
     * Updates existing club.
     * @param clubId Long id of the club
     * @param informationRequest ClubInformationRequest used for information of club
     * @param account Account of the requester
     * @return ClubView of updated club
     * @throws BadRequestException if club is not created
     * @throws NotFoundException if club is not found
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public ClubView updateClub(
            Long clubId,
            ClubInformationRequest informationRequest,
            Account account
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(club.getId(), account.getId(), true);
        }

        ClubProfile clubProfile = club.getClubProfile().isPresent()
                ? club.getClubProfile().get()
                : clubDomain.createClubProfile(club, informationRequest);

        return ClubView.of(
                clubDao.update(
                        clubDomain.updateClub(
                                club,
                                informationRequest
                        )
                )
                        .peek(c -> clubProfileDao.update(
                                clubDomain.updateClubProfile(
                                        clubProfile,
                                        informationRequest
                                )
                        ))
                        .get()
                        .orElseThrow(BadRequestException::new)
        );
    }

    /**
     * Deletes club, its profile, clears planes and accounts.
     * @param clubId Long id for club
     * @param account Account of the requester
     * @throws NotFoundException if club is not found with id
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public Void deleteClub(Long clubId, Account account) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(club.getId(), account.getId(), true);
        }

        return clubAccountDao.deleteByClubId(club.getId())
                .flatMap(ignored -> planeDao.deleteByClub(club.getId()))
                .flatMap(ignored -> clubProfileDao.delete(
                        club.getClubProfile()
                                .orElseThrow(InternalServerErrorException::new)
                                .getId()
                        )
                )
                .flatMap(ignored -> clubDao.delete(club.getId()))
                .get();
    }

    /**
     * Adds member to club. Should be available for admin only.
     * @param clubId Long id for club
     * @param accountId Integer account id
     * @param clubMemberRequest ClubMemberRequest role of the user
     * @return ClubAccountView that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public ClubAccountView addMemberToClub(
            Long clubId,
            Long accountId,
            ClubMemberRequest clubMemberRequest
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        clubAccountDao.getClubMembership(club, accountId, false, false);

        return ClubAccountView.of(
                clubAccountDao.create(
                        clubId,
                        accountId,
                        clubMemberRequest.getRole(),
                        true
                )
                        .flatMap(clubAccountDao::getById)
                        .get()
                        .orElseThrow(BadRequestException::new)
        );
    }

    /**
     * Leaves club. Also used to cancel requests to join.
     * @param clubId Long id for club
     * @param account Account of the requester
     * @return Void
     * @throws BadRequestException if club slug is not valid or account is member already
     * @throws NotAuthorizedException if not authorized
     * */
    public Void leaveClub(Long clubId, Account account) {
        Club club = clubDao.getById(clubId).get().orElseThrow(NotFoundException::new);
        clubAccountDao.getClubMembership(club, account.getId(), true, null);

        return clubAccountDao.delete(clubId, account.getId()).get();
    }

    /**
     * Creates request to join club.
     * @param clubId Long id for club
     * @param account Account of the requester
     * @param memberRequest ClubMemberRequest requested role
     * @return ClubAccountView that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * @throws NotAuthorizedException if not authorized
     * */
    public ClubAccountView requestMemberToClub(
            Long clubId,
            Account account,
            ClubMemberRequest memberRequest
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        clubAccountDao.getClubMembership(club, account.getId(), false, false);

        if (!account.getAccountRole().equals(AccountRole.admin)
                && memberRequest.getRole().equals(ClubAccountRole.club)
        ) {
            throw new NotAuthorizedException("User has no access.");
        }

        return ClubAccountView.of(
                clubAccountDao.create(
                        clubId,
                        account.getId(),
                        memberRequest.getRole(),
                        false
                )
                        .flatMap(clubAccountDao::getById)
                        .get()
                        .orElseThrow(BadRequestException::new)
        );
    }

    /**
     * Accepts request to join club. Requires ClubAccount with accepted.
     * @param clubId Long id for club
     * @param accountId Integer account id
     * @return ClubAccountView that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public ClubAccountView acceptMemberToClub(
            Long clubId,
            Long accountId,
            Account account
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), true);
        }

        clubAccountDao.getClubMembership(club, accountId, true, false);

        return ClubAccountView.of(
                clubAccountDao.updateStatus(
                        clubId,
                        accountId,
                        true
                )
                        .flatMap(ignored -> clubAccountDao.findAccount(
                                ClubAccountQuery.of(club.getId(), accountId))
                        )
                        .get()
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Removes member from club.
     * @param clubId Long id for club
     * @param accountId Integer account id
     * @param accepted Boolean whether account to remove or cancel
     * @param account Account of the requester
     * @return Void
     * @throws BadRequestException if club slug is not valid or account is member already
     * @throws NotAuthorizedException if user is not authorized to do this
     * */
    public Void removeMemberFromClub(
            Long clubId,
            Long accountId,
            Account account,
            Boolean accepted
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        if (account.getAccountRole() != AccountRole.admin) {
            clubAccountDao.checkRole(clubId, account.getId(), true);
        }

        clubAccountDao.getClubMembership(club, accountId, true, accepted);

        return clubAccountDao.delete(clubId, accountId).get();
    }
}
