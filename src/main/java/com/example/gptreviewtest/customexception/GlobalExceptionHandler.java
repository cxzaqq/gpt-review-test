package com.example.gptreviewtest.customexception;

import com.example.gptreviewtest.customexception.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateException(DuplicateException e) {
        log.error("Duplicate Exception", e);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setErrorCode("DUPLICATE_RESOURCE");
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailSendingException(EmailSendingException e) {
        log.error("EmailSendingException", e);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setErrorCode("EMAIL_SEND_FAILED");
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserTypeException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidUserTypeException(InvalidUserTypeException e) {
        log.error("InvalidUserTypeException", e);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setErrorCode("INVALID_USER_TYPE");
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
