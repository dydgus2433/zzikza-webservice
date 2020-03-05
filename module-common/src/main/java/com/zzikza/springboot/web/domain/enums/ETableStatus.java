package com.zzikza.springboot.web.domain.enums;

public enum ETableStatus {
    Y("1",true),
    N("0",false);

    private String table1Value;
    private boolean table2Value;

    ETableStatus(String table1Value, boolean table2Value) {
        this.table1Value = table1Value;
        this.table2Value = table2Value;
    }

    public String getTable1Value(){
        return table1Value;
    }

    public boolean isTable2Value(){
        return table2Value;
    }
}
