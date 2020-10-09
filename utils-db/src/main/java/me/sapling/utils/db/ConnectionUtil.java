package me.sapling.utils.db;

import lombok.extern.slf4j.Slf4j;
import me.sapling.utils.common.tools.common.StringUtil;
import me.sapling.utils.db.constants.DBTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/8
 * @since 1.0
 */
@Slf4j
public class ConnectionUtil {

    /**
     * MYSQL类型的数据库的链接参数
     */
    private static final String URL_MYSQL_PATTERN = "jdbc:mysql://{{DB_HOST}}:{{DB_PORT}}/{{DB_NAME}}";

    /**
     * SQLSERVER类型的数据库的链接参数
     */
    private static final String URL_SQLSERVER_PATTERN = "jdbc:sqlserver://{{DB_HOST}}:{{DB_PORT}};DatabaseName={{DB_NAME}}";


    public static String getMySQLDbUrl(String host, Integer port, String dbName) {
        return getDbUrl(host, port, dbName, DBTypeEnum.MYSQL);
    }

    public static String getSqlServerDbUrl(String host, Integer port, String dbName) {
        return getDbUrl(host, port, dbName, DBTypeEnum.MSSQL);
    }

    public static String getDbUrl(String host, Integer port, String dbName, DBTypeEnum type) {
        if (type == null) {
            return null;
        }
        if (StringUtil.isEmpty(host) || StringUtil.isEmpty(dbName) || port == null || port < 0) {
            throw new IllegalArgumentException("db configuration error");
        }
        String tmpUrl = "";
        if (DBTypeEnum.MYSQL.equals(type)) {
            tmpUrl = URL_MYSQL_PATTERN;
        } else if (DBTypeEnum.TIDB.equals(type)) {
            tmpUrl = URL_MYSQL_PATTERN;
        } else if (DBTypeEnum.MSSQL.equals(type)) {
            tmpUrl = URL_SQLSERVER_PATTERN;
        } else {
            tmpUrl = URL_SQLSERVER_PATTERN;
        }
        return tmpUrl.replace("{{DB_HOST}}", host)
                .replace("{{DB_PORT}}", String.valueOf(port))
                .replace("{{DB_NAME}}", dbName);

    }

    /**
     * get the database connection
     *
     * @param url        the url of the database
     * @param userName   the user name of database
     * @param password   the password of user
     * @param driverName the driver class
     * @param properties the other connection properties
     * @return the database connection
     * @throws ClassNotFoundException it will occur when the driver class is not found
     * @throws SQLException           it will occur when some error return from the database
     */
    public static Connection connect(String url,
                                     String userName,
                                     String password,
                                     String driverName,
                                     Properties properties) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(driverName);
            if (properties == null) {
                properties = new Properties();
            }
            if (!StringUtil.isEmpty(userName)) {
                properties.setProperty("user", userName);
            }
            if (!StringUtil.isEmpty(password)) {
                properties.setProperty("password", password);
            }
            return DriverManager.getConnection(url, properties);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * get the database connection
     *
     * @param url        the url of the database
     * @param userName   the user name of database
     * @param password   the password of user
     * @param driverName the driver class
     * @return the database connection
     * @throws ClassNotFoundException it will occur when the driver class is not found
     * @throws SQLException           it will occur when some error return from the database
     */
    public static Connection connect(String url,
                                     String userName,
                                     String password,
                                     String driverName) throws ClassNotFoundException, SQLException {
        return connect(url, userName, password, driverName, new Properties());
    }

    public static void release(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.warn("关闭连接失败");
            }
        }
    }

}
