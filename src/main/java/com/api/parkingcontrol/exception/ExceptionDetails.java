package com.api.parkingcontrol.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
public class ExceptionDetails {
    protected String title;
    protected long status;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;
}
