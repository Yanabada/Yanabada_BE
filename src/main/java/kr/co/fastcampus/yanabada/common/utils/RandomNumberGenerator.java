package kr.co.fastcampus.yanabada.common.utils;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomNumberGenerator {

    private static final Random RANDOM = new Random();

    public static int generate(int origin, int bound) {
        return RANDOM.nextInt(origin, bound);
    }
}
