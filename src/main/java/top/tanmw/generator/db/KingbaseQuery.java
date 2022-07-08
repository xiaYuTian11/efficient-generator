package top.tanmw.generator.db;

/**
 * @author TMW
 * @since 2022/3/2 10:19
 */
public class KingbaseQuery extends DbQueryAbst {

    public KingbaseQuery(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getShowTablesSql() {
        return "select tablename from sys_tables WHERE \"schemaname\" = 'PUBLIC';";
    }

    @Override
    public String getShowTablesCommentSql() {
        return "select relname as tableName,cast(obj_description(relfilenode,'sys_class') as varchar) as tableComment from sys_class;";
    }
}
