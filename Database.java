package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Database {
    public static Map<String, String> map;
    public static Map<String, List<String>> counterMap;
    public Database() {
        map = new HashMap<>();
        counterMap = new HashMap<>();
    }

    public static void execute() {
        Scanner scanner = new Scanner(System.in);
        Transaction transaction = new Transaction();
        while(scanner.hasNext()){
            String input = scanner.next();
            OperationEnum operationEnum = OperationEnum.valueOf(input.toUpperCase());
            Operation oprExecute;
            Operation oprRollback;
            String key ;
            String value;

            switch (operationEnum) {
                case END:
                    System.exit(0);
                case BEGIN:
                    transaction.beginTransaction();
                    break;
                case COMMIT:
                    transaction.commitTransaction();
                    break;
                case ROLLBACK:
                    transaction.rollbackTransaction();
                    break;
                case GET:
                    oprExecute = new Operation(OperationEnum.GET, scanner.next(), null,
                            map, counterMap, transaction.getTransId());
                    oprExecute.execute();
                    break;
                case NUMEQUALTO:
                    oprExecute = new Operation(OperationEnum.NUMEQUALTO, null, scanner.next(),
                            map, counterMap, transaction.getTransId());
                    oprExecute.execute();
                    break;
                case SET:
                    key = scanner.next();
                    value = scanner.next();
                    if (transaction.hasTransaction()) {
                        if (map.containsKey(key)) {
                            oprRollback = new Operation(OperationEnum.SET, key, map.get(key),
                                    map, counterMap, transaction.getTransId());
                            transaction.getStack().push(oprRollback);
                        } else {
                            oprRollback = new Operation(OperationEnum.UNSET, key, null,
                                    map, counterMap, transaction.getTransId());
                            transaction.getStack().push(oprRollback);
                        }
                    }
                    oprExecute = new Operation(OperationEnum.SET, key, value,
                            map, counterMap, transaction.getTransId());
                    oprExecute.execute();
                    break;
                case UNSET:
                    key = scanner.next();
                    if (transaction.hasTransaction()) {
                        if (map.containsKey(key)) {
                            oprRollback = new Operation(OperationEnum.SET, key, map.get(key),
                                    map, counterMap, transaction.getTransId());
                            transaction.getStack().push(oprRollback);
                        }
                    }
                    oprExecute = new Operation(OperationEnum.UNSET, key, null,
                            map, counterMap, transaction.getTransId());
                    oprExecute.execute();
                    break;

            }
        }
    }
}
