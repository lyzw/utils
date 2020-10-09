package me.sapling.utils.db.constants;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/22
 * @since 1.0
 */
public enum TypeMapping {

    /**
     *  bigint
     */
    TYPE_BIGINT("bigint","bigint"),
    /**
     * binary
     */
    TYPE_BINARY("binary","binary"),
    /**
     * boolean
     */
    TYPE_BOOLEAN("bit","tinyint"),
    /**
     * char
     */
    TYPE_CHAR("char","char"),
    /**
     * date
     */
    TYPE_DATE("date","date"),
    /**
     * datetime
     */
    TYPE_DATETIME("datetime","datetime"),
    /**
     * datetime
     */
    TYPE_DATETIME2("datetime2","datetime"),
    /**
     * datetime
     */
    TYPE_DATETIMEOFFSET("datetimeoffset","datetime"),
    /**
     * decimal
     */
    TYPE_DECIMAL("decimal","decimal"),
    /**
     * float
     */
    TYPE_FLOAT("float","float"),
    /**
     * int
     */
    TYPE_INT("int","int"),
    /**
     * decimal - money
     */
    TYPE_MONEY("money","decimal"),
    /**
     * nchar
     */
    TYPE_NCHAR("nchar","char"),
    /**
     * ntext
     */
    TYPE_NTEXT("ntext","text"),
    /**
     * numeric
     */
    TYPE_NUMERIC("numeric","decimal"),
    /**
     * nvarchar
     */
    TYPE_NVARCHAR("nvarchar","varchar"),
    /**
     * real
     */
    TYPE_REAL("real","float"),
    /**
     * smalldatetime
     */
    TYPE_SMALLDATETIME("smalldatetime","datetime"),
    /**
     * smallint
     */
    TYPE_SMALLINT("smallint","smallint"),
    /**
     * smallmoney
     */
    TYPE_SMALLMONEY("smallmoney","float"),
    /**
     * text
     */
    TYPE_TEXT("text","text"),
    /**
     * time
     */
    TYPE_TIME("time","time"),
    /**
     * timestamp
     */
    TYPE_TIMESTAMP("timestamp","timestamp"),
    /**
     * tinyint
     */
    TYPE_TINYINT("tinyint","tinyint"),
    /**
     * tinyint
     */
    TYPE_UNIQUEIDENTIFIER("uniqueidentifier","varchar"),
    /**
     * varbinary
     */
    TYPE_VARBINARY("varbinary","varbinary"),
    /**
     * varchar
     */
    TYPE_VARCHAR("varchar","varchar"),
    /**
     * xml
     */
    TYPE_XML("xml","text"),

    ;

    String mysqlType;

    String mssqlType;

    TypeMapping(String mssqlType,String mysqlType) {
        this.mysqlType = mysqlType;
        this.mssqlType = mssqlType;
    }
}
