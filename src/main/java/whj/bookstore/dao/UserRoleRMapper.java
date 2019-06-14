package whj.bookstore.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import whj.bookstore.model.UserRoleR;

@Mapper
public interface UserRoleRMapper {
    @Delete({
        "delete from user_role_r",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user_role_r (ID, USER_CODE, ",
        "ROLE_CODE)",
        "values (#{id,jdbcType=INTEGER}, #{userCode,jdbcType=VARCHAR}, ",
        "#{roleCode,jdbcType=VARCHAR})"
    })
    int insert(UserRoleR record);

    @InsertProvider(type=UserRoleRSqlProvider.class, method="insertSelective")
    int insertSelective(UserRoleR record);

    @Select({
        "select",
        "ID, USER_CODE, ROLE_CODE",
        "from user_role_r",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="USER_CODE", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="ROLE_CODE", property="roleCode", jdbcType=JdbcType.VARCHAR)
    })
    UserRoleR selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserRoleRSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserRoleR record);

    @Update({
        "update user_role_r",
        "set USER_CODE = #{userCode,jdbcType=VARCHAR},",
          "ROLE_CODE = #{roleCode,jdbcType=VARCHAR}",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserRoleR record);

    /**
     * 获取用户角色信息
     * @param userCode
     * @return List<UserRoleInfo>
     */
    @Select({
            "select",
            "ROLE_CODE",
            "from user_role_r",
            "where USER_CODE = #{userCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="ROLE_CODE", property="roleCode", jdbcType=JdbcType.VARCHAR),
    })
    String selectByUserCode(String userCode);

}