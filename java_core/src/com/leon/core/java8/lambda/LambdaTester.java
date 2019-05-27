package com.leon.core.java8.lambda;

import java.util.Arrays;

/**
 * Lambda 表达式实例
 */
public class LambdaTester {

    public static void main(String[] args) {

        Arrays.asList( "a", "b", "d" ).forEach(e -> System.out.println( e ) );
        //显式指定该参数的类型
        Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> System.out.println( e ) );

        Arrays.asList( "a", "b", "d" ).forEach( e -> {
            System.out.print( e );
            System.out.print( e );
        } );

        String separator = ",";
        Arrays.asList( "a", "b", "d" ).forEach(
                ( String e ) -> System.out.print( e + separator ) );

    }

}
