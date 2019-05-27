package com.leon.core.java8.recon.template_method;

import java.util.function.Consumer;

/**
 * 模板方法  lambda实现
 */
public class OnlineBankingLambda {


    public static void main(String[] args) {
        new OnlineBankingLambda()
                .processCustomer(1337, (Customer c) -> System.out.println("Hello " + c.getName()));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // dummy Database class
    static private class Database{
        static Customer getCustomerWithId(int id){ return new Customer();}
    }

}