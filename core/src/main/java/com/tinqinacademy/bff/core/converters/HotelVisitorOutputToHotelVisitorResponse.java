package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.hotelvisitor.HotelVisitorResponse;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import org.springframework.stereotype.Component;

@Component
public class HotelVisitorOutputToHotelVisitorResponse extends AbstractConverter<HotelVisitorOutput, HotelVisitorResponse> {
    @Override
    protected Class<HotelVisitorResponse> getTargetClass() {
        return HotelVisitorResponse.class;
    }

    @Override
    protected HotelVisitorResponse doConvert(HotelVisitorOutput source) {
        HotelVisitorResponse result = HotelVisitorResponse.builder()
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
