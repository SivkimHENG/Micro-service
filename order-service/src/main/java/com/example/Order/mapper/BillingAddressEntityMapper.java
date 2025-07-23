package com.example.Order.mapper;


import com.example.Order.dto.BillingAddressDto;
import com.example.Order.model.BillingAddress;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class BillingAddressEntityMapper  {

    public BillingAddress toEntity(BillingAddressDto dto) {
        if(dto == null) {
            return null;
        }

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setRecipientName(dto.recipientName());
        billingAddress.setAddressLine1(dto.addressLine1());
        billingAddress.setAddressLine2(dto.addressLine2());
        billingAddress.setCity(dto.city());
        billingAddress.setState(dto.state());
        billingAddress.setPostalCode(dto.postalCode());
        billingAddress.setCountry(dto.country());
        return billingAddress;
    }

    public void updateEntity(BillingAddress entity, BillingAddressDto dto){
        if(entity == null || dto == null){
            return ;
        }

        entity.setRecipientName(dto.recipientName());
        entity.setAddressLine1(dto.addressLine1());
        entity.setAddressLine2(dto.addressLine2());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setPostalCode(dto.postalCode());
        entity.setCountry(dto.country());
    }

}
