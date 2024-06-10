package com.users.exceptions;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private LocalDateTime timeStamp;
    private String message;
    private int errorCode;
}
