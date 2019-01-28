package com.wojtek.parkingmeter.mapper;

import com.wojtek.parkingmeter.model.TicketEntity;
import com.wojtek.parkingmeter.model.DTO.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketDTO ticketToTicketDTO(TicketEntity ticketEntity);

    TicketEntity ticketDTOToTocket(TicketDTO ticketDTO);

}
