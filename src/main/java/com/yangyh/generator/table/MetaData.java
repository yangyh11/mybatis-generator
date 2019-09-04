package com.yangyh.generator.table;

import java.io.Serializable;

/**
 * @description:
 * @author: yangyh
 * @create: 2019-05-15 17:19
 */
public class MetaData implements Serializable {

    private static final long serialVersionUID = 3317622984309004033L;
    /** 字段名**/
    private String columnName;
    /** 字段在数据库中的类型**/
    private String dataType;
    /** 字段对应Java的类型**/
    private String classType;
    /** 字段对应Java实体类的变量名(驼峰命名)**/
    private String columnClassPropertyName;
    /** 字段是表主键标志**/
    private boolean keyFlag;
    private String remark;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getColumnClassPropertyName() {
        return columnClassPropertyName;
    }

    public void setColumnClassPropertyName(String columnClassPropertyName) {
        this.columnClassPropertyName = columnClassPropertyName;
    }

    public boolean isKeyFlag() {
        return keyFlag;
    }

    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", classType='" + classType + '\'' +
                ", columnClassPropertyName='" + columnClassPropertyName + '\'' +
                ", keyFlag=" + keyFlag +
                ", remark='" + remark + '\'' +
                '}';
    }
}
