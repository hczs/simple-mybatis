package icu.sunnyc.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 21:58
 * @modified ：
 * 就是对 <select id="getUserById" resultType ="icu.sunnyc.entity.User">
 *         select * from user where id = ?
 *     </select> 的封装
 */
@Data
@ToString
public class Function {

    /**
     * sql类型 增删改查
     */
    private String sqlType;

    /**
     * 方法名
     */
    private String funcName;

    /**
     * sql语句
     */
    private String sql;

    /**
     * 返回类型
     */
    private Object resultType;

    /**
     * 参数类型
     */
    private String parameterType;
}
