package org.example.repository;

import org.example.dto.ClientFilterDTO;
import org.example.model.Cashier;
import org.example.model.Game;
import org.example.model.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketInMemoryRepository implements TicketRepository {
    private final Map<Integer, Ticket> ticketMap = new HashMap<>();

    private final GameRepository gameRepository;
    private final CashierRepository cashierRepository;

    public TicketInMemoryRepository(GameRepository gameRepository, CashierRepository cashierRepository) {
        this.gameRepository = gameRepository;
        this.cashierRepository = cashierRepository;
    }

    @Override
    public Iterable<Ticket> getTicketsSoldForGame(Game game) {
        return ticketMap.values().stream()
                .filter(ticket -> ticket.getGame().getId().equals(game.getId()))
                .toList();
    }

    @Override
    public Iterable<Ticket> getTicketsForClient(ClientFilterDTO filter) {
        if (filter == null) {
            return findAll();
        }
        return ticketMap.values().stream()
                .filter(ticket -> {
                    boolean matchesName = (filter.getName() == null || ticket.getCustomerName().equals(filter.getName()));
                    boolean matchesAddress = (filter.getAddress() == null || ticket.getCustomerAddress().equals(filter.getAddress()));
                    return matchesName && matchesAddress;
                })
                .toList();
    }

    @Override
    public Iterable<Ticket> findAll() {
        return ticketMap.values();
    }

    @Override
    public Optional<Ticket> findById(Integer id) {
        return Optional.ofNullable(ticketMap.get(id));
    }

    @Override
    public Ticket save(Ticket entity) {
        int id = getnewId();
        entity.setId(id);


        Optional<Game> gameOpt = gameRepository.findById(entity.getGame().getId());
        Optional<Cashier> cashierOpt = cashierRepository.findById(entity.getSeller().getId());

        if (gameOpt.isEmpty()) {
            throw new IllegalArgumentException("Game with ID " + entity.getGame().getId() + " not found.");
        } else {
            entity.setGame(gameOpt.get());
        }
        if (cashierOpt.isEmpty()) {
            throw new IllegalArgumentException("Cashier with ID " + entity.getSeller().getId() + " not found.");
        } else {
            entity.setSeller(cashierOpt.get());
        }
        ticketMap.put(entity.getId(), entity);
        return entity;
    }

    private int getnewId() {
        AtomicInteger id = new AtomicInteger(-1);
        ticketMap.values().forEach(t -> {
           if (t.getId() > id.get()) {
               id.set(t.getId());
           }
        });
        return id.get() + 1;
    }

    @Override
    public Optional<Ticket> deleteById(Integer id) {
        Ticket removedTicket = ticketMap.remove(id);
        return Optional.ofNullable(removedTicket);
    }

    @Override
    public Ticket update(Ticket entity) {
        if (ticketMap.containsKey(entity.getId())) {
            ticketMap.put(entity.getId(), entity);

            Optional<Game> gameOpt = gameRepository.findById(entity.getGame().getId());
            Optional<Cashier> cashierOpt = cashierRepository.findById(entity.getSeller().getId());

            if (gameOpt.isEmpty()) {
                throw new IllegalArgumentException("Game with ID " + entity.getGame().getId() + " not found.");
            }
            if (cashierOpt.isEmpty()) {
                throw new IllegalArgumentException("Cashier with ID " + entity.getSeller().getId() + " not found.");
            }

            return entity;
        } else {
            throw new IllegalArgumentException("Ticket with ID " + entity.getId() + " not found.");
        }
    }
}