package kr.co.fastcampus.yanabada.common.jwt.dto;

public record TokenExpiredResponse(
    boolean isNeededRefresh
) {

}
