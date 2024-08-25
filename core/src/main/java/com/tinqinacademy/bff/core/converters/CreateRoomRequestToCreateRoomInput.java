package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.createroom.CreateRoomRequest;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomRequestToCreateRoomInput extends AbstractConverter<CreateRoomRequest, CreateRoomInput> {
    @Override
    protected Class<CreateRoomInput> getTargetClass() {
        return CreateRoomInput.class;
    }

    @Override
    protected CreateRoomInput doConvert(CreateRoomRequest source) {
        CreateRoomInput result = CreateRoomInput.builder()
                .roomNo(source.getRoomNo())
                .bathroomType(source.getBathroomType())
                .bedSizes(source.getBedSizes())
                .floor(source.getFloor())
                .price(source.getPrice())
                .build();

        return result;
    }
}
