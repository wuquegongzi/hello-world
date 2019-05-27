package com.leon.core.java8.recon.template_method;

/**
 * 模板方法 实现
 */
public class OnlineBankingDemo1 extends OnlineBanking {

    public static void main(String[] args) {

        OnlineBankingDemo1 onlineBankingDemo1 = new OnlineBankingDemo1();
        onlineBankingDemo1.makeCustomerHappy(new Customer());

    }

    @Override
    void makeCustomerHappy(Customer c) {
        System.out.println("Hello "+c.getName());
    }
}
