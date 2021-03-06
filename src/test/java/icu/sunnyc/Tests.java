package icu.sunnyc;

import icu.sunnyc.config.MapperBean;
import icu.sunnyc.entity.User;
import icu.sunnyc.mapper.UserMapper;
import icu.sunnyc.sqlsession.MyConfiguration;
import icu.sunnyc.sqlsession.MySqlSession;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 22:39
 * @modified ：
 * 项目所有测试类
 */
public class Tests {

    @Test
    public void testReadMapper() {
        MyConfiguration myConfiguration = new MyConfiguration();
        MapperBean mapperBean = myConfiguration.readMapper("icu/sunnyc/mapper/UserMapper.xml");
        System.out.println(mapperBean);
    }

    @Test
    public void testBuild() {
        MyConfiguration myConfiguration = new MyConfiguration();
        Connection connection = myConfiguration.build("database.xml");
        System.out.println(connection);
    }

    @Test
    public void mainTest() {
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}
