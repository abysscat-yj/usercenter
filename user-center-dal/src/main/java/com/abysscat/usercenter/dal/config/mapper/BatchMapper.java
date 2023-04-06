package com.abysscat.usercenter.dal.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;

import com.abysscat.usercenter.dal.config.mapper.provider.BatchProvider;

@tk.mybatis.mapper.annotation.RegisterMapper
public interface BatchMapper<T> {

    /**
     * 批量更新:
     *      1. 性能低，需要配合批处理来使用
     *      2. 对主键数量无限制
     *      3. 支持批量更新的数据库可以使用，拼接多条sql
     *      4. 需要开启 &allowMultiQueries=true
     *
     * @return 0: 全部未修改成功, 1: 全部修改成功or部分修改成功
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int updateListByPrimaryKeySelective(@Param("records") List<T> records);


    /**
     * 批量更新:
     *      1. 性能低，需要配合批处理来使用
     *      2. 对主键数量无限制
     *      3. 支持批量更新的数据库可以使用，拼接多条sql
     *      4. 需要开启 &allowMultiQueries=true
     *
     * @return 0: 全部未修改成功, 1: 全部修改成功or部分修改成功
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int updateListByPrimaryKey(@Param("records") List<T> records);

    /**
     * 批量更新：
     *      1. 性能好，但批量处理条数，受到max_allowed_packet的限制
     *      2. 须只有一个带有 @Id 注解的字段
     *      3. 使用case-when拼接成一条sql
     *
     * @return 成功修改条数
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int updateListByIdSelective(@Param("records") List<T> records);

    /**
     * 批量更新：
     *      1. 性能好，但批量处理条数，受到max_allowed_packet的限制
     *      2. 须只有一个带有 @Id 注解的字段
     *      3. 使用case-when拼接成一条sql
     *
     * @return 成功修改条数
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int updateListById(@Param("records") List<T> records);

    /**
     * 根据主键批量插入或者更新，性能高
     *
     * @return 成功修改条数 * 2 + 成功插入条数
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int insertOrUpdateListSelective(@Param("records") List<? extends T> records);

    /**
     * 根据主键批量插入或者更新，性能高
     *
     * @return 成功修改条数 * 2 + 成功插入条数
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int insertOrUpdateList(@Param("records") List<? extends T> records);

    /**
     * 批量插入，性能高
     *
     * @return 成功插入条数
     */
    @UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
    int insertListSelective(@Param("records") List<? extends T> records);

    /**
     * 批量查询：根据对象非空字段进行批量查询
     *
     * @return 查找到的所有记录
     */
    @SelectProvider(type = BatchProvider.class, method = "dynamicSQL")
    List<T> selectBatch(@Param("records") List<T> records);

    /**
     * 批量查询：根据对象非空字段进行批量查询, 支持分页
     *
     * @return 查找到的所有记录
     */
    @SelectProvider(type = BatchProvider.class, method = "dynamicSQL")
    List<T> selectBatchByRowBounds(@Param("records") List<T> records, RowBounds rowBounds);
}
