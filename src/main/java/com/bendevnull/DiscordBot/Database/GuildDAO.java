package com.bendevnull.DiscordBot.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bendevnull.DiscordBot.Program;
import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

import net.dv8tion.jda.core.entities.Guild;

public class GuildDAO {
	
	private String tableName = "guilds";
	private DatabaseHandler handler;
	
	private Logger logger;
	
	public GuildDAO(DatabaseHandler handler) {
		this.handler = handler;
		this.logger = BotLoggerFactory.createLogger(this);

		this.handler.createTable(this.tableName, new String[]{
			"id text NOT NULL",
			"prefix text NOT NULL",
			"word text NOT NULL"
		});
	}
	
	public void create(Guild guild) {
		DAOResult res = this.exists(guild);
		if (res.success == false || res.result == true) {
			return;
		}

		String sql = String.format("INSERT INTO %s(id,prefix) VALUES(?,?)", this.tableName);

		try {
			Connection conn = this.handler.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, guild.getId());
			stmt.setString(2, Program.getConfig().prefix);

			stmt.executeUpdate();
			this.logger.info("Created database entry for guild ID " + guild.getId());
		} catch(SQLException e) {
			this.logger.error(e.toString());
		}
	}

	public void remove(Guild guild) {
		String sql = String.format("DELETE FROM %s WHERE id = '%s'", this.tableName, guild.getId());

		try {
			Connection conn = this.handler.getConnection();
			Statement stmt = conn.createStatement();

			stmt.execute(sql);
		} catch (SQLException e) {
			this.logger.error(e.toString());
		}
	}

	public void updatePrefix(Guild guild, String prefix) {
		String sql = String.format("UPDATE %s SET prefix = '%s' WHERE id = '%s'", this.tableName, prefix, guild.getId());

		try {
			Connection conn = this.handler.getConnection();
			Statement stmt = conn.createStatement();

			stmt.execute(sql);
		} catch (SQLException e) {
			this.logger.error(e.toString());
		}
	}

	public String getPrefix(Guild guild) {
		String sql = String.format("SELECT prefix FROM %s WHERE id = '%s'", this.tableName, guild.getId());
		
		try {
			Connection conn = this.handler.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return rs.getString("prefix");
			} else {
				return Program.getConfig().prefix;
			}
		} catch (SQLException e) {
			this.logger.error(e.toString());
			return Program.getConfig().prefix;
		}
	}
	
	public DAOResult exists(Guild guild) {
		ResultSet res = this.handler.get(this.tableName, "id");
		try {
			if (res != null) {
				while (res.next()) {
					if (res.getString("id").equalsIgnoreCase(guild.getId())) {
						return new DAOResult(true, true);
					};
				}
			}
			return new DAOResult(true, false);
		} catch(SQLException e) {
			this.logger.error(e.toString());
			return new DAOResult(false, false);
		}
	}
}
