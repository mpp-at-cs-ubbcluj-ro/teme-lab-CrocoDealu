package org.example.repository;

import org.example.model.Cashier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CashierInMemoryRepository implements CashierRepository{
    private final Map<Integer, Cashier> cashierMap = new HashMap<>();

    @Override
    public Optional<Cashier> findByUsername(String username) {
        return cashierMap.values().stream()
                .filter(cashier -> cashier.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Iterable<Cashier> findAll() {
        return cashierMap.values();
    }

    @Override
    public Optional<Cashier> findById(Integer id) {
        return Optional.ofNullable(cashierMap.get(id));
    }

    @Override
    public Cashier save(Cashier entity) {
        cashierMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Cashier> deleteById(Integer id) {
        Cashier removedCashier = cashierMap.remove(id);
        return Optional.ofNullable(removedCashier);
    }

    @Override
    public Cashier update(Cashier entity) {
        if (cashierMap.containsKey(entity.getId())) {
            cashierMap.put(entity.getId(), entity);
            return entity;
        } else {
            throw new IllegalArgumentException("Cashier with ID " + entity.getId() + " not found.");
        }
    }
}
