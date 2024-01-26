package kr.co.fastcampus.yanabada.common.exception.handler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.co.fastcampus.yanabada.common.exception.BaseException;
import kr.co.fastcampus.yanabada.common.exception.EmailAuthTimeExpiredException;
import kr.co.fastcampus.yanabada.common.exception.EmailDuplicatedException;
import kr.co.fastcampus.yanabada.common.exception.EmailSendFailedException;
import kr.co.fastcampus.yanabada.common.exception.FcmAccessTokenGetFailedException;
import kr.co.fastcampus.yanabada.common.exception.FcmMessageSendFailedException;
import kr.co.fastcampus.yanabada.common.exception.JsonProcessFailedException;
import kr.co.fastcampus.yanabada.common.exception.NotMatchedProviderNameException;
import kr.co.fastcampus.yanabada.common.exception.OkHttp3RequestFailedException;
import kr.co.fastcampus.yanabada.common.exception.EmailNotVerifiedException;
import kr.co.fastcampus.yanabada.common.exception.TokenExpiredException;
import kr.co.fastcampus.yanabada.common.jwt.dto.TokenExpiredResponse;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BaseException.class)
    public ResponseBody<Void> handleBaseException(BaseException e) {
        log.warn("[BaseException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBody<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("[NoHandlerFoundException] Message = {}", e.getMessage());
        return ResponseBody.fail("잘못된 URL입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseBody<Void> handleValidException(MethodArgumentNotValidException e) {
        log.warn("[MethodArgumentNotValidException] Message = {}",
            NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = getSortedFieldErrors(bindingResult);

        StringBuilder response = new StringBuilder();
        fieldErrors.forEach(
            error -> response.append("\n[Request error]").append(error.getDefaultMessage())
        );
        return ResponseBody.fail(response.toString());
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

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBody<Void> badCredentialsException(
        BadCredentialsException e
    ) {
        log.error("[BadCredentialsException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBody<TokenExpiredResponse> tokenExpiredException(
        TokenExpiredException e
    ) {
        log.error("[TokenExpiredException] Message = {}", e.getMessage());
        return ResponseBody.fail(
            e.getMessage(),
            new TokenExpiredResponse(true)
        );
    }

    @ExceptionHandler(EmailSendFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody<Void> emailSendFailedException(
        EmailSendFailedException e
    ) {
        log.error("[EmailSendFailedException] Message = {}", e.getMessage());
        return ResponseBody.error(e.getMessage());
    }

    @ExceptionHandler(EmailDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody<Void> emailDuplicatedException(
        EmailDuplicatedException e
    ) {
        log.error("[EmailDuplicatedException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ExceptionHandler(JsonProcessFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody<Void> jsonProcessFailedException(
        JsonProcessFailedException e
    ) {
        log.error("[JsonProcessFailedException] Message = {}", e.getMessage());
        return ResponseBody.error(e.getMessage());
    }

    @ExceptionHandler(OkHttp3RequestFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody<Void> okHttp3RequestFailedException(
        OkHttp3RequestFailedException e
    ) {
        log.error("[OkHttp3RequestFailedException] Message = {}", e.getMessage());
        return ResponseBody.error(e.getMessage());
    }

    @ExceptionHandler(FcmMessageSendFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody<Void> fcmMessageSendFailedException(
        FcmMessageSendFailedException e
    ) {
        log.error("[FcmMessageSendFailedException] Message = {}", e.getMessage());
        return ResponseBody.error(e.getMessage());
    }

    @ExceptionHandler(FcmAccessTokenGetFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody<Void> fcmAccessTokenGetFailedException(
        FcmAccessTokenGetFailedException e
    ) {
        log.error("[FcmAccessTokenGetFailedException] Message = {}", e.getMessage());
        return ResponseBody.error(e.getMessage());
    }

    @ExceptionHandler(NotMatchedProviderNameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody<Void> notMatchedProviderNameException(
        NotMatchedProviderNameException e
    ) {
        log.error("[NotMatchedProviderNameException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ExceptionHandler(EmailAuthTimeExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody<Void> emailAuthTimeExpiredException(
        EmailAuthTimeExpiredException e
    ) {
        log.error("[EmailAuthTimeExpiredException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody<Void> emailNotVerifiedException(
        EmailNotVerifiedException e
    ) {
        log.error("[EmailNotVerifiedException] Message = {}", e.getMessage());
        return ResponseBody.fail(e.getMessage());
    }
}
