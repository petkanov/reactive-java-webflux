package com.petkanov.customerportfolio.dto;

import com.petkanov.customerportfolio.domain.Ticker;
import com.petkanov.customerportfolio.domain.TradeAction;

public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {

    public Integer totalPrice(){
        return price * quantity;
    }

}
