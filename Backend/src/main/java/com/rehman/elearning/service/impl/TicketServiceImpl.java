package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.TicketStatus;
import com.rehman.elearning.model.Ticket;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.TicketRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.TicketRequestDTO;
import com.rehman.elearning.rest.dto.outbound.TicketResponseDTO;
import com.rehman.elearning.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequest, Long userId) {
        // Find the student by ID
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create a new ticket
        Ticket ticket = new Ticket();
        ticket.setSubject(ticketRequest.getSubject());
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setStudent(student);
        ticket.setCreatedAt(LocalDateTime.now());

        // Save the ticket to the repository
        Ticket savedTicket = ticketRepository.save(ticket);

        // Map to response DTO
        return mapToResponseDTO(savedTicket);
    }

    @Override
    public List<TicketResponseDTO> getResolvedTickets() {
        List<Ticket> resolvedTickets = ticketRepository.findByResolvedAtNotNull();
        return resolvedTickets.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDTO> getTicketsByStudentId(Long userId) {
        List<Ticket> tickets = ticketRepository.findByStudent_UserId(userId);
        return tickets.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return mapToResponseDTO(ticket);
    }

    @Override
    public TicketResponseDTO resolveTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setResolvedAt(LocalDateTime.now());
        ticket.setStatus(TicketStatus.CLOSED);
        ticketRepository.save(ticket); // Update the ticket status

        return mapToResponseDTO(ticket);
    }

    // Manual mapping method from Ticket to TicketResponseDTO
    private TicketResponseDTO mapToResponseDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setTicketId(ticket.getId()); // Set the ticket ID from the entity
        dto.setUserId(ticket.getStudent().getUserId()); // Set the student entity as userId in DTO
        dto.setSubject(ticket.getSubject());
        dto.setDescription(ticket.getDescription());
        dto.setStatus(ticket.getStatus().name());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setResolvedAt(ticket.getResolvedAt());
        return dto;
    }
}