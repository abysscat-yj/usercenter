package com.abysscat.usercenter.dal.config.mapper;

import tk.mybatis.mapper.common.base.select.ExistsWithPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;
import tk.mybatis.mapper.common.condition.SelectByConditionMapper;
import tk.mybatis.mapper.common.condition.SelectCountByConditionMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.common.example.SelectCountByExampleMapper;
import tk.mybatis.mapper.common.example.SelectOneByExampleMapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import tk.mybatis.mapper.common.rowbounds.SelectByConditionRowBoundsMapper;
import tk.mybatis.mapper.common.rowbounds.SelectByExampleRowBoundsMapper;
import tk.mybatis.mapper.common.rowbounds.SelectRowBoundsMapper;

/**
 * 基础Mapper，只包含查询逻辑
 * <p>
 * 若有需要可额外继承 IdsMapper<T> ，继承后每个实体类都需要 @Id 确定主键
 */
public interface BaseQueryMapper<T> extends SelectOneMapper<T>, SelectMapper<T>,
        SelectCountMapper<T>, SelectByPrimaryKeyMapper<T>, ExistsWithPrimaryKeyMapper<T>,
        SelectByExampleMapper<T>, SelectOneByExampleMapper<T>, SelectCountByExampleMapper<T>,
        SelectByIdsMapper<T>, SelectByConditionMapper<T>, SelectCountByConditionMapper<T>,
        SelectByConditionRowBoundsMapper<T>, SelectByExampleRowBoundsMapper<T>, SelectRowBoundsMapper<T> {

}
