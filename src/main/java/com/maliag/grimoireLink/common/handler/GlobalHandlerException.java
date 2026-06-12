package com.maliag.grimoireLink.common.handler;

    import com.maliag.grimoireLink.common.exceptions.ConflictException;
    import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;
    import com.maliag.grimoireLink.common.exceptions.UnauthorizedException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.validation.ConstraintViolationException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.http.converter.HttpMessageNotReadableException;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

    import java.time.LocalDateTime;
    import java.util.stream.Collectors;

    @ControllerAdvice

    public class GlobalHandlerException {
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
            String message = "Invalid json body";
            return ResponseEntity.badRequest()
                    .body(new ApiErrorResponse(400, message, request.getRequestURI(), LocalDateTime.now()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
            String message = ex.getBindingResult().getFieldErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest()
                    .body(new ApiErrorResponse(400, message, request.getRequestURI(), LocalDateTime.now()));
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
            String message = ex.getConstraintViolations().stream()
                    .map(x -> x.getMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest()
                    .body(new ApiErrorResponse(400, message, request.getRequestURI(), LocalDateTime.now()));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404, ex.getMessage(), request.getRequestURI(), LocalDateTime.now()));
        }

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<ApiErrorResponse> handleConflict(ConflictException ex, HttpServletRequest request) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse(409, ex.getMessage(), request.getRequestURI(), LocalDateTime.now()));
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ApiErrorResponse> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse(401, ex.getMessage(), request.getRequestURI(), LocalDateTime.now()));
        }

        ///exception de spring
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
            String message = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
            return ResponseEntity.badRequest()
                    .body(new ApiErrorResponse(400, message, request.getRequestURI(), LocalDateTime.now()));
        }

    }
