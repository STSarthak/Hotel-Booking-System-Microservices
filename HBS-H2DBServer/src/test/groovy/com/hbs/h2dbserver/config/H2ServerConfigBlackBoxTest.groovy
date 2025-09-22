package com.hbs.h2dbserver.config

import groovy.sql.Sql
import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@SpringBootTest(classes = H2ServerConfig.class)
@TestPropertySource(properties = ["h2.tcp.port=0"]) // Use a random available port for tests
class H2ServerConfigBlackBoxTest extends Specification {

    @Autowired
    ApplicationContext context

    // Helper method to get the dynamic JDBC URL for the running test server
    private String getJdbcUrl() {
        def server = context.getBean(Server.class)
        "jdbc:h2:tcp://localhost:${server.getPort()}/mem:testdb"
    }

    def "when context is loaded, H2 server beans should be present and server running"() {
        expect: "H2ServerConfig bean is present"
        context.getBean(H2ServerConfig.class)

        and: "H2 Server bean is present and running"
        def server = context.getBean(Server.class)
        assert server != null
        assert server.isRunning(true)
    }

    def "should be able to connect to the H2 TCP server"() {
        given: "The H2 server connection details"
        def url = getJdbcUrl()
        def user = "sa"
        def password = ""
        Connection connection

        when: "A connection is attempted"
        connection = DriverManager.getConnection(url, user, password)

        then: "The connection is successfully established and valid"
        connection != null
        connection.isValid(1)

        cleanup: "Close the connection"
        connection?.close()
    }

    def "should fail to connect with wrong credentials"() {
        given: "The H2 server connection details with a wrong password"
        def url = getJdbcUrl()
        def user = "sa"
        def wrongPassword = "wrong_password"

        when: "A connection is attempted with wrong credentials"
        DriverManager.getConnection(url, user, wrongPassword)

        then: "An SQLException for wrong credentials is thrown"
        // H2 error code for "Wrong user name or password" is 28000
        def exception = thrown(SQLException)
        exception.message.toLowerCase().contains("wrong user") || exception.errorCode == 28000
    }

    def "should fail to connect to a non-existent in-memory database"() {
        given: "The H2 server connection details for a different database"
        def server = context.getBean(Server.class)
        def url = "jdbc:h2:tcp://localhost:${server.getPort()}/mem:anotherdb;IFEXISTS=TRUE"
        def user = "sa"
        def password = ""

        when: "A connection is attempted to a non-existent DB"
        DriverManager.getConnection(url, user, password)

        then: "An SQLException for database not found is thrown"
        // H2 error code for "Database not found" is 90046
        def exception = thrown(SQLException)
        exception.getMessage().toLowerCase().contains("not found") || exception.getMessage().toLowerCase().contains("database .* not found")
    }

    def "should be able to perform CRUD operations on the database"() {
        given: "A connection to the H2 database using Groovy's Sql"
        def url = getJdbcUrl()
        def user = "sa"
        def password = ""
        def sql = Sql.newInstance(url, user, password, "org.h2.Driver")

        when: "A table is created and data is inserted"
        sql.execute("CREATE TABLE TEST_USERS(ID INT PRIMARY KEY, NAME VARCHAR(255))")
        sql.execute("INSERT INTO TEST_USERS VALUES(1, 'Test User')")

        then: "The data can be retrieved"
        def userRow = sql.firstRow("SELECT * FROM TEST_USERS WHERE ID = 1")
        userRow.ID == 1
        userRow.NAME == "Test User"

        when: "Data is updated"
        sql.execute("UPDATE TEST_USERS SET NAME = 'Updated User' WHERE ID = 1")

        then: "The data is updated successfully"
        def updatedName = sql.firstRow("SELECT NAME FROM TEST_USERS WHERE ID = 1").NAME
        updatedName == "Updated User"

        when: "Data is deleted"
        sql.execute("DELETE FROM TEST_USERS WHERE ID = 1")

        then: "The data is deleted successfully"
        def count = sql.firstRow("SELECT COUNT(*) as c FROM TEST_USERS").c
        count == 0

        cleanup: "Drop the table and close resources"
        sql?.execute("DROP TABLE TEST_USERS")
        sql?.close()
    }
}