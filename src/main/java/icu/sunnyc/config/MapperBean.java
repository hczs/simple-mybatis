package icu.sunnyc.config;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 21:57
 * @modified ：
 */
@Data
@ToString
public class MapperBean {

    /**
     * mapper接口名称
     */
    private String interfaceName;

    /**
     * 接口中所有方法定义
     */
    private List<Function> functionList;
}
