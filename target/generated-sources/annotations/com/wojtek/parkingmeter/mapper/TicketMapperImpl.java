package com.wojtek.parkingmeter.mapper;

import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import com.wojtek.parkingmeter.model.TicketEntity;
import java.math.BigDecimal;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-01-29T14:48:54+0100",
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
        if ( ticketEntity.getCharge() != null ) {
            ticketDTO.setCharge( ticketEntity.getCharge().doubleValue() );
        }
        ticketDTO.setStampStart( ticketEntity.getStampStart() );
        ticketDTO.setId( ticketEntity.getId() );

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
        ticketEntity.setCharge( BigDecimal.valueOf( ticketDTO.getCharge() ) );
        ticketEntity.setStampStart( ticketDTO.getStampStart() );

        return ticketEntity;
    }
}
