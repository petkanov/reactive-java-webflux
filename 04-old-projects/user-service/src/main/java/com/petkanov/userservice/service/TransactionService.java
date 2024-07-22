package com.petkanov.userservice.service;

import com.petkanov.userservice.repository.UserRepository;
import com.petkanov.userservice.repository.UserTransactionRepository;
import com.petkanov.userservice.dto.TransactionRequestDto;
import com.petkanov.userservice.dto.TransactionResponseDto;
import com.petkanov.userservice.dto.TransactionStatus;
import com.petkanov.userservice.entity.UserTransaction;
import com.petkanov.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                        .filter(Boolean::booleanValue)
                        .map(b -> EntityDtoUtil.toEntity(requestDto))
                        .flatMap(this.transactionRepository::save)
                        .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                        .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }

}
