package org.example.connect;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.sql.*;

import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHENTICATION;

/**
 * @Author Mayn
 * @create 2022/11/21 11:28
 */
public class JDBCTest {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String kyuubiJdbcUrl = "jdbc:hive2://lake-node3:10009/default;";

    public static void main(String[] args) throws Exception {
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection(kyuubiJdbcUrl);
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery("show databases");
        while (res.next()) {
            System.out.println(res.getString(1));
        }
        res.close();
        st.close();
        conn.close();
    }


//    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
//
//        String principal = args[0]; // kerberos principal
//        String keytab = args[1]; // keytab file location
//        Configuration configuration = new Configuration();
//        configuration.set(HADOOP_SECURITY_AUTHENTICATION, "kerberos");
//        UserGroupInformation.setConfiguration(configuration);
//        UserGroupInformation ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(principal, keytab);
//
//        Class.forName(driverName);
//        Connection conn = ugi.doAs(new PrivilegedExceptionAction<Connection>(){
//            public Connection run() throws SQLException {
//                return DriverManager.getConnection(kyuubiJdbcUrl);
//            }
//        });
//        Statement st = conn.createStatement();
//        ResultSet res = st.executeQuery("show databases");
//        while (res.next()) {
//            System.out.println(res.getString(1));
//        }
//        res.close();
//        st.close();
//        conn.close();
//    }
}
