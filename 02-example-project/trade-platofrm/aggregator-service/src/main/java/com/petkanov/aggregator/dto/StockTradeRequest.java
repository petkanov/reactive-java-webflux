package com.petkanov.aggregator.dto;


import com.petkanov.aggregator.domain.Ticker;
import com.petkanov.aggregator.domain.TradeAction;

public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {

}
