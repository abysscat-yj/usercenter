package ${package};

import ${tableClass.fullClassName};
import com.abysscat.usercenter.dal.config.mapper.BaseMapper;
import com.abysscat.usercenter.dal.config.MyBatisUserCenterDao;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@MyBatisUserCenterDao
public interface ${tableClass.shortClassName}${mapperSuffix} extends ${baseMapper!"BaseMapper"}<${tableClass.shortClassName}> {
}