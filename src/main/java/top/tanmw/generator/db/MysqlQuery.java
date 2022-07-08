package top.tanmw.generator.db;

/**
 * @author TMW
 * @since 2022/3/2 9:56
 */
public class MysqlQuery extends DbQueryAbst {

    public MysqlQuery(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getShowTablesSql() {
        return "SHOW TABLES;";
    }

    @Override
    public String getShowTablesCommentSql() {
        return "SELECT table_name as tableName,table_comment as tableComment FROM information_schema.TABLES WHERE table_schema = '" + dbName + "';";
    }
}
