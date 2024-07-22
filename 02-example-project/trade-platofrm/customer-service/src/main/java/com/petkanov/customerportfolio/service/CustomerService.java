package com.petkanov.customerportfolio.service;

import com.petkanov.customerportfolio.dto.CustomerInformation;
import com.petkanov.customerportfolio.entity.Customer;
import com.petkanov.customerportfolio.exceptions.ApplicationExceptions;
import com.petkanov.customerportfolio.repository.CustomerRepository;
import com.petkanov.customerportfolio.repository.PortfolioItemRepository;
import com.petkanov.customerportfolio.mapper.EntityDtoMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public CustomerService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return this.customerRepository.findById(customerId)
                                      .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                                      .flatMap(this::buildCustomerInformation);
    }

    private Mono<CustomerInformation> buildCustomerInformation(Customer customer) {
        return this.portfolioItemRepository.findAllByCustomerId(customer.getId())
                                           .collectList()
                                           .map(list -> EntityDtoMapper.toCustomerInformation(customer, list));
    }

}
