package icu.sunnyc.sqlsession;

import icu.sunnyc.config.Function;
import icu.sunnyc.config.MapperBean;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 21:07
 * @modified ：
 * 读取与解析配置信息，并返回处理后的Environment
 */
@Slf4j
public class MyConfiguration {

    /**
     * database.xml根标签
     */
    private static final String XML_ROOT_ELEMENT = "database";

    /**
     * database.xml属性标签
     */
    private static final String PROPERTY = "property";

    private static final ClassLoader LOADER = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     * @param resource xml文件路径
     * @return xml配置正确返回java.sql.Connection，其他情况返回null
     */
    public Connection build(String resource) {
        InputStream resourceAsStream = LOADER.getResourceAsStream(resource);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            return evalDataSource(rootElement);
        } catch (DocumentException e) {
            e.printStackTrace();
            log.error("build DocumentException", e);
            log.error("error occured while evaling xml: {}", resource);
            return null;
        }
    }

    /**
     * 解析xml文件内容
     * @param rootElement xml根标签
     * @return xml配置正确返回java.sql.Connection，其他情况返回null
     */
    private Connection evalDataSource(Element rootElement) {
        // database.xml根标签必须是database标签
        if (!XML_ROOT_ELEMENT.equals(rootElement.getName())) {
            log.error("xml root element should be <database>");
            return null;
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        // 读取属性节点的值
        for (Object property : rootElement.elements(PROPERTY)) {
            Element element = (Element) property;
            String value = getValue(element);
            String name = element.attributeValue("name");
            if (name == null || value == null) {
                log.error("[database]: <property> should contain name and value");
                return null;
            }
            // 赋值
            switch (name) {
                case "url": url = value; break;
                case "driverClassName": driverClassName = value; break;
                case "username": username = value; break;
                case "password": password = value; break;
                default: log.error("[database]: <property> unknown name");
            }
        }
        if (url == null || driverClassName == null || username == null || password == null) {
            log.error("database xml property value is null");
        }
        // 加载数据库驱动类
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("driverClassName load error", e);
        }
        // 发起连接
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            log.error("sql connect exception", throwables);
        }
        return null;
    }

    /**
     * 获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
     * @param node Element
     * @return String
     */
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    /**
     * 读取mapper.xml中的内容，封装到MapperBean中
     * @param path icu.sunnyc.mapper.xml文件路径
     * @return icu.sunnyc.config.MapperBean
     */
    public MapperBean readMapper(String path) {
        MapperBean mapperBean = new MapperBean();
        InputStream resourceAsStream = LOADER.getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(resourceAsStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            log.error("readMapper DocumentException");
            return null;
        }
        Element rootElement = document.getRootElement();
        // 根节点的namespace存的是mapper接口的全限定类名，直接赋值
        mapperBean.setInterfaceName(rootElement.attributeValue("namespace").trim());
        List<Function> functionList = new LinkedList<>();
        // 遍历根节点下所有子节点，构建functionList
        for (Object item : rootElement.elements()) {
            Function function = new Function();
            Element element = (Element) item;
            // 赋值
            function.setSqlType(element.getName());
            function.setFuncName(element.attributeValue("id").trim());
            function.setSql(element.getTextTrim());
            // 返回类型
            Class<?> resultType = null;
            try {
                resultType = Class.forName(element.attributeValue("resultType").trim());
                resultType = Class.forName(element.attributeValue("resultType").trim());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            function.setResultType(resultType);
            // TODO function.setParameterType();
            functionList.add(function);
        }
        mapperBean.setFunctionList(functionList);
        return mapperBean;
    }
}
