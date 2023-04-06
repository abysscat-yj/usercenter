package com.abysscat.usercenter.dal.config.mapper.provider;

import java.util.Iterator;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.annotation.Version;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.version.VersionException;

public class BatchProvider extends MapperTemplate {
    public BatchProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateListByPrimaryKeySelective(MappedStatement ms) {
       return doUpdateListByPrimaryKey(ms, true);
    }

    public String updateListByPrimaryKey(MappedStatement ms) {
        return doUpdateListByPrimaryKey(ms, false);
    }

    private String doUpdateListByPrimaryKey(MappedStatement ms, Boolean selective) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util"
                        + ".OGNL@notEmptyCollectionCheck(records, '")
                .append(ms.getId()).append(" 方法参数为空')\"/>");
        sql.append("<foreach collection=\"records\" item=\"record\" "
                + "index=\"index\" open=\"\" close=\"\" separator=\";\">");
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, "record", selective, selective && this.isNotEmpty()));
        sql.append(wherePKColumns(entityClass));
        sql.append("</foreach>");
        return sql.toString();
    }

    /**
     * 需要重写SqlHelper中的方法，因为SqlHelper的useVersion在foreach中不能用。
     */
    private String wherePKColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        boolean hasLogicDelete = SqlHelper.hasLogicDeleteColumn(entityClass);
        sql.append("<where>");
        Set<EntityColumn> columnSet = EntityHelper.getPKColumns(entityClass);

        for (EntityColumn column : columnSet) {
            sql.append(" AND ").append(column.getColumnEqualsHolder("record"));
        }

        sql.append(whereVersion(entityClass));

        if (hasLogicDelete) {
            sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        }

        sql.append("</where>");
        return sql.toString();
    }

    private String whereVersion(Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        boolean hasVersion = false;
        String result = "";

        for (EntityColumn column : columnSet) {
            if (column.getEntityField().isAnnotationPresent(Version.class)) {
                if (hasVersion) {
                    throw new VersionException(entityClass.getCanonicalName()
                            + " 中包含多个带有 @Version 注解的字段，一个类中只能存在一个带有 @Version 注解的字段!");
                }
                hasVersion = true;
                result = " AND " + column.getColumnEqualsHolder("record");
            }
        }

        return result;
    }

    public String updateListByIdSelective(MappedStatement ms) {
        return doUpdateListByIdSelective(ms, true);
    }

    public String updateListById(MappedStatement ms) {
        return doUpdateListByIdSelective(ms, false);
    }

    private String doUpdateListByIdSelective(MappedStatement ms, Boolean selective) {
        Class<?> entityClass = this.getEntityClass(ms);
        String tableName = SqlHelper.getDynamicTableName(entityClass, this.tableName(entityClass), "example");

        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util"
                        + ".OGNL@notEmptyCollectionCheck(records, '")
                .append(ms.getId()).append(" 方法参数为空')\"/>");
        sql.append("UPDATE ").append(tableName);
        sql.append(" <set>");

        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        EntityColumn idColumn = columnList
                .stream()
                .filter(EntityColumn::isId).findFirst()
                .orElse(null);
        if (idColumn == null) {
            return "";
            // throw new MapperException("[" + entityClass.getCanonicalName() + "]中必须有一个带有 @Id 注解的字段");
        }

        columnList.forEach(tempColumn -> {
            if (!tempColumn.isId()) {
                appendSetInfo(sql, tempColumn, idColumn, tableName, selective);
            }
        });
        sql.append("</set>");
        appendWhereIdList(sql, entityClass, idColumn);
        return sql.toString();
    }

    private void appendSetInfo(StringBuilder sql, EntityColumn column, EntityColumn idColumn, String tableName,
                               Boolean selective) {
        sql.append("<trim prefix=\"").append(column.getColumn()).append(" = case ").append(idColumn.getColumn())
                .append("\" suffix=\"end , \">");
        sql.append("<foreach collection=\"records\" item=\"record\">");
        sql.append("<if test=\"record.").append(column.getEntityField().getName()).append(" !=null\">");
        sql.append(" WHEN #{record.").append(idColumn.getEntityField().getName()).append("} THEN #{record.")
                .append(column.getEntityField().getName()).append("} ");
        sql.append("</if>");

        if (selective) {
            sql.append("<if test=\"record.").append(column.getEntityField().getName()).append(" ==null\">");
            sql.append(" WHEN #{record.").append(idColumn.getEntityField().getName()).append("} THEN ")
                    .append(tableName).append(".").append(column.getColumn()).append(" ");
            sql.append("</if>");
        }

        sql.append("</foreach>");
        sql.append("</trim>");
    }

    private void appendWhereIdList(StringBuilder sql, Class<?> entityClass, EntityColumn idColumn) {
        sql.append("<where>");
        sql.append("<foreach collection=\"records\" item=\"record\" separator=\",\" open=\"");
        sql.append(idColumn.getColumn());
        sql.append(" in ");
        sql.append("(\" close=\")\">");
        sql.append("#{record.").append(idColumn.getEntityField().getName()).append("}");
        sql.append("</foreach>");
        sql.append(SqlHelper.whereVersion(entityClass));
        sql.append("</where>");
    }

    public String insertOrUpdateListSelective(MappedStatement ms) {
        return doInsertOrUpdateList(ms, true);
    }

    public String insertOrUpdateList(MappedStatement ms) {
        return doInsertOrUpdateList(ms, false);
    }

    public String insertListSelective(MappedStatement ms) {
        StringBuilder sql = getInsertListSelective(ms, true);
        return sql.toString();
    }

    private String doInsertOrUpdateList(MappedStatement ms, Boolean selective) {
        StringBuilder sql = getInsertListSelective(ms, selective);
        sql.append(" ON DUPLICATE KEY UPDATE ");
        sql.append("<foreach collection=\"records\" item=\"record\" separator=\",\" >");
        sql.append(" <trim prefix=\"\" suffixOverrides=\",\"> ");
        Class<?> entityClass = this.getEntityClass(ms);
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        columnSet.forEach((tempColumn) -> {
            if (!tempColumn.isId()) {
                sql.append("<if test=\"null != record.");
                sql.append(tempColumn.getEntityField().getName());
                sql.append("\">");
                sql.append(tempColumn.getColumn());
                sql.append(" = #{record." );
                sql.append(tempColumn.getEntityField().getName());
                sql.append("},</if>");
            }
        });
        sql.append("</trim>");
        sql.append("</foreach>");

        EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);

        return sql.toString();
    }

    private StringBuilder getInsertListSelective(MappedStatement ms, Boolean selective) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        String tableName = SqlHelper.getDynamicTableName(entityClass, this.tableName(entityClass), "example");
        EntityColumn logicDeleteColumn = SqlHelper.getLogicDeleteColumn(entityClass);
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util"
                        + ".OGNL@notEmptyCollectionCheck(records, '")
                .append(ms.getId()).append(" 方法参数为空')\"/>");
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass), "records[0]"));
        sql.append(getInsertColumns(entityClass));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"records\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");

        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator<EntityColumn> columnIterator = columnList.iterator();
        EntityColumn column;
        while (true) {
            while (true) {
                do {
                    if (!columnIterator.hasNext()) {
                        sql.append("</trim>");
                        sql.append("</foreach>");
                        return sql;
                    }

                    column = columnIterator.next();
                } while (!column.isInsertable());

                if (logicDeleteColumn != null && logicDeleteColumn == column) {
                    sql.append(SqlHelper.getLogicDeletedValue(column, false)).append(",");
                } else {
                    sql.append(SqlHelper.getIfNotNull("record", column,
                            column.getColumnHolder("record", null, ","), this.isNotEmpty()));

                    if (selective) {
                        String columnHolder = tableName + "." + column.getColumn();
                        sql.append(SqlHelper.getIfIsNull("record", column,
                                columnHolder + ",", this.isNotEmpty()));
                    }
                }
            }
        }
    }

    private String getInsertColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        Iterator<EntityColumn> columnIterator = columnSet.iterator();

        while (true) {
            EntityColumn column;
            do {
                if (!columnIterator.hasNext()) {
                    sql.append("</trim>");
                    return sql.toString();
                }

                column = columnIterator.next();
            } while (!column.isInsertable());

            sql.append(column.getColumn()).append(",");
        }
    }

    public String selectBatchByRowBounds(MappedStatement ms) {
        return selectBatch(ms);
    }

    public String selectBatch(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        // 修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util"
                        + ".OGNL@notEmptyCollectionCheck(records, '")
                .append(ms.getId()).append(" 方法参数为空')\"/>");
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(whereAllConditions(entityClass));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

    private String whereAllConditions(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        sql.append("<foreach collection=\"records\" item=\"record\" open=\"(\" close=\")\" separator=\"or \" >");
        sql.append(" <trim prefix=\"(\" suffix=\")\" suffixOverrides=\"and\"> ");
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        columnSet.forEach((tempColumn) -> {
            sql.append("<if test=\"null != record.");
            sql.append(tempColumn.getEntityField().getName());
            sql.append("\">");
            sql.append(tempColumn.getColumn());
            sql.append(" = #{record." );
            sql.append(tempColumn.getEntityField().getName());
            sql.append("} and </if>");
        });
        sql.append("</trim>");
        sql.append("</foreach>");
        sql.append("</where>");
        return sql.toString();
    }
}
