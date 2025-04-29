package com.example.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Actor;
import com.example.utils.DatabaseConnection;

public class ActorRepository implements Repository<Actor> {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    @Override
    public Actor getByID(Integer id) {
        String sql = "SELECT * FROM sakila.actor WHERE actor_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Actor(
                        rs.getInt("actor_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Actor actor) {
        String checkSql = "SELECT COUNT(*) FROM sakila.actor WHERE actor_id = ?";
        String insertSql = "INSERT INTO sakila.actor (first_name, last_name) VALUES (?, ?)";
        String updateSql = "UPDATE sakila.actor SET first_name = ?, last_name = ? WHERE actor_id = ?";

        try (Connection conn = getConnection()) {
            if (actor.getActorID() != null) {
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, actor.getActorID());
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setString(1, actor.getFirstName());
                                updateStmt.setString(2, actor.getLastName());
                                updateStmt.setInt(3, actor.getActorID());
                                updateStmt.executeUpdate();
                                return;
                            }
                        }
                    }
                }
            }

            // Insert si no existe el ID o es null
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, actor.getFirstName());
                insertStmt.setString(2, actor.getLastName());
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM sakila.actor WHERE actor_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}