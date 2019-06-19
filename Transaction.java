package com.company;

import java.util.*;

public class Transaction {
    private Stack<Operation> stack;
    private int transId;

    public Transaction() {
        stack = new Stack<>();
        transId = -1;
    }

    public int getTransId() {
        return transId;
    }

    public Stack getStack() {
        return stack;
    }


    public void beginTransaction() {
        transId++;
    }

    public void commitTransaction() {
        if (stack.empty() && transId == -1) {
            System.out.println("NO TRANSACTION");
            return;
        }
        stack.clear();
        transId = -1;
    }

    public void rollbackTransaction() {
        if (stack.empty()) {
            System.out.println("NO TRANSACTION");
            return;
        }
        while(stack.size()>0 && stack.peek().transId == this.transId) {
            Operation operation = stack.pop();
            operation.execute();
        }
        transId--;
    }

    public boolean hasTransaction() {
        return transId != -1;
    }
}
