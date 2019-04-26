package com.bendevnull.DiscordBot.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bendevnull.DiscordBot.Logging.BotLoggerFactory;

import org.slf4j.Logger;

public class DatabaseHandler {
	
	private String databasePath;
	private Logger logger;
	private Connection conn;
	
	public DatabaseHandler(String path) {
		this.logger = BotLoggerFactory.createLogger(this);
		this.databasePath = path;
	}
	
	public void connect() {
		try {
			this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", this.databasePath));
			this.logger.info(String.format("Opened database at %s.", this.databasePath));
		} catch (SQLException e) {
			this.logger.error(e.toString());
		}
	}

	public void createTable(String name, String[] keys) {
		List<String> keyList = Arrays.asList(keys);
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s (\n", name);
		for (String s : keyList) {
			if (keyList.indexOf(s) == keyList.size() - 1) {
				sql += String.format("%s\n", s);
			} else {
				sql += String.format("%s,\n", s);
			}
		}
		sql += ");";
		// this.logger.info(sql);
		try {
			Statement stmt = this.conn.createStatement();
			stmt.execute(sql);
		} catch(SQLException e) {
			this.logger.error(e.toString());
		}

		this.verifyTable(name, keys);
	}

	public void verifyTable(String name, String[] keys) {
		List<String> keyList = Arrays.asList(keys);
		String baseAlter = "ALTER TABLE %s ADD %s";
		String baseAlterDrop = "ALTER TABLE %s RENAME COLUMN %s TO %s_old_%d";
		String basePragma = "PRAGMA table_info(%s)";
		String baseSchema = "SELECT sql FROM sqlite_master WHERE tbl_name = '%s' AND type = 'table'";

		String schema = "";

		try {
			Statement stmt = this.conn.createStatement();
			String filledSchema = String.format(baseSchema, name);
			ResultSet rs = stmt.executeQuery(filledSchema);
			schema = rs.getString("sql");
		} catch(SQLException e) {
			this.logger.error(e.toString());
			return;
		}

		List<String> schemaKeys = new ArrayList<>(Arrays.asList(schema.split("\n")));
		schemaKeys.remove(0);
		schemaKeys.remove(schemaKeys.size() - 1);
		
		for (String schemaKey : schemaKeys) {
			schemaKey = schemaKey.replace(",", "");
			String schemaName = schemaKey.split(" ")[0];
			for (String key : keys) {
				String keyName = key.split(" ")[0];
				if (keyName.equalsIgnoreCase(schemaName)) {
					if (!key.equalsIgnoreCase(schemaKey)) {
						try {
							Statement stmt = this.conn.createStatement();
							stmt.execute(String.format(baseAlterDrop, name, schemaName, schemaName, new Date().getTime()));
							stmt.execute(String.format(baseAlter, name, schemaKey));
						} catch(SQLException e) {
							this.logger.error(e.toString());
							return;
						}
					}
				}
			}
		}

		// for (String s : keyList) {
		// 	// String column = s.split(" ")[0];
		// 	String sql = String.format(baseAlter, name, s);

		// 	try {
		// 		Statement stmt = this.conn.createStatement();
		// 		stmt.execute(sql);
		// 	} catch(SQLException e) {
		// 		this.logger.error(e.toString());
		// 	}
		// }
	}

	public ResultSet get(String table, String key) {
		String sql = String.format("SELECT %s FROM %s;", key, table);
		try {
			Statement stmt = this.conn.createStatement();
			return stmt.executeQuery(sql);
		} catch(SQLException e) {
			this.logger.error(e.toString());
			return null;
		}
	}

	public ResultSet get(String table, String key, String conditional) {
		String sql = String.format("SELECT %s FROM %s WHERE %s;", key, table, conditional);
		try {
			Statement stmt = this.conn.createStatement();
			return stmt.executeQuery(sql);
		} catch(SQLException e) {
			this.logger.error(e.toString());
			return null;
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}

	public void finish() {
		try {
			this.conn.close();
		} catch(SQLException e) {
			this.logger.error(e.getStackTrace().toString());
		}
	}

}
