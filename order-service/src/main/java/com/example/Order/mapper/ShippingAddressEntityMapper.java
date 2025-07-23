package com.example.Order.mapper;

import com.example.Order.dto.ShippingAddressDto;
import com.example.Order.model.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class ShippingAddressEntityMapper {


    public ShippingAddress toEntity(ShippingAddressDto dto){
        if(dto == null) {
            return null;
        }

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setRecipientName(dto.recipientName());
        shippingAddress.setAddressLine1(dto.addressLine1());
        shippingAddress.setAddressLine2(dto.addressLine2());
        shippingAddress.setCity(dto.city());
        shippingAddress.setState(dto.state());
        shippingAddress.setPostalCode(dto.postalCode());
        shippingAddress.setCountry(dto.country());
        shippingAddress.setPhoneNumber(dto.phoneNumber());
        return shippingAddress;
    }


    public void updateEntity(ShippingAddress entity, ShippingAddressDto dto){
        if(entity == null || dto == null) {
            return;
        }

        entity.setRecipientName(dto.recipientName());
        entity.setAddressLine1(dto.addressLine1());
        entity.setAddressLine2(dto.addressLine2());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setPostalCode(dto.postalCode());
        entity.setCountry(dto.country());
        entity.setPhoneNumber(dto.phoneNumber());

    }

}
