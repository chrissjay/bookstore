package whj.bookstore.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import whj.bookstore.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Delete({
        "delete from user",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user (ID, USER_CODE, ",
        "USER_NAME, USER_ADDR, ",
        "USER_PHONE, USER_PWD)",
        "values (#{id,jdbcType=INTEGER}, #{userCode,jdbcType=VARCHAR}, ",
        "#{userName,jdbcType=VARCHAR}, #{userAddr,jdbcType=VARCHAR}, ",
        "#{userPhone,jdbcType=VARCHAR}, #{userPwd,jdbcType=VARCHAR})"
    })
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @Select({
        "select",
        "ID, USER_CODE, USER_NAME, USER_ADDR, USER_PHONE, USER_PWD",
        "from user",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="USER_CODE", property="userCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="USER_NAME", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="USER_ADDR", property="userAddr", jdbcType=JdbcType.VARCHAR),
        @Result(column="USER_PHONE", property="userPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="USER_PWD", property="userPwd", jdbcType=JdbcType.VARCHAR)
    })
    User selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update user",
        "set USER_CODE = #{userCode,jdbcType=VARCHAR},",
          "USER_NAME = #{userName,jdbcType=VARCHAR},",
          "USER_ADDR = #{userAddr,jdbcType=VARCHAR},",
          "USER_PHONE = #{userPhone,jdbcType=VARCHAR},",
          "USER_PWD = #{userPwd,jdbcType=VARCHAR}",
        "where ID = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);


    /**
     * 获取用户信息
     * @param userCode
     * @return User
     */
    @Select({
            "select",
            "ID, USER_CODE, USER_NAME, USER_ADDR, USER_PHONE, USER_PWD",
            "from user",
            "where USER_CODE = #{userCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="ID", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="USER_CODE", property="userCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_NAME", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_ADDR", property="userAddr", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_PHONE", property="userPhone", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_PWD", property="userPwd", jdbcType=JdbcType.VARCHAR)
    })
    User selectOneByUserCode(String userCode);

    /**
     * 获取用户信息
     * @param userCode
     * @return List<UserInfo>
     */
    @Select({
            "select",
            "*",
            "from user",
            "where USER_CODE = #{userCode,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="ID", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="USER_CODE", property="userCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_NAME", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_ADDR", property="userAddr", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_PHONE", property="userPhone", jdbcType=JdbcType.VARCHAR),
            @Result(column="USER_PWD", property="userPwd", jdbcType=JdbcType.VARCHAR)
    })
    List<User> selectByUserCode(String userCode);

}