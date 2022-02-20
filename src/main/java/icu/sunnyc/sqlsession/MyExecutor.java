package icu.sunnyc.sqlsession;

import icu.sunnyc.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ：hc
 * @date ：Created in 2022/2/20 9:32
 * @modified ：
 * 封装具体的JDBC操作
 */
public class MyExecutor implements Executor {

    private final MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, parameter.toString());
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getString(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
            }
            return (T) user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取数据库连接
     * @return Connection
     */
    private Connection getConnection() {
        return xmlConfiguration.build("database.xml");
    }
}
