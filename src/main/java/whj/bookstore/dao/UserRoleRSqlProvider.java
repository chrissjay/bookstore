package whj.bookstore.dao;

import org.apache.ibatis.jdbc.SQL;
import whj.bookstore.model.UserRoleR;

public class UserRoleRSqlProvider {

    public String insertSelective(UserRoleR record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user_role_r");
        
        if (record.getId() != null) {
            sql.VALUES("ID", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUserCode() != null) {
            sql.VALUES("USER_CODE", "#{userCode,jdbcType=VARCHAR}");
        }
        
        if (record.getRoleCode() != null) {
            sql.VALUES("ROLE_CODE", "#{roleCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserRoleR record) {
        SQL sql = new SQL();
        sql.UPDATE("user_role_r");
        
        if (record.getUserCode() != null) {
            sql.SET("USER_CODE = #{userCode,jdbcType=VARCHAR}");
        }
        
        if (record.getRoleCode() != null) {
            sql.SET("ROLE_CODE = #{roleCode,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("ID = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}