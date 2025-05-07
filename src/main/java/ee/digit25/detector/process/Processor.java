package ee.digit25.detector.process;

import ee.digit25.detector.domain.transaction.TransactionValidator;
import ee.digit25.detector.domain.transaction.external.TransactionRequester;
import ee.digit25.detector.domain.transaction.external.TransactionVerifier;
import ee.digit25.detector.domain.transaction.external.api.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Processor {

    private final int TRANSACTION_BATCH_SIZE = 10;
    private final TransactionRequester requester;
    private final TransactionValidator validator;
    private final TransactionVerifier verifier;

    @Scheduled(fixedDelay = 1000) //Runs every 1000 ms after the last run
    public void process() {
        log.info("Starting to process a batch of transactions of size {}", TRANSACTION_BATCH_SIZE);

        List<Transaction> transactions = requester.getUnverified(TRANSACTION_BATCH_SIZE);

        for (Transaction transaction : transactions) {
            if (validator.isLegitimate(transaction)) {
                log.info("Legitimate transaction {}", transaction.getId());
                verifier.verify(transaction);
            } else {
                log.info("Not legitimate transaction {}", transaction.getId());
                verifier.reject(transaction);
            }
        }
    }
}
