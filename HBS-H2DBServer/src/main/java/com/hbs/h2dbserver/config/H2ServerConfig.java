package com.hbs.h2dbserver.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class H2ServerConfig {

    private Server tcpServer;
    private Connection keepAliveConn;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        tcpServer = Server.createTcpServer("-tcp", "-tcpPort", "9093", "-tcpAllowOthers");
        return tcpServer;
    }

    @PostConstruct
    public void createInMemoryDbAndKeepAlive() throws SQLException {
        // Create the mem DB inside this JVM and keep a connection open
        // so the in-memory DB exists for remote clients connecting via TCP.
        keepAliveConn = DriverManager.getConnection(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", ""
        );
        // Optional: run schema init here if needed
    }

    @PreDestroy
    public void shutdown() throws SQLException {
        if (keepAliveConn != null && !keepAliveConn.isClosed()) keepAliveConn.close();
        if (tcpServer != null) tcpServer.stop();
    }

}
