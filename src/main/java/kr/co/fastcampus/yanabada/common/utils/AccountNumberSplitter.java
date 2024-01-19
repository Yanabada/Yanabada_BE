package kr.co.fastcampus.yanabada.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountNumberSplitter {

    private static final int LENGTH = 4;

    public static String split(String accountNumber) {
        int beginIndex = accountNumber.length() - LENGTH;

        return accountNumber.substring(beginIndex);
    }
}
