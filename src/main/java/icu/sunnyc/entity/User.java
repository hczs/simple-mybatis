package icu.sunnyc.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 20:59
 * @modified ：
 */
@Data
@ToString
public class User {

    private String id;

    private String username;

    private String password;
}
