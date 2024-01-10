package kr.co.fastcampus.yanabada.domain.product.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSaveRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductIdResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductSummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseBody<ProductIdResponse> addProduct(
        @RequestBody @Valid ProductSaveRequest request
    ) {
        return ResponseBody.ok(
            productService.saveProduct(1L, request)
        );
    }

    @GetMapping
    public ResponseBody<ProductSummaryPageResponse> getProductsBySearchRequest(
        @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseBody.ok(
            productService.getProductsBySearchRequest(request)
        );
    }
}
