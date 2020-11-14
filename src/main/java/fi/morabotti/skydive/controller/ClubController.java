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
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.club.ClubAccountQuery;
import fi.morabotti.skydive.view.club.ClubCreationRequest;
import fi.morabotti.skydive.view.club.ClubMemberRequest;
import fi.morabotti.skydive.view.club.ClubQuery;
import fi.morabotti.skydive.view.club.ClubView;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
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
     * Fetches clubs member.
     * @param paginationQuery PaginationQuery used to paginate response
     * @param slug String identifies wanted club
     * @return PaginationResponse of AccountView
     * */
    public PaginationResponse<AccountView> getMembers(
            PaginationQuery paginationQuery,
            String slug
    ) {
        Club club = clubDao.getClubBySlug(slug).get()
                .orElseThrow(NotFoundException::new);

        ClubAccountQuery query = new ClubAccountQuery()
                .withClubId(club.getId());

        return PaginationResponse.create(
                clubAccountDao.fetchClubMembers(
                        paginationQuery,
                        new ClubAccountQuery().withClubId(club.getId())
                ).stream()
                        .map(AccountView::of)
                        .collect(Collectors.toList()),
                clubAccountDao.fetchClubMembers(query)
        );
    }

    /**
     * Fetches club by slug.
     * @param slug String identifies wanted club
     * @return ClubView
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
     * */
    public ClubView getClub(Long id) {
        return ClubView.of(
                clubDao.getById(id).get()
                        .orElseThrow(NotFoundException::new)
        );
    }

    /**
     * Creates new club.
     * @param creationRequest ClubCreationRequest used for information of club
     * @param creatorAccount Account that is created the club
     * @return Club that is created
     * @throws BadRequestException if club is not created
     * */
    public ClubView createClub(
            ClubCreationRequest creationRequest,
            Account creatorAccount
    ) {
        return ClubView.of(
                clubDao.create(
                        clubDomain.createClub(
                                creationRequest,
                                creatorAccount
                        )
                )
                        .flatMap(club -> clubProfileDao.create(
                                clubDomain.createClubProfile(
                                        club,
                                        creationRequest
                                )
                        ))
                        .flatMap(clubDao::getById)
                        .get()
                        .orElseThrow(BadRequestException::new)
        );
    }

    /**
     * Deletes club, its profile, clears planes and accounts.
     * @param slug String slug for club
     * @throws NotFoundException if club is not found with slug
     * */
    public Void deleteClub(String slug) {
        Club club = clubDao.getClubBySlug(slug).get()
                .orElseThrow(NotFoundException::new);

        return clubAccountDao.deleteByClub(club.getId())
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
     * @param slug String slug for club
     * @param accountId Integer account id
     * @param accountRole AccountRole role of the user
     * @return ClubAccount that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public ClubAccount addMemberToClub(
            String slug,
            Long accountId,
            ClubAccountRole accountRole
    ) {
        Club club = getClubMembership(slug, accountId, false);

        return clubDao.getClubBySlug(slug)
                .flatMap(c -> clubAccountDao.create(
                        club.getId(),
                        accountId,
                        accountRole,
                        true
                ))
                .flatMap(clubAccountDao::getById)
                .get()
                .orElseThrow(BadRequestException::new);
    }

    /**
     * Creates request to join club.
     * @param slug String slug for club
     * @param account Account of the requester
     * @param memberRequest ClubMemberRequest requested role
     * @return AccountView that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public AccountView requestMemberToClub(
            String slug,
            Account account,
            ClubMemberRequest memberRequest
    ) {
        Club club = getClubMembership(slug, account.getId(), false);

        if (!account.getAccountRole().equals(AccountRole.admin)
                && memberRequest.getRole().equals(ClubAccountRole.club)
        ) {
            throw new NotAuthorizedException("User has no access.");
        }

        return AccountView.of(
                clubDao.getClubBySlug(slug)
                        .flatMap(c -> clubAccountDao.create(
                                club.getId(),
                                account.getId(),
                                memberRequest.getRole(),
                                false
                        ))
                        .flatMap(clubAccountDao::getById)
                        .get()
                        .orElseThrow(BadRequestException::new)
        );
    }

    /**
     * Accepts request to join club. Requires ClubAccount with accepted.
     * @param slug String slug for club
     * @param accountId Integer account id
     * @return AccountView that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public AccountView acceptMemberToClub(
            String slug,
            Long accountId
    ) {
        Club club = getClubMembership(slug, accountId, true);

        return AccountView.of(
                clubDao.getClubBySlug(slug)
                        .flatMap(c -> clubAccountDao.updateStatus(
                                club.getId(),
                                accountId,
                                true
                        ))
                        .flatMap(clubAccountDao::getById)
                        .get()
                        .orElseThrow(BadRequestException::new)
        );
    }

    /**
     * Removes member from club.
     * @param slug String slug for club
     * @param accountId Integer account id
     * @return ClubAccount that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public Void removeMemberFromClub(String slug, Long accountId) {
        Club club = getClubMembership(slug, accountId, true);

        return clubDao.getClubBySlug(slug)
                .flatMap(c -> clubAccountDao.delete(
                        club.getId(),
                        accountId
                    )
                ).get();
    }

    private Club getClubMembership(String clubSlug, Long accountId, Boolean shouldBeMember) {
        Club club = clubDao.checkClubBySlug(clubSlug)
                .orElseThrow(NotFoundException::new);

        Boolean isMember = clubAccountDao.checkIfMember(club.getId(), accountId);

        if (!isMember && shouldBeMember) {
            throw new BadRequestException("Account is not member.");
        }
        else if (isMember && !shouldBeMember) {
            throw new BadRequestException("Account is already a member.");
        }

        return club;
    }
}
