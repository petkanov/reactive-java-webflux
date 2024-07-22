package com.petkanov.aggregator.dto;

import com.petkanov.aggregator.domain.Ticker;
import com.petkanov.aggregator.domain.TradeAction;

public record TradeRequest(Ticker ticker,
                           TradeAction action,
                           Integer quantity) {
}
