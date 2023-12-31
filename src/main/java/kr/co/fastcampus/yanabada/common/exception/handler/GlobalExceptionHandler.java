package kr.co.fastcampus.yanabada.common.exception.handler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.common.exception.BaseException;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BaseException.class)
    public ResponseBody<Void> handleBaseException(BaseException e) {
        log.warn("[BaseException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseBody<Void> handleValidException(MethodArgumentNotValidException e) {
        log.warn("[MethodArgumentNotValidException] Message = {}",
            NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = getSortedFieldErrors(bindingResult);

        String response = "[Request error] "
            + fieldErrors.stream()
            .map(fieldError -> String.format("%s",
                    fieldError.getDefaultMessage()
                )
            ).collect(Collectors.joining());
        return ResponseBody.fail(response);
    }

    private List<FieldError> getSortedFieldErrors(BindingResult bindingResult) {
        List<String> declaredFields = Arrays.stream(
                Objects.requireNonNull(bindingResult.getTarget()).getClass().getDeclaredFields())
            .map(Field::getName)
            .toList();

        return bindingResult.getFieldErrors().stream()
            .filter(fieldError -> declaredFields.contains(fieldError.getField()))
            .sorted(Comparator.comparingInt(fe -> declaredFields.indexOf(fe.getField())))
            .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseBody<Void> illegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseBody<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseBody<Void> methodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e
    ) {
        log.error("[MethodArgumentTypeMismatchException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody<Void> missingServletRequestParameterException(
        MissingServletRequestParameterException e
    ) {
        log.error("[MissingServletRequestParameterException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getParameterName()
            + " 파라미터가 빈 값이거나 잘못된 유형입니다.");
    }
}
