import icu.sunnyc.config.MapperBean;
import lombok.extern.slf4j.Slf4j;
import icu.sunnyc.sqlsession.MyConfiguration;

import java.sql.Connection;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 21:25
 * @modified ：
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        MyConfiguration myConfiguration = new MyConfiguration();
        MapperBean mapperBean = myConfiguration.readMapper("icu/sunnyc/mapper/UserMapper.xml");
        Connection connection = myConfiguration.build("database.xml");
        System.out.println(mapperBean);
        System.out.println(connection);
    }
}
