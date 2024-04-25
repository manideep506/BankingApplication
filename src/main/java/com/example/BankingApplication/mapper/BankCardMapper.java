package com.example.BankingApplication.mapper;

import com.example.BankingApplication.model.BankCard;
import com.example.BankingApplication.model.Customer;
import com.example.BankingApplication.model.dto.BankCardRequest;
import com.example.BankingApplication.model.dto.BankCardResponse;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.Random;

@Component
public class BankCardMapper {

//    The bank identification number, a set digits at the start of the credit card number
    private static final int BANK_IDENTIFICATION_NUMBER = 4000;
//    Total length of the credit card number
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int CVC_LENGTH = 3;
    private final Random random = new Random(System.currentTimeMillis());

    public BankCardResponse mapToDto(BankCard bankCard) {
        BankCardResponse bankCardResponse = new BankCardResponse();
        bankCardResponse.setCardNumber(bankCard.getCardNumber());
        bankCardResponse.setCvc(bankCard.getCvc());
        bankCardResponse.setEnabled(bankCard.isEnabled());
        bankCardResponse.setExpiryDate(bankCard.getExpiryDate());
        bankCardResponse.setPin(bankCard.getPin());

        return bankCardResponse;
    }

    public BankCard map(BankCardRequest bankCardRequest, Customer customer) {
        BankCard bankCard = new BankCard();
        bankCard.setCardNumber(generateCardNumber());
        bankCard.setCvc(generateCVC());
        bankCard.setPin(bankCardRequest.getPin());
        bankCard.setExpiryDate(LocalDate.now().plusYears(5));
        bankCard.setEnabled(true);
        bankCard.setCustomer(customer);

        return bankCard;
    }

//    generates cvc number as 3 digits
    private String generateCVC() {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<CVC_LENGTH; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        return builder.toString();
    }

//    generates random card number, digits starts with bank identification number
    private String generateCardNumber() {
        StringBuilder builder = new StringBuilder().append(BANK_IDENTIFICATION_NUMBER);

//        generates last 12 numbers of the card number
        for(int i=0; i<CARD_NUMBER_LENGTH - 4; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        return builder.toString();
    }
}
