package com.bmt.user.service.advice;

import com.common.exception.InvalidOtpException;
import com.common.exception.NoUserInformationException;
import com.common.exception.UserAlreadyRegisteredException;
import com.common.exception.UserNotFoundException;
import com.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }*/

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        Map<String, String> errorDetails = Map.of("reason", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("User not found", errorDetails, request.getRequestURI()));
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, HttpServletRequest request) {
        Map<String, String> errorDetails = Map.of("reason", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.failure("User Already Registered", errorDetails, request.getRequestURI()));
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidOtpException(InvalidOtpException ex, HttpServletRequest request) {
        Map<String, String> errorDetails = Map.of("reason", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("Invalid OTP", errorDetails, request.getRequestURI()));
    }

    @ExceptionHandler(NoUserInformationException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoUserInformationException(NoUserInformationException ex, HttpServletRequest request) {
        Map<String, String> errorDetails = Map.of("reason", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("No User Information", errorDetails, request.getRequestURI()));
    }

    /**
     * Method to handle incorrect arguments received in request body using validation framework
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errorDetails = Map.of("reason", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("Invalid OTP", errorDetails, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex, HttpServletRequest request) {
        Map<String, String> errorDetails = Map.of("reason", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("An unexpected error occurred", errorDetails, request.getRequestURI()));
    }
}
