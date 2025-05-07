package ee.digit25.detector.domain.person;

import ee.digit25.detector.domain.person.external.PersonRequester;
import ee.digit25.detector.domain.person.external.api.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonValidator {

    public final PersonRequester requester;

    private boolean hasWarrantIssued(String personCode, Person person) {
        log.info("Cheking if person ({}) has a warrant issued", personCode);

        return person.getWarrantIssued();
    }

    private boolean hasContract(String personCode, Person person) {
        log.info("Cheking if person ({}) has a contract", personCode);

        return person.getHasContract();
    }

    private boolean isBlacklisted(String personCode, Person person) {
        log.info("Cheking if person ({}) is blacklisted", personCode);

        return person.getBlacklisted();
    }

    public boolean isValid(String personCode, Person person) {
        return !hasWarrantIssued(personCode, person) && hasContract(personCode, person) && !isBlacklisted(personCode, person);
    }
}
