package icu.sunnyc.sqlsession;

import icu.sunnyc.config.Function;
import icu.sunnyc.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2022/2/20 9:45
 * @modified ：
 * 动态代理类执行方法
 * 完成xml方法和真实方法对应
 */
public class MyMapperProxy implements InvocationHandler {

    private final MySqlSession mySqlSession;

    private final MyConfiguration myConfiguration;

    public MyMapperProxy(MyConfiguration myConfiguration,MySqlSession mySqlSession) {
        this.myConfiguration = myConfiguration;
        this.mySqlSession = mySqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MapperBean mapperBean = myConfiguration.readMapper("icu/sunnyc/mapper/UserMapper.xml");
        // 是否是xml文件对应的接口
        if(!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
            return null;
        }
        List<Function> functionList = mapperBean.getFunctionList();
        if (null != functionList && functionList.size() != 0) {
            for (Function function : functionList) {
                // 匹配到id和方法名一样的，执行查询
                if (method.getName().equals(function.getFuncName())) {
                    return mySqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}
