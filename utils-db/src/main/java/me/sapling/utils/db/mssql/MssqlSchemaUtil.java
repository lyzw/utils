package me.sapling.utils.db.mssql;

import me.sapling.utils.db.bean.TableSchema;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/22
 * @since 1.0
 */
public class MssqlSchemaUtil {

    String schemaQuerySQL = "SELECT  " +
            "        obj.name tableName, " +
            "        col.colorder AS  \"position\", " +
            "        col.name AS columnName , " +
            "        ISNULL(ep.[value], '') AS description , " +
            "        t.name AS dataType , " +
            "        col.length AS length , " +
            "        ISNULL(COLUMNPROPERTY(col.id, col.name, 'Scale'), 0) AS scale , " +
            "        CASE WHEN COLUMNPROPERTY(col.id, col.name, 'IsIdentity') = 1 THEN 1 " +
            "             ELSE 0 " +
            "        END AS IsIdentity , " +
            "        CASE WHEN EXISTS ( SELECT   1 " +
            "                           FROM     dbo.sysindexes si " +
            "                                    INNER JOIN dbo.sysindexkeys sik ON si.id = sik.id " +
            "                                                              AND si.indid = sik.indid " +
            "                                    INNER JOIN dbo.syscolumns sc ON sc.id = sik.id " +
            "                                                              AND sc.colid = sik.colid " +
            "                                    INNER JOIN dbo.sysobjects so ON so.name = si.name " +
            "                                                              AND so.xtype = 'PK' " +
            "                           WHERE    sc.id = col.id " +
            "                                    AND sc.colid = col.colid ) THEN 1 " +
            "             ELSE 0 " +
            "        END AS pk , " +
            "        CASE WHEN col.isnullable = 1 THEN 1 " +
            "             ELSE 0 " +
            "        END AS nullable , " +
            "        ISNULL(comm.text, '') AS defaultValue " +
            "FROM    dbo.syscolumns col " +
            "        LEFT  JOIN dbo.systypes t ON col.xtype = t.xusertype " +
            "        inner JOIN dbo.sysobjects obj ON col.id = obj.id " +
            "                                         AND obj.xtype = 'U' " +
            "                                         AND obj.status >= 0 " +
            "        LEFT  JOIN dbo.syscomments comm ON col.cdefault = comm.id " +
            "        LEFT  JOIN sys.extended_properties ep ON col.id = ep.major_id " +
            "                                                      AND col.colid = ep.minor_id " +
            "                                                      AND ep.name = 'MS_Description' " +
            "        LEFT  JOIN sys.extended_properties epTwo ON obj.id = epTwo.major_id " +
            "                                                         AND epTwo.minor_id = 0 " +
            "                                                         AND epTwo.name = 'MS_Description' " +
            "WHERE 1 =1 obj.name = '%s'" +
            "ORDER BY obj.name ,col.colorder ;";

    public static TableSchema getTableSchema(){
        return null;
    }
}
