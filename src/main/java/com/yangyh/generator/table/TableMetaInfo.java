package com.yangyh.generator.table;

import com.yangyh.generator.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: yangyh
 * @create: 2019-05-15 17:19
 */
public class TableMetaInfo {

    private String dbName;
    private String tableName;
    private List<MetaData> metaDataList;
    /** 实体类名**/
    private String className;
    private Set<String> propertyTypeSet;


    public TableMetaInfo() {
    }

    public TableMetaInfo(String dbName, String tableName) {
        this.dbName = dbName;
        this.tableName = tableName;
        className =tableName;
        if (tableName.indexOf(".") > 0) {
            className = className.substring(tableName.lastIndexOf(".") + 1, tableName.length());
        }

        className = className.toLowerCase();
        String[] classNameSplit = className.split("_");
        StringBuffer classNameBuffer = new StringBuffer();
        // 实体类驼峰命名
        for (String split : classNameSplit) {
            split = split.replaceFirst(split.substring(0, 1), split.substring(0, 1).toUpperCase());
            classNameBuffer.append(split);
        }
        className = classNameBuffer.toString();

        setMetaDataList();

    }

    public Set<String> primaryKeys(DatabaseMetaData dmd, String tableName) {
        Set<String> primaryKeys = new HashSet<String>();

        String schameName = null;
        String tableNameReal = tableName;
        if (tableName.indexOf(".") > 0) {
            String[] tableNameSplit = tableName.split("\\.");
            tableNameReal = tableNameSplit[tableNameReal.length() - 1];
            schameName = tableNameSplit[tableNameReal.length() - 2];
        }
        try {
            ResultSet indexRs = dmd.getPrimaryKeys(null, schameName, tableNameReal);
            while (indexRs.next()) {
                Object columnObj = indexRs.getObject(4);
                if (columnObj != null) {
                    primaryKeys.add(columnObj.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return primaryKeys;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<MetaData> getMetaDataList() {
        return metaDataList;
    }

    public void setMetaDataList() {
        String sql = "select * from " + this.tableName + " where 1=0";
        Connection conn = ConnectionManager.getConnection(dbName);
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            Set<String> keys = primaryKeys(dmd, tableName);
            if (keys == null || keys.size() < 1) {
                System.out.println("该表无主键");
                keys = uniqueIndex(dmd, tableName);
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if (columnCount > 0) {
                metaDataList = new ArrayList<MetaData>();
                propertyTypeSet = new HashSet<String>();
            }
            for (int i = 1; i <= columnCount; i++) {
                MetaData metaData = new MetaData();
                String columnName = resultSetMetaData.getColumnName(i);
                metaData.setColumnName(columnName);
                String columClassName = resultSetMetaData.getColumnClassName(i);
                if ("java.sql.Date".equals(columClassName) || "java.sql.Timestamp".equals(columClassName)) {
                    columClassName = "java.util.Date";
                }
                metaData.setClassType(columClassName);
                String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                metaData.setDataType(columnTypeName);
                columnName = columnName.toLowerCase();
                String[] columnSplit = columnName.split("_");
                StringBuffer columnClassPropertyNameBuffer = new StringBuffer();
                for (int j = 0; j < columnSplit.length; j++) {
                    String split = columnSplit[j];
                    if (j > 0) {
                        split = split.replaceFirst(split.substring(0, 1), split.substring(0, 1).toUpperCase());
                    }
                    columnClassPropertyNameBuffer.append(split);
                }
                metaData.setColumnClassPropertyName(columnClassPropertyNameBuffer.toString());
                metaData.setKeyFlag(keys.contains(columnName));
                metaDataList.add(metaData);
                propertyTypeSet.add(columClassName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Set<String> uniqueIndex(DatabaseMetaData dmd, String tableName) {
        Set<String> uniqueIndexs = new HashSet<String>();

        String schameName = null;
        String tableNameReal = tableName;
        if (tableName.indexOf(".") > 0) {
            String[] tableNameSplit = tableName.split("\\.");
            tableNameReal = tableNameSplit[tableNameReal.length() - 1];
            schameName = tableNameSplit[tableNameReal.length() - 2];
        }
        try {
            ResultSet indexRs = dmd.getIndexInfo(null, schameName, tableNameReal, true, false);
            while (indexRs.next()) {
                Object columnObj = indexRs.getObject(9);
                if (columnObj != null) {
                    uniqueIndexs.add(columnObj.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uniqueIndexs;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<String> getPropertyTypeSet() {
        return propertyTypeSet;
    }

    public void setPropertyTypeSet(Set<String> propertyTypeSet) {
        this.propertyTypeSet = propertyTypeSet;
    }
}
