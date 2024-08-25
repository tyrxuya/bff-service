package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoomRequest;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomRequestToUpdateRoomInput extends AbstractConverter<UpdateRoomRequest, UpdateRoomInput> {
    @Override
    protected Class<UpdateRoomInput> getTargetClass() {
        return UpdateRoomInput.class;
    }

    @Override
    protected UpdateRoomInput doConvert(UpdateRoomRequest source) {
        UpdateRoomInput result = UpdateRoomInput.builder()
                .roomId(source.getRoomId())
                .roomNo(source.getRoomNo())
                .bathroomType(source.getBathroomType())
                .bedSizes(source.getBedSizes())
                .floor(source.getFloor())
                .price(source.getPrice())
                .build();

        return result;
    }
}
