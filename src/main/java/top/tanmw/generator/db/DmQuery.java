package top.tanmw.generator.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author TMW
 * @since 2022/3/2 10:20
 */
public class DmQuery extends DbQueryAbst {

    public DmQuery(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getShowTablesSql() {
        return "select table_name  from dba_tables  where owner='" + dbName + "';";
    }

    @Override
    public String getShowTablesCommentSql() {
        return "select tvname as tableName,comment$ as tableComment from SYSTABLECOMMENTS where SCHNAME='" + dbName + "' ;";
    }

    @Override
    public ResultSet getResultSet(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
        return databaseMetaData.getColumns(null, databaseMetaData.getUserName(), tableName, "%");
    }
}
