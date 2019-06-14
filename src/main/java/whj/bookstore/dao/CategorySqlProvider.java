package whj.bookstore.dao;

import org.apache.ibatis.jdbc.SQL;
import whj.bookstore.model.Category;

public class CategorySqlProvider {

    public String insertSelective(Category record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("category");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Category record) {
        SQL sql = new SQL();
        sql.UPDATE("category");
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}