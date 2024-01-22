package kr.co.fastcampus.yanabada.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class S3ImageUrlGenerator {

    @Value("${s3.end-point}")
    private static String s3EndPoint;

    public static String generate(String image) {
        return s3EndPoint + image;
    }
}
