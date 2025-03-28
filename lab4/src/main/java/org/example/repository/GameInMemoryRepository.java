package org.example.repository;

import org.example.model.Game;
import org.example.model.Ticket;

import java.util.*;

public class GameInMemoryRepository implements GameRepository {
    private final Map<Integer, Game> gameMap = new HashMap<>();

    @Override
    public Iterable<Game> findGamesForTickets(Iterable<Ticket> tickets) {
        Set<Game> games = new HashSet<>();

        for (Ticket ticket : tickets) {
            Integer gameId = ticket.getGame().getId();
            Game game = gameMap.get(gameId);
            if (game != null) {
                games.add(game);
            }
        }

        return games;
    }

    @Override
    public Iterable<Game> findAll() {
        // Return all games in the repository
        return gameMap.values();
    }

    @Override
    public Optional<Game> findById(Integer id) {
        return Optional.ofNullable(gameMap.get(id));
    }

    @Override
    public Game save(Game entity) {
        gameMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Game> deleteById(Integer id) {
        Game removedGame = gameMap.remove(id);
        return Optional.ofNullable(removedGame);
    }

    @Override
    public Game update(Game entity) {
        if (gameMap.containsKey(entity.getId())) {
            gameMap.put(entity.getId(), entity);
            return entity;
        } else {
            throw new IllegalArgumentException("Game with ID " + entity.getId() + " not found.");
        }
    }
}
