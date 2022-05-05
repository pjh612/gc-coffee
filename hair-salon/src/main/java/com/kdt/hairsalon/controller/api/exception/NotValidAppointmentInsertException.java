package com.kdt.hairsalon.controller.api.exception;


public class NotValidAppointmentInsertException extends RuntimeException {

    public NotValidAppointmentInsertException(String msg) {
        super(msg);
    }

    public NotValidAppointmentInsertException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
