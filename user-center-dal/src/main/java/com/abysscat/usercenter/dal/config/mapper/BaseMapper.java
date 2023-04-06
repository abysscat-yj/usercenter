package com.abysscat.usercenter.dal.config.mapper;

import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础Mapper，不能被 @MapperScan 扫描到
 * <p>
 * 若有需要可额外继承 IdsMapper<T> ，继承后每个实体类都需要 @Id 确定主键
 */
public interface BaseMapper<T> extends Mapper<T>,
        MySqlMapper<T>,
        ExampleMapper<T>,
        BatchMapper<T> {

}
