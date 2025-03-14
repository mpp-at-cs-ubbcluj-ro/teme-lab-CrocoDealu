package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.JdbcUtils;
import org.example.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UserRepository implements IRepository<Integer> {
    private final JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();
    public UserRepository(Properties properties) {
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public List<User> findAll() {
        logger.traceEntry();
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        Connection conn = jdbcUtils.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return users;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        logger.traceEntry();
        String sql = "SELECT * FROM Users WHERE id = ?";
        Connection conn = jdbcUtils.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<User> save(User entity) {
        logger.traceEntry();
        String sql = "INSERT INTO Users (username, password, name) VALUES (?, ?, ?)";
        Connection conn = jdbcUtils.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteById(Integer id) {
        Optional<User> userToDelete = findById(id);
        logger.traceEntry();
        if (userToDelete.isPresent()) {
            String sql = "DELETE FROM Users WHERE id = ?";
            Connection conn = jdbcUtils.getConnection();

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    return userToDelete;
                }
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        Optional<User> existingUser = findById(entity.getId());
        logger.traceEntry();
        if (existingUser.isPresent()) {
            String sql = "UPDATE Users SET username = ?, password = ?, name = ? WHERE id = ?";
            Connection conn = jdbcUtils.getConnection();

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, entity.getUsername());
                stmt.setString(2, entity.getPassword());
                stmt.setString(3, entity.getName());
                stmt.setInt(4, entity.getId());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    return Optional.of(entity);
                }
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        logger.traceExit();
        return Optional.empty();
    }

    public List<User> findByPassword(String password) {
        logger.traceEntry();
        String sql = "SELECT * FROM Users WHERE password = ?";
        Connection conn = jdbcUtils.getConnection();

        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name")
                ));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        logger.traceExit();
        return users;
    }
}
