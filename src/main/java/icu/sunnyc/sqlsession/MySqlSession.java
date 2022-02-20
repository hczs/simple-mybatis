package icu.sunnyc.sqlsession;

import java.lang.reflect.Proxy;

/**
 * @author ：hc
 * @date ：Created in 2022/2/20 9:40
 * @modified ：
 * 连接configuration和executor的桥梁
 */
public class MySqlSession {

    private final Executor executor = new MyExecutor();

    private final MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String sql, Object parameter) {
        return executor.query(sql, parameter);
    }

    @SuppressWarnings("unchecked")
    public  <T> T getMapper(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(),
                new Class[]{cls}, new MyMapperProxy(myConfiguration, this));
    }
}
