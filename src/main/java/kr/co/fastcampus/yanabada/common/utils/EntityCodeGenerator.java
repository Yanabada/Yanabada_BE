package kr.co.fastcampus.yanabada.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityCodeGenerator {

    private static final int UUID_BEGIN_INDEX = 0;
    private static final int UUID_END_INDEX = 12;
    private static final String DATE_PATTERN = "yyMMdd";

    public static String generate() {
        return generateDateCode(LocalDate.now()) + generateUuidCode();
    }

    private static String generateDateCode(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    private static String generateUuidCode() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid.substring(UUID_BEGIN_INDEX, UUID_END_INDEX);
    }
}
