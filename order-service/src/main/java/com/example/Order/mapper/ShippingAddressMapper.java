package com.example.Order.mapper;

import com.example.Order.dto.ShippingAddressDto;
import com.example.Order.model.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class ShippingAddressMapper implements Function<ShippingAddress, ShippingAddressDto> {


    @Override
    public ShippingAddressDto apply(ShippingAddress shippingAddress){
        if(shippingAddress == null) {
            throw new RuntimeException("Shipping Address is null.");
        }

        return new ShippingAddressDto(
                shippingAddress.getRecipientName(),
                shippingAddress.getAddressLine1(),
                shippingAddress.getAddressLine2(),
                shippingAddress.getCity(),
                shippingAddress.getState(),
                shippingAddress.getPostalCode(),
                shippingAddress.getCountry(),
                shippingAddress.getPhoneNumber());
    }




}
