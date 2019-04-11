package com.bendevnull.DiscordBot.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

import net.dv8tion.jda.core.entities.Message;

public class LoggerDAO {

    private String tableName = "messages";
	private DatabaseHandler handler;
	
	private Logger logger;

    public LoggerDAO(DatabaseHandler handler) {
        this.handler = handler;
        this.logger = BotLoggerFactory.createLogger(this);

        this.handler.createTable(this.tableName, new String[] {
            "id TEXT NOT NULL",
            "guild TEXT NOT NULL",
            "channel TEXT NOT NULL",
            "author TEXT NOT NULL",
            "time TEXT NOT NULL",
            "raw TEXT NOT NULL",
            "rendered TEXT NOT NULL",
            "edited INTEGER NOT NULL",
            "deleted INTEGER NOT NULL",
            "editraw TEXT",
            "editrendered TEXT"
        });
    }

    public void logMessage(Message message) {
        String sql = String.format("INSERT INTO %s(id,guild,channel,author,time,raw,rendered,edited,deleted,editraw,editrendered) VALUES(?,?,?,?,?,?,?,?,?,?,?)", this.tableName);

        try {
            Connection conn = this.handler.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, message.getId());
            stmt.setString(2, message.getGuild().getId());
            stmt.setString(3, message.getChannel().getId());
            stmt.setString(4, message.getAuthor().getId());
            stmt.setString(5, message.getCreationTime().toString());
            stmt.setString(6, message.getContentRaw());
            stmt.setString(7, message.getContentDisplay());
            stmt.setInt(8, 0);
            stmt.setInt(9, 0);
            stmt.setString(10, "");
            stmt.setString(11, "");

            stmt.executeUpdate();
        } catch(SQLException e) {
            this.logger.error(e.toString());
            e.printStackTrace();
        }
    }

    public void editMessage(Message message) {
        String sql = String.format("UPDATE %s SET edited = 1, editraw = '%s', editrendered = '%s' WHERE id = '%s'", this.tableName, message.getContentRaw(), message.getContentDisplay(), message.getId());

        try {
			Connection conn = this.handler.getConnection();
			Statement stmt = conn.createStatement();

			stmt.execute(sql);
		} catch (SQLException e) {
            this.logger.error(e.toString());
            e.printStackTrace();
		}
    }

    public void deleteMessage(String messageId) {
        String sql = String.format("UPDATE %s SET deleted = 1 WHERE id = '%s'", this.tableName, messageId);

        try {
			Connection conn = this.handler.getConnection();
			Statement stmt = conn.createStatement();

			stmt.execute(sql);
		} catch (SQLException e) {
			this.logger.error(e.toString());
		}
    }
}