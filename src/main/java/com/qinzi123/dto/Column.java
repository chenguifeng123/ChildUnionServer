package com.qinzi123.dto;

public class Column {
	private String columnName;
	private String dataType;
	
	public Column(String columnName, String dataType){
		setColumnName(columnName);
		setDataType(dataType);
	}
	
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

}
