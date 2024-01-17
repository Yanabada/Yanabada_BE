package kr.co.fastcampus.yanabada.domain.product.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductPatchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSaveRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.request.ProductSearchRequest;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductIdResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductInfoResponse;
import kr.co.fastcampus.yanabada.domain.product.dto.response.ProductSummaryPageResponse;
import kr.co.fastcampus.yanabada.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{productId}")
    public ResponseBody<ProductInfoResponse> getProduct(
        @PathVariable("productId") Long productId
    ) {
        return ResponseBody.ok(
            productService.getProductById(productId)
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

    @PatchMapping("/{productId}")
    public ResponseBody<ProductIdResponse> modifyProduct(
        @PathVariable("productId") Long productId,
        @RequestBody ProductPatchRequest request
    ) {
        return ResponseBody.ok(
            productService.updateProduct(1L, productId, request)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseBody<Void> deleteProduct(
        @PathVariable("productId") Long productId
    ) {
        productService.cancelProduct(1L, productId);
        return ResponseBody.ok();
    }
}
