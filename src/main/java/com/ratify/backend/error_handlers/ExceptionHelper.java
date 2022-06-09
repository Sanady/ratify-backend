package com.ratify.backend.error_handlers;

import com.ratify.backend.error_handlers.exceptions.ForbiddenActionException;
import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.error_handlers.exceptions.ItemAlreadyExistsException;
import com.ratify.backend.error_handlers.exceptions.NotFoundException;
import com.ratify.backend.error_handlers.exceptions.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import com.ratify.backend.models.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@RestControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = {InvalidInputException.class })
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleInvalidInputException(InvalidInputException ex) {
        List<String> errors = List.of(ex.getMessage());
        logger.error("Invalid input exception: {}", ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), errors, new Date());
    }

    @ExceptionHandler(value = {UnauthorizedException.class })
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleUnauthorizedException(UnauthorizedException ex) {
        List<String> errors = List.of(ex.getMessage());
        logger.error("Unauthorized exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }

    @ExceptionHandler(value = {ItemAlreadyExistsException.class })
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Error handleItemAlreadyExistsException(ItemAlreadyExistsException ex) {
        List<String> errors = List.of(ex.getMessage());
        logger.error("Item already exists exception: {}", ex.getMessage());
        return new Error(HttpStatus.CONFLICT.value(), errors, new Date());
    }

    @ExceptionHandler(value = {NotFoundException.class })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(NotFoundException ex) {
        List<String> errors = List.of(ex.getMessage());
        logger.error("Not found exception: {}", ex.getMessage());
        return new Error(HttpStatus.NOT_FOUND.value(), errors, new Date());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException  ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
            logger.error("Argument not valid exception: {}", error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
            logger.error("Argument not valid exception: {}", error.getDefaultMessage());
        }
        return new Error(HttpStatus.BAD_REQUEST.value(), errors, new Date());
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleAuthenticationException(AuthenticationException ex) {
        List<String> errors = List.of(ex.getMessage());
        logger.error("Authentication exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }

    @ExceptionHandler(value = {ForbiddenActionException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Error handleForbiddenActionException(ForbiddenActionException ex) {
        List<String> errors = List.of(ex.getMessage());
        logger.error("Forbidden action exception: {}", ex.getMessage());
        return new Error(HttpStatus.FORBIDDEN.value(), errors, new Date());
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<String> errors = List.of(Objects.requireNonNull(ex.getMessage()));
        logger.error("Http message not readable exception: {}", ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), errors, new Date());
    }

    @ExceptionHandler(value = {SignatureException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleSignatureException(SignatureException ex) {
        List<String> errors = List.of(Objects.requireNonNull(ex.getMessage()));
        logger.error("Singature exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleMalformedJwtException(MalformedJwtException ex) {
        List<String> errors = List.of(Objects.requireNonNull(ex.getMessage()));
        logger.error("Malformed jwt exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleExpiredJwtException(ExpiredJwtException ex) {
        List<String> errors = List.of(Objects.requireNonNull(ex.getMessage()));
        logger.error("Expired jwt exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }

    @ExceptionHandler(value = {UnsupportedJwtException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleUnsupportedJwtException(UnsupportedJwtException ex) {
        List<String> errors = List.of(Objects.requireNonNull(ex.getMessage()));
        logger.error("Unsupported jwt exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Error handleIllegalArgumentException(IllegalArgumentException ex) {
        List<String> errors = List.of(Objects.requireNonNull(ex.getMessage()));
        logger.error("Illegal argument exception: {}", ex.getMessage());
        return new Error(HttpStatus.UNAUTHORIZED.value(), errors, new Date());
    }
}
