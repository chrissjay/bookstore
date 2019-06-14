package whj.bookstore.dao;

import org.apache.ibatis.jdbc.SQL;
import whj.bookstore.model.User;

public class UserSqlProvider {

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUserCode() != null) {
            sql.VALUES("USER_CODE", "#{userCode,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.VALUES("USER_NAME", "#{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserAddr() != null) {
            sql.VALUES("USER_ADDR", "#{userAddr,jdbcType=VARCHAR}");
        }
        
        if (record.getUserPhone() != null) {
            sql.VALUES("USER_PHONE", "#{userPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getUserPwd() != null) {
            sql.VALUES("USER_PWD", "#{userPwd,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        SQL sql = new SQL();
        sql.UPDATE("user");
        
        if (record.getUserCode() != null) {
            sql.SET("USER_CODE = #{userCode,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.SET("USER_NAME = #{userName,jdbcType=VARCHAR}");
        }
        
        if (record.getUserAddr() != null) {
            sql.SET("USER_ADDR = #{userAddr,jdbcType=VARCHAR}");
        }
        
        if (record.getUserPhone() != null) {
            sql.SET("USER_PHONE = #{userPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getUserPwd() != null) {
            sql.SET("USER_PWD = #{userPwd,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}