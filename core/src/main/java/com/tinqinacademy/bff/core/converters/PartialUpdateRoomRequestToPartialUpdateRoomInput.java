package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoomRequest;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class PartialUpdateRoomRequestToPartialUpdateRoomInput extends AbstractConverter<PartialUpdateRoomRequest, PartialUpdateRoomInput> {
    @Override
    protected Class<PartialUpdateRoomInput> getTargetClass() {
        return PartialUpdateRoomInput.class;
    }

    @Override
    protected PartialUpdateRoomInput doConvert(PartialUpdateRoomRequest source) {
        PartialUpdateRoomInput result = PartialUpdateRoomInput.builder()
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
