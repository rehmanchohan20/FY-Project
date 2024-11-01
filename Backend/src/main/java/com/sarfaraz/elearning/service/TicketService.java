package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.model.Ticket;
import com.sarfaraz.elearning.rest.dto.inbound.TicketRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.TicketResponseDTO;

import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket(TicketRequestDTO ticketRequest, Long studentId);
    List<TicketResponseDTO> getResolvedTickets();
    List<TicketResponseDTO> getTicketsByStudentId(Long userId);
    List<TicketResponseDTO> getAllTickets();
    TicketResponseDTO getTicketById(Long id);
    TicketResponseDTO resolveTicket(Long id);
}
