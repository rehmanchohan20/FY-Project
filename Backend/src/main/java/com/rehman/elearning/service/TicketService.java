package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.TicketRequestDTO;
import com.rehman.elearning.rest.dto.outbound.TicketResponseDTO;

import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket(TicketRequestDTO ticketRequest, Long studentId);
    List<TicketResponseDTO> getResolvedTickets();
    List<TicketResponseDTO> getTicketsByStudentId(Long userId);
    List<TicketResponseDTO> getAllTickets();
    TicketResponseDTO getTicketById(Long id);
    TicketResponseDTO resolveTicket(Long id);
}
