package whj.bookstore.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import whj.bookstore.model.Book;

import java.util.List;

@Mapper
public interface BookMapper {
    @Delete({
        "delete from book",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into book (id, url, ",
        "name, author, press, ",
        "price, cid, uid, ",
        "description)",
        "values (#{id,jdbcType=INTEGER}, #{url,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{author,jdbcType=VARCHAR}, #{press,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=DOUBLE}, #{cid,jdbcType=INTEGER}, #{uid,jdbcType=INTEGER}, ",
        "#{description,jdbcType=LONGVARCHAR})"
    })
    int insert(Book record);

    @InsertProvider(type=BookSqlProvider.class, method="insertSelective")
    int insertSelective(Book record);

    @Select({
        "select",
        "id, url, name, author, press, price, cid, uid, description",
        "from book",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="press", property="press", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="cid", property="cid", jdbcType=JdbcType.INTEGER),
        @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    Book selectByPrimaryKey(Integer id);

    @Select({
            "select",
            "id, url, name, author, press, price, cid, uid, description",
            "from book",
            "where cid = #{cid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
            @Result(column="press", property="press", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
            @Result(column="cid", property="cid", jdbcType=JdbcType.INTEGER),
            @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
            @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Book> selectByCid(Integer cid);

    @Select({
            "select",
            "id, url, name, author, press, price, cid, uid, description",
            "from book",
            "where uid = #{uid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
            @Result(column="press", property="press", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
            @Result(column="cid", property="cid", jdbcType=JdbcType.INTEGER),
            @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
            @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Book> selectByUid(Integer uid);

    @UpdateProvider(type=BookSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Book record);

    @Update({
        "update book",
        "set url = #{url,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "author = #{author,jdbcType=VARCHAR},",
          "press = #{press,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "cid = #{cid,jdbcType=INTEGER},",
          "uid = #{uid,jdbcType=INTEGER},",
          "description = #{description,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Book record);

    @Update({
        "update book",
        "set url = #{url,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "author = #{author,jdbcType=VARCHAR},",
          "press = #{press,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "cid = #{cid,jdbcType=INTEGER},",
          "uid = #{uid,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Book record);

    /**
     * 根据条件获取相应书本的List
     * @param start 起始图书ID
     * @param count 需要的书的总数
     * @param cid CategoryId
     * @return 符合条件的List
     */
    @Select({
            "<script>",
            "select * from book where cid = #{cid} order by id desc",
            "<when test='start != -1'>",
            "limit #{start},#{count}",
            "</when>",
            "</script>"
    })
    List<Book> getListByCategoryId(@Param("start") int start, @Param("count") int count, @Param("cid") int cid);

    @Select({
            "select * from book"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
            @Result(column="press", property="press", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
            @Result(column="cid", property="cid", jdbcType=JdbcType.INTEGER),
            @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
            @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Book> selectAllAskBooks();


    @Select({
            "select id from book order by id DESC limit 1"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
    })
    Integer lastId();

    @Select({
            "select * from book where name like #{bookName,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
            @Result(column="press", property="press", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
            @Result(column="cid", property="cid", jdbcType=JdbcType.INTEGER),
            @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
            @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Book> selectSearchBooks(@Param("bookName") String bookName);

}