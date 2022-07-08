package top.tanmw.generator.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author TMW
 * @since 2022/3/2 10:24
 */
public abstract class DbQueryAbst implements DbQuery {
    protected String dbName;

    @Override
    public ResultSet getResultSet(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
        return databaseMetaData.getColumns(null, "%", tableName, "%");
    }
}
