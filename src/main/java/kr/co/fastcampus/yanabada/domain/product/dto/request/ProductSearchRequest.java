package kr.co.fastcampus.yanabada.domain.product.dto.request;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchCategory;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchOption;
import kr.co.fastcampus.yanabada.domain.product.dto.request.enums.ProductSearchOrderCondition;

public record ProductSearchRequest(
    String keyword,
    LocalDate from,
    LocalDate to,
    Integer adult,
    Integer child,
    Double smallX,
    Double smallY,
    Double bigX,
    Double bigY,
    Boolean isHidingSoldOut,
    ProductSearchOrderCondition order,
    ProductSearchCategory category,
    List<ProductSearchOption> options,
    Integer page,
    Integer size
) {

}
