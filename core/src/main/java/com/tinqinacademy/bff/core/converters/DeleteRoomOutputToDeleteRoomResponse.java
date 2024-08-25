package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoomResponse;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoomOutputToDeleteRoomResponse extends AbstractConverter<DeleteRoomOutput, DeleteRoomResponse> {
    @Override
    protected Class<DeleteRoomResponse> getTargetClass() {
        return DeleteRoomResponse.class;
    }

    @Override
    protected DeleteRoomResponse doConvert(DeleteRoomOutput source) {
        DeleteRoomResponse result = DeleteRoomResponse.builder()
                .roomNo(source.getRoomNo())
                .bathroomType(source.getBathroomType())
                .bedSizes(source.getBedSizes())
                .floor(source.getFloor())
                .price(source.getPrice())
                .build();

        return result;
    }
}
