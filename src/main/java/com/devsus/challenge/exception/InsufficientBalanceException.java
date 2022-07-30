package com.devsus.challenge.exception;

import lombok.Data;

@Data
public class InsufficientBalanceException extends RuntimeException{

    private Long initialBalance;
    private Long newAmount;

    public InsufficientBalanceException(Long initialBalance, Long newAmount){

        super(String.format("El monto total de $'%s' es insuficiente para debitar $'%s' ", initialBalance, newAmount));

        this.initialBalance = initialBalance;
        this.newAmount = newAmount;

    }
}
