package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.TicketRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.TicketResponseDTO;
import com.sarfaraz.elearning.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/student/{studentId}")
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_GUEST')")
    public TicketResponseDTO createTicket(@PathVariable Long studentId, @RequestBody TicketRequestDTO ticketRequest) {
        return ticketService.createTicket(ticketRequest, studentId);
    }

    @GetMapping("alltickets")
    public List<TicketResponseDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponseDTO getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}/resolve")
    public TicketResponseDTO resolveTicket(@PathVariable Long id) {
        return ticketService.resolveTicket(id);
    }

    // New endpoint to get resolved tickets
    @GetMapping("/resolved")
    public List<TicketResponseDTO> getResolvedTickets() {
        return ticketService.getResolvedTickets();
    }

    // New endpoint to get tickets by student ID
    @GetMapping("/student/{studentId}")
    public List<TicketResponseDTO> getTicketsByUserId(@PathVariable Long studentId) {
        return ticketService.getTicketsByStudentId(studentId);
    }
}
