package org.example.fullstack;

import io.vertx.pgclient.PgException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Provider
public class RestExceptionHandler implements ExceptionMapper<HibernateException> {

    private static final String SQL_UNIQUE_VIOLATION_ERROR = "23505";

    @Override
    public Response toResponse(HibernateException exception) {
        if (hasExceptionInChain(exception, ObjectNotFoundException.class)) {
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
        if (hasExceptionInChain(exception, ConstraintViolationException.class)
                || hasSQLErrorCode(exception, SQL_UNIQUE_VIOLATION_ERROR)
                || hasExceptionInChain(exception, StaleObjectStateException.class)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("\"" + exception.getMessage() + "\"")
                .build();
    }

    private static boolean hasExceptionInChain(Throwable throwable, Class<? extends Throwable> exceptionClass) {
        return getExceptionInChain(throwable, exceptionClass).isPresent();
    }


    private static boolean hasSQLErrorCode(Throwable throwable, String code) {
        return getExceptionInChain(throwable, SQLException.class)
                .filter(ex -> Objects.equals(ex.getSQLState(), code))
                .isPresent();
    }

    private static <T extends Throwable> Optional<T> getExceptionInChain(Throwable throwable, Class<T> exceptionClass) {
        while (throwable != null) {
            if (exceptionClass.isInstance(throwable)) {
                return Optional.of((T) throwable);
            }
            throwable = throwable.getCause();
        }
        return Optional.empty();
    }
}
