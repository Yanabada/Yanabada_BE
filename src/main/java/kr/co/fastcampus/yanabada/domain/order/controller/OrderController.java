package kr.co.fastcampus.yanabada.domain.order.controller;

import java.util.List;
import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.common.security.PrincipalDetails;
import kr.co.fastcampus.yanabada.domain.order.dto.request.OrderSaveRequest;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderInfoResponse;
import kr.co.fastcampus.yanabada.domain.order.dto.response.OrderSummaryResponse;
import kr.co.fastcampus.yanabada.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseBody<Void> addOrder(@RequestBody OrderSaveRequest request) {
        orderService.saveOrder(request);
        return ResponseBody.ok();
    }

    @GetMapping("/can-sell")
    public ResponseBody<List<OrderSummaryResponse>> getSellableOrders(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseBody.ok(
            orderService.getSellableOrders(principalDetails.id())
        );
    }

    @GetMapping("/{orderId}")
    public ResponseBody<OrderInfoResponse> getOrderInfo(
        @PathVariable Long orderId,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseBody.ok(orderService.getOrderInfo(orderId, principalDetails.id()));
    }
}
