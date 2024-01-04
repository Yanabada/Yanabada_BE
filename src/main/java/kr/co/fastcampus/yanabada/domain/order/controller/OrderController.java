package kr.co.fastcampus.yanabada.domain.order.controller;

import kr.co.fastcampus.yanabada.common.response.ResponseBody;
import kr.co.fastcampus.yanabada.domain.order.dto.request.OrderSaveRequest;
import kr.co.fastcampus.yanabada.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseBody<Void> addOrder(@RequestBody OrderSaveRequest request) {
        orderService.saveOrder(request);
        return ResponseBody.ok();
    }

}
