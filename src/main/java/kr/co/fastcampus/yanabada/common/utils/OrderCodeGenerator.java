package kr.co.fastcampus.yanabada.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import kr.co.fastcampus.yanabada.common.exception.RandomCodeLengthRangeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCodeGenerator {

    private static final int RANDOM_CODE_LENGTH = 12;
    private static final String DATE_PATTERN = "yyMMdd";

    public static String generate() {
        return generateDateCode(LocalDate.now()) + generateRandomCode(RANDOM_CODE_LENGTH);
    }

    private static String generateDateCode(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    private static String generateRandomCode(int length) {
        String uuid = UUID.randomUUID().toString();

        if (length <= 0 || length > uuid.length()) {
            throw new RandomCodeLengthRangeException();
        }
        return uuid.substring(uuid.length() - length);
    }
}
