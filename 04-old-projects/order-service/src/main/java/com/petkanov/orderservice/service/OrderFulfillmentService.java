package com.petkanov.orderservice.service;

import com.petkanov.orderservice.client.UserClient;
import com.petkanov.orderservice.dto.PurchaseOrderResponseDto;
import com.petkanov.orderservice.dto.RequestContext;
import com.petkanov.orderservice.repository.PurchaseOrderRepository;
import com.petkanov.orderservice.client.ProductClient;
import com.petkanov.orderservice.dto.PurchaseOrderRequestDto;
import com.petkanov.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(this.orderRepository::save) // blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc){
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc){
        return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }

}
