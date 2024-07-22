package com.petkanov.aggregator.dto;

import com.petkanov.aggregator.domain.Ticker;

public record StockPriceResponse(Ticker ticker,
                                 Integer price) {
}
