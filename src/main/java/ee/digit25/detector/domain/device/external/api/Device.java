package ee.digit25.detector.domain.device.external.api;

import lombok.*;

@Value
public class Device {

    private String mac;
    private Boolean isBlacklisted;
}
