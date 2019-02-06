package com.wojtek.parkingmeter.Ticket;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketDTO ticketToTicketDTO(TicketEntity ticketEntity);

    TicketEntity ticketDTOToTocket(TicketDTO ticketDTO);

    TicketStopDTO ticketEntityToTicketPutDTO(TicketEntity ticketEntity);

}
