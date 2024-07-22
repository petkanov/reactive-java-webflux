package com.petkanov.aggregator.controller;

import com.petkanov.aggregator.service.CustomerPortfolioService;
import com.petkanov.aggregator.validator.RequestValidator;
import com.petkanov.aggregator.dto.CustomerInformation;
import com.petkanov.aggregator.dto.StockTradeResponse;
import com.petkanov.aggregator.dto.TradeRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerPortfolioController {

    private final CustomerPortfolioService customerPortfolioService;

    public CustomerPortfolioController(CustomerPortfolioService customerPortfolioService) {
        this.customerPortfolioService = customerPortfolioService;
    }

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
        return this.customerPortfolioService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<TradeRequest> mono) {
        return mono.transform(RequestValidator.validate())
                   .flatMap(req -> this.customerPortfolioService.trade(customerId, req));
    }

}
