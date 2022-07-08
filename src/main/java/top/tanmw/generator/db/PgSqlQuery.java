package top.tanmw.generator.db;

/**
 * @author TMW
 * @since 2022/3/2 10:19
 */
public class PgSqlQuery extends DbQueryAbst {

    public PgSqlQuery(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getShowTablesSql() {
        return "select tablename from pg_tables where schemaname='public';";
    }

    @Override
    public String getShowTablesCommentSql() {
        return "select relname as tableName,cast(obj_description(relfilenode,'pg_class') as varchar) as tableComment from pg_class;";
    }
}
