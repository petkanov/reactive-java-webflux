package com.petkanov.aggregator.dto;


import com.petkanov.aggregator.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
