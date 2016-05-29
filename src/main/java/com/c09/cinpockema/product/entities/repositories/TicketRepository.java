package com.c09.cinpockema.product.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c09.cinpockema.product.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

}
