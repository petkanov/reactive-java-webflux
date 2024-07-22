package com.petkanov.aggregator.dto;

import com.petkanov.aggregator.domain.Ticker;

import java.time.LocalDateTime;

public record PriceUpdate(Ticker ticker,
                          Integer price,
                          LocalDateTime time) {
}
