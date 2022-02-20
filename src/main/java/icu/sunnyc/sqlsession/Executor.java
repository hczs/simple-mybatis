package icu.sunnyc.sqlsession;

/**
 * @author ：hc
 * @date ：Created in 2022/2/20 9:29
 * @modified ：
 */
public interface Executor {

    /**
     * 查询
     * @param sql sql语句
     * @param parameter 参数
     * @param <T> 泛型
     * @return 查询结果
     */
    <T> T query(String sql, Object parameter);
}
