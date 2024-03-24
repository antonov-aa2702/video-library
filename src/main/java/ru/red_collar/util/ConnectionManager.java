package ru.red_collar.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConnectionManager {

    private static final String URL_KEY = "db.url";

    private static final String USER_KEY = "db.user";

    private static final String PASSWORD_KEY = "db.password";

    private static final String POOL_SIZE_KEY = "db.pool.size";

    private static final Integer DEFAULT_POOL_SIZE = 10;

    public static final String DRIVER_KEY = "db.driver";

    private static BlockingQueue<Connection> connectionPool;

    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void initConnectionPool() {
        final String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        final int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        connectionPool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final Connection connection = open();
            final Connection proxyConnection =
                (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[] {Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ? connectionPool.add((Connection) proxy) :
                        method.invoke(connection, args));
            connectionPool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }
    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection get() {
        try {
            return connectionPool.take();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        try {
            for (final Connection connection : sourceConnections) {
                connection.close();
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY), PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
