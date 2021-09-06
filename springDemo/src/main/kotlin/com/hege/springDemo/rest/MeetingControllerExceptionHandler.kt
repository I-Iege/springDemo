package com.hege.springDemo.rest

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class MeetingControllerExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConflict() {
        // Nothing to do
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataRetrievalFailureException::class)
    fun handleNotFound() {
        // Nothing to do
    }
}