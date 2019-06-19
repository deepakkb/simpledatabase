package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operation {

    private String key;
    private String value;
    private OperationEnum operationEnum;
    private Map<String, String> map;
    private Map<String, List<String>> counterMap;
    public int transId;

    public Operation(OperationEnum operationEnum, String key, String value, Map<String, String> map, Map<String, List<String>> counterMap, int transId) {
        this.key = key;
        this.value = value;
        this.operationEnum = operationEnum;
        this.map = map;
        this.counterMap = counterMap;
        this.transId = transId;
    }

    public void execute(){
        switch (this.operationEnum) {
            case GET:
                System.out.println(GetName(key));
                break;
            case NUMEQUALTO:
                System.out.println(NumEqualTo(value));
                break;
            case SET:
                SetNameValue(key, value);
                break;
            case UNSET:
                UnsetName(key);
                break;
        }
    }

    public void SetNameValue(String key, String value) {
        map.put(key, value);
        List<String> lst;
        if (counterMap.containsKey(value)) {
            lst = counterMap.get(value);
        } else {
            lst = new ArrayList<>();
        }
        lst.add(key);
        counterMap.put(value, lst);
    }

    public String GetName(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return "NULL";
    }

    public void UnsetName(String key){
        if (map.containsKey(key)) {
            String value = map.get(key);
            List<String> lst = counterMap.get(value);
            lst.remove(key);
            if (lst.size()>0) {
                counterMap.put(value, lst);
            } else {
                counterMap.remove(value);
            }
            map.remove(key);
        }
    }

    public String NumEqualTo(String value) {
        if (counterMap.containsKey(value)) {
            return String.valueOf(counterMap.get(value).size());
        }
        return "0";
    }
}
