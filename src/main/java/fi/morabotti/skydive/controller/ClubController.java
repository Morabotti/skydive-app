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
import fi.morabotti.skydive.model.ClubAccount;
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
import java.util.Optional;
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
     * @return ClubAccountView of account if member
     * @throws NotFoundException if club not found
     * */
    public ClubAccountView getMember(
            Long clubId,
            Long accountId
    ) {
        getClubMembership(clubId, accountId, true, true);

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
     * @param clubId Long identifies wanted club
     * @return PaginationResponse of ClubAccountView
     * @throws NotFoundException if club not found
     * */
    public PaginationResponse<ClubAccountView> getMembers(
            PaginationQuery paginationQuery,
            Long clubId
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        ClubAccountQuery query = new ClubAccountQuery()
                .withClubId(club.getId());

        return PaginationResponse.create(
                clubAccountDao.fetchClubAccounts(
                        paginationQuery,
                        query
                ).stream()
                        .map(ClubAccountView::of)
                        .collect(Collectors.toList()),
                clubAccountDao.fetchClubMembersLength(query)
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
     * @return List of Plane
     * */
    public List<Plane> getPlanes(Long clubId) {
        return planeDao.fetchPlanes(clubId);
    }

    /**
     * Fetch plane by plane Id.
     * @param id Long identifies wanted plane
     * @return Plane
     * @throws NotFoundException if plane not found
     * */
    public Plane getPlane(Long id) {
        return planeDao.getById(id).get()
                .orElseThrow(NotFoundException::new);
    }

    /**
     * Creates new plane for club.
     * @param clubId Long identifies club.
     * @param plane Plane to be created
     * @return newly created Plane
     * @throws InternalServerErrorException if something went wrong
     * */
    public Plane createPlane(Long clubId, Plane plane) {
        return planeDao.create(clubId, plane)
                .flatMap(planeDao::getById)
                .get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Updates plane.
     * @param clubId Long identifies club.
     * @param plane Plane to be updated
     * @return updated Plane
     * @throws InternalServerErrorException if something went wrong
     * */
    public Plane updatePlane(Long clubId, Plane plane) {
        return planeDao.update(clubId, plane).get()
                .orElseThrow(InternalServerErrorException::new);
    }

    /**
     * Removes plane by Id.
     * @param planeId Long identifies plane.
     * @return Void
     * */
    public Void deletePlane(Long planeId) {
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
     * @return ClubView of updated club
     * @throws BadRequestException if club is not created
     * @throws NotFoundException if club is not found
     * */
    public ClubView updateClub(
            Long clubId,
            ClubInformationRequest informationRequest
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

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
     * @throws NotFoundException if club is not found with slug
     * */
    public Void deleteClub(Long clubId) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

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
        getClubMembership(clubId, accountId, false, false);

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
     * Creates request to join club.
     * @param clubId Long id for club
     * @param account Account of the requester
     * @param memberRequest ClubMemberRequest requested role
     * @return ClubAccountView that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public ClubAccountView requestMemberToClub(
            Long clubId,
            Account account,
            ClubMemberRequest memberRequest
    ) {
        getClubMembership(clubId, account.getId(), false, false);

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
            Long accountId
    ) {
        Club club = getClubMembership(clubId, accountId, true, false);

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
     * @return Void
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public Void removeMemberFromClub(Long clubId, Long accountId, Boolean accepted) {
        getClubMembership(clubId, accountId, true, accepted);

        return clubAccountDao.delete(clubId, accountId).get();
    }

    private Club getClubMembership(
            Long clubId,
            Long accountId,
            Boolean shouldBeMember,
            Boolean shouldBeAccepted
    ) {
        Club club = clubDao.getById(clubId).get()
                .orElseThrow(NotFoundException::new);

        Optional<ClubAccount> account = clubAccountDao.findAccount(
                ClubAccountQuery.of(clubId, accountId)
        ).get();

        if (!account.isPresent() && shouldBeMember) {
            throw new BadRequestException("Account is not member.");
        }
        else if (account.isPresent() && account.get().getAccepted() == null && shouldBeAccepted) {
            throw new BadRequestException("Account is not accepted into club yet.");
        }
        else if (account.isPresent() && account.get().getAccepted() != null && !shouldBeAccepted) {
            throw new BadRequestException("Account is already accepted to club.");
        }
        else if (account.isPresent() && !shouldBeMember) {
            throw new BadRequestException("Account is already a member.");
        }

        return club;
    }
}
