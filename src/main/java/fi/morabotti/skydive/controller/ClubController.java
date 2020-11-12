package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.ClubAccountDao;
import fi.morabotti.skydive.dao.ClubDao;
import fi.morabotti.skydive.dao.ClubProfileDao;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.domain.ClubDomain;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.view.club.ClubCreationRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Singleton
public class ClubController {
    private final ClubDomain clubDomain;

    private final ClubDao clubDao;
    private final ClubProfileDao clubProfileDao;
    private final ClubAccountDao clubAccountDao;

    @Inject
    public ClubController(
            ClubDomain clubDomain,
            ClubDao clubDao,
            ClubProfileDao clubProfileDao,
            ClubAccountDao clubAccountDao
    ) {
        this.clubDomain = clubDomain;
        this.clubDao = clubDao;
        this.clubProfileDao = clubProfileDao;
        this.clubAccountDao = clubAccountDao;
    }

    /**
     * Creates new club.
     * @param creationRequest ClubCreationRequest used for information of club
     * @param creatorAccount Account that is created the club
     * @return Club that is created
     * @throws BadRequestException if club is not created
     * */
    public Club createClub(
            ClubCreationRequest creationRequest,
            Account creatorAccount
    ) {
        Club newClub = clubDomain.createClub(
                creationRequest,
                creatorAccount
        );

        return clubDao.create(newClub)
                .flatMap(club -> clubProfileDao.create(
                        clubDomain.createClubProfile(
                                club,
                                creationRequest
                        )
                ))
                .flatMap(clubDao::getById)
                .get()
                .orElseThrow(BadRequestException::new);
    }

    /**
     * Creates new club.
     * @param clubSlug String slug for club
     * @param accountId Integer account id
     * @return ClubAccount that is created
     * @throws BadRequestException if club slug is not valid or account is member already
     * */
    public ClubAccount addMemberToClub(
            String clubSlug,
            Long accountId,
            ClubAccountRole accountRole
    ) {
        Club club = getClubMembership(clubSlug, accountId);

        return clubDao.getClubBySlug(clubSlug)
                .flatMap(c -> clubAccountDao.create(
                        club.getId(),
                        accountId,
                        accountRole
                ))
                .flatMap(clubAccountDao::getById)
                .get()
                .orElseThrow(BadRequestException::new);
    }

    private Club getClubMembership(String clubSlug, Long accountId) {
        Optional<Club> club = clubDao.checkClubBySlug(clubSlug);

        if (!club.isPresent()) {
            throw new NotFoundException("Club slug is not valid.");
        }

        if (clubAccountDao.checkIfMember(clubSlug, accountId)) {
            throw new BadRequestException("Account is already a member.");
        }

        return club.get();
    }
}
