package com.wojtek.parkingmeter.mapper;

import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.model.TicketEntity;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-02-01T21:23:30+0100",
    comments = "version: 1.2.0.CR2, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public TicketDTO ticketToTicketDTO(TicketEntity ticketEntity) {
        if ( ticketEntity == null ) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setTicketType( ticketEntity.getTicketType() );
        ticketDTO.setStampStart( ticketEntity.getStampStart() );
        ticketDTO.setId( ticketEntity.getId() );
        ticketDTO.setCarNumberPlate( ticketEntity.getCarNumberPlate() );

        return ticketDTO;
    }

    @Override
    public TicketEntity ticketDTOToTocket(TicketDTO ticketDTO) {
        if ( ticketDTO == null ) {
            return null;
        }

        TicketEntity ticketEntity = new TicketEntity();

        ticketEntity.setId( ticketDTO.getId() );
        ticketEntity.setTicketType( ticketDTO.getTicketType() );
        ticketEntity.setStampStart( ticketDTO.getStampStart() );
        ticketEntity.setCarNumberPlate( ticketDTO.getCarNumberPlate() );

        return ticketEntity;
    }
}
