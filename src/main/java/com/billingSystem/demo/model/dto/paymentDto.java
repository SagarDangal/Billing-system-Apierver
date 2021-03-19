package com.billingSystem.demo.model.dto;

public class paymentDto {
    private Integer senderAccountNumber;
    private Integer receiverAccountNumber;
    private String Amount;

    public Boolean paymentDto(Integer senderAccountNumber,Integer receiverAccountNumber,String Amount){
        this.receiverAccountNumber=receiverAccountNumber;
        this.senderAccountNumber = senderAccountNumber;
        this.Amount =Amount;
        return true;
    }


}
