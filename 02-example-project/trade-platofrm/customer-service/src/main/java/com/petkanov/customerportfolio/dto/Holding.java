package com.petkanov.customerportfolio.dto;

import com.petkanov.customerportfolio.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
