package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.enums.MessageStatus;
import com.fptpolytechnic.duan1.model.Message;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository {


    public List<Message> findAll(int offset, int limit) {

        String query = """
                SELECT * FROM messages
                ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;

        List<Message> messages = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(this.toSimpleMessage(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public boolean add(Message message) {
        String query = """
                        INSERT INTO messages(sender, email, title, message, status, created_at)
                        VALUES (?, ?, ?, ?, ?, ?) 
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setString(1, message.getSender());
            ps.setString(2, message.getEmail());
            ps.setString(3, message.getTitle());
            ps.setString(4, message.getMessage());
            ps.setString(5, message.getStatus().toString());
            ps.setObject(6, LocalDateTime.now());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void updateStatus(Long id, MessageStatus status) {
        String query = """
                UPDATE messages
                SET status = ?
                WHERE id = ?;
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setString(1, status.name());
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Message getMessage(Long id) {

        String query = """
                SELECT * FROM messages
                WHERE id=?
                """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return this.toMessage(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean delete(Long id) {
        String query = """
                        DELETE FROM messages
                        WHERE id=?;
                """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Message toSimpleMessage(ResultSet rs) throws SQLException {
        return Message.builder()
                .id(rs.getLong("id"))
                .sender(rs.getString("sender"))
                .email(rs.getString("email"))
                .title(rs.getString("title"))
                .status(MessageStatus.valueOf(rs.getString("status")))
                .createAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }


    private Message toMessage(ResultSet rs) throws SQLException {
        return Message.builder()
                .id(rs.getLong("id"))
                .sender(rs.getString("sender"))
                .email(rs.getString("email"))
                .title(rs.getString("title"))
                .message(rs.getString("message"))
                .status(MessageStatus.valueOf(rs.getString("status")))
                .createAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
