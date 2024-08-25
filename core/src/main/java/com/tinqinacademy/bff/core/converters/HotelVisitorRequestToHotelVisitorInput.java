package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.hotelvisitor.HotelVisitorRequest;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import org.springframework.stereotype.Component;

@Component
public class HotelVisitorRequestToHotelVisitorInput extends AbstractConverter<HotelVisitorRequest, HotelVisitorInput> {
    @Override
    protected Class<HotelVisitorInput> getTargetClass() {
        return HotelVisitorInput.class;
    }

    @Override
    protected HotelVisitorInput doConvert(HotelVisitorRequest source) {
        HotelVisitorInput result = HotelVisitorInput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .birthday(source.getBirthday())
                .phoneNo(source.getPhoneNo())
                .civilNumber(source.getCivilNumber())
                .idIssueDate(source.getIdIssueDate())
                .idIssueAuthority(source.getIdIssueAuthority())
                .idValidity(source.getIdValidity())
                .build();

        return result;
    }
}
