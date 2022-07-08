package top.tanmw.generator.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author TMW
 * @since 2022/3/2 9:55
 */
public interface DbQuery {

    String getShowTablesSql();

    String getShowTablesCommentSql();

    ResultSet getResultSet(DatabaseMetaData databaseMetaData, String tableName) throws SQLException;
}
