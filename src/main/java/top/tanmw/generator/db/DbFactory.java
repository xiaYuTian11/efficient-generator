package top.tanmw.generator.db;

import cn.hutool.core.util.StrUtil;

/**
 * @author TMW
 * @since 2022/3/2 9:55
 */
public class DbFactory {

    public static DbQuery getDbQuery(String url) {
        final String dbName = getDbName(url);
        final DbType dbType = getDbType(url);
        switch (dbType) {
            case ORACLE:
                return new OracleQuery(dbName);
            case MYSQL:
                return new MysqlQuery(dbName);
            case POSTGRESQL:
                return new PgSqlQuery(dbName);
            case KINGBASE:
                return new KingbaseQuery(dbName);
            case DM:
                return new DmQuery(dbName);
            default:
                throw new RuntimeException("驱动类型无法识别，请联系开发者");
        }
    }

    public static DbType getDbType(String url) {
        if (StrUtil.contains(url, "jdbc:oracle")) {
            return DbType.ORACLE;
        } else if (StrUtil.contains(url, "jdbc:mysql")) {
            return DbType.MYSQL;
        } else if (StrUtil.contains(url, "jdbc:postgresql")) {
            return DbType.POSTGRESQL;
        } else if (StrUtil.contains(url, "jdbc:kingbase")) {
            return DbType.KINGBASE;
        } else if (StrUtil.contains(url, "jdbc:dm")) {
            return DbType.DM;
        } else {
            return DbType.UNKNOW;
        }
    }

    public static String getDbName(String url) {
        final int indexOf1 = url.lastIndexOf("/");
        final int indexOf2 = url.indexOf("?");
        if (indexOf2 < 0) {
            return url.substring(indexOf1 + 1);
        }
        return url.substring(indexOf1 + 1, indexOf2);
    }

}
