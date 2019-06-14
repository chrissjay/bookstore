package whj.bookstore.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import whj.bookstore.model.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Delete({
        "delete from role",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into role (ID, ROLE_CODE, ",
        "ROLE_NAME)",
        "values (#{id,jdbcType=INTEGER}, #{roleCode,jdbcType=VARCHAR}, ",
        "#{roleName,jdbcType=VARCHAR})"
    })
    int insert(Role record);

    @InsertProvider(type=RoleSqlProvider.class, method="insertSelective")
    int insertSelective(Role record);

    @Select({
        "select",
        "ID, ROLE_CODE, ROLE_NAME",
        "from role",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ROLE_CODE", property="roleCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="ROLE_NAME", property="roleName", jdbcType=JdbcType.VARCHAR)
    })
    Role selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RoleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Role record);

    @Update({
        "update role",
        "set ROLE_CODE = #{roleCode,jdbcType=VARCHAR},",
          "ROLE_NAME = #{roleName,jdbcType=VARCHAR}",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Role record);

    /**
     * 获取用户角色信息
     * @param roleCode
     * @return List<UserRoleInfo>
     */
    @Select({
            "select",
            "ID, ROLE_CODE, ROLE_NAME",
            "from role",
            "where ROLE_CODE = #{roleCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="ID", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="ROLE_CODE", property="roleCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="ROLE_NAME", property="roleName", jdbcType=JdbcType.VARCHAR),
    })
    List<Role> selectByRoleCode(String roleCode);

}