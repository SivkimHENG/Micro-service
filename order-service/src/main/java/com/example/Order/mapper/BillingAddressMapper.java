package com.example.Order.mapper;

import com.example.Order.dto.BillingAddressDto;
import com.example.Order.model.BillingAddress;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BillingAddressMapper implements Function<BillingAddress, BillingAddressDto> {

    @Override
    public BillingAddressDto apply(BillingAddress billingAddress) {
        if(billingAddress == null) {
            return null;
        }

        return new BillingAddressDto(
                billingAddress.getRecipientName(),
                billingAddress.getAddressLine1(),
                billingAddress.getAddressLine2(),
                billingAddress.getCity(),
                billingAddress.getState(),
                billingAddress.getPostalCode(),
                billingAddress.getCountry());

    }
}
