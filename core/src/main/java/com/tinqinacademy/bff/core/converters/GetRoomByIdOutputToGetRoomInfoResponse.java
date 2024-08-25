package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfoResponse;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import org.springframework.stereotype.Component;

@Component
public class GetRoomByIdOutputToGetRoomInfoResponse extends AbstractConverter<GetRoomByIdOutput, GetRoomInfoResponse> {
    @Override
    protected Class<GetRoomInfoResponse> getTargetClass() {
        return GetRoomInfoResponse.class;
    }

    @Override
    protected GetRoomInfoResponse doConvert(GetRoomByIdOutput source) {
        GetRoomInfoResponse result = GetRoomInfoResponse.builder()
                .roomId(source.getRoomId())
                .price(source.getPrice())
                .bathroomType(source.getBathroomType())
                .bedCount(source.getBedCount())
                .bedSizes(source.getBedSizes())
                .datesOccupied(source.getDatesOccupied())
                .floor(source.getFloor())
                .build();

        return result;
    }
}
