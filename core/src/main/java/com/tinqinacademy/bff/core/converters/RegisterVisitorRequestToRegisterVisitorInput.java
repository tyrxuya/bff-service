package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.hotelvisitor.HotelVisitorRequest;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitorRequest;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterVisitorRequestToRegisterVisitorInput extends AbstractConverter<RegisterVisitorRequest, RegisterVisitorInput> {
    private final HotelVisitorRequestToHotelVisitorInput hotelVisitorRequestToHotelVisitorInput;

    @Override
    protected Class<RegisterVisitorInput> getTargetClass() {
        return RegisterVisitorInput.class;
    }

    @Override
    protected RegisterVisitorInput doConvert(RegisterVisitorRequest source) {
        List<HotelVisitorInput> hotelVisitors = source.getHotelVisitors()
                .stream()
                .map(hotelVisitorRequestToHotelVisitorInput::doConvert)
                .toList();

        RegisterVisitorInput result = RegisterVisitorInput.builder()
                .bookingId(source.getBookingId())
                .hotelVisitors(hotelVisitors)
                .build();

        return result;
    }
}
