package ch.burg.deskriptor.service.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass().getCanonicalName());

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> notFound(final EntityNotFoundException e) {
        logger.error("Missing Permissions", e);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new ExceptionDto("Not Found", e), headers, NOT_FOUND);
    }

    public static class ExceptionDto {
        private final String message;
        private final Throwable e;

        public ExceptionDto() {
            this(null, null);
        }

        public ExceptionDto(final String message, final Throwable e) {
            this.message = message;
            this.e = e;
        }

        public String getMessage() {
            return message;
        }

        public Throwable getE() {
            return e;
        }
    }

    public static class EntityNotFoundException extends RuntimeException {
    }
}
