package top.tanmw.generator.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author TMW
 * @since 2022/3/2 10:18
 */
public class OracleQuery extends DbQueryAbst {

    public OracleQuery(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getShowTablesSql() {
        return null;
    }

    @Override
    public String getShowTablesCommentSql() {
        return null;
    }

    @Override
    public ResultSet getResultSet(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
        return databaseMetaData.getColumns(null, databaseMetaData.getUserName(), tableName, "%");
    }
}
