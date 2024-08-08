package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.bookroom.BookRoomRequest;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomRequestToBookRoomInput extends AbstractConverter<BookRoomRequest, BookRoomInput> {
    @Override
    protected Class<BookRoomInput> getTargetClass() {
        return BookRoomInput.class;
    }

    @Override
    protected BookRoomInput doConvert(BookRoomRequest source) {
        BookRoomInput result = BookRoomInput.builder()
                .roomId(source.getRoomId())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNo(source.getPhoneNo())
                .username(source.getUsername())
                .build();

        return result;
    }
}
