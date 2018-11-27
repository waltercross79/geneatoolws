package com.waltercross.geneatoolcore;

import java.util.HashMap;
import java.util.Map;

public enum RecordType {
    Birth(1), Death(2), Marriage(3);

    public static final Map<String,RecordType> recordTypeMap =
            new HashMap<String,RecordType>();
    static {
        for (RecordType c : RecordType.values())
            recordTypeMap.put(Integer.toString(c.value()), c);
    }

    RecordType(int i) {
        value = i;
    }

    private final int value;
    public int value() { return value; }
}
