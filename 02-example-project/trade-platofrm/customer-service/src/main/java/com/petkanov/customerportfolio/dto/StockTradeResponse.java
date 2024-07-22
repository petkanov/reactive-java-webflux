package com.petkanov.customerportfolio.dto;

import com.petkanov.customerportfolio.domain.Ticker;
import com.petkanov.customerportfolio.domain.TradeAction;

public record StockTradeResponse(Integer customerId,
                                 Ticker ticker,
                                 Integer price,
                                 Integer quantity,
                                 TradeAction action,
                                 Integer totalPrice,
                                 Integer balance) {
}
