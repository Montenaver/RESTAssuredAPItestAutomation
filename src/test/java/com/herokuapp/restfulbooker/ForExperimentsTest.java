package com.herokuapp.restfulbooker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ForExperimentsTest {
    // Java code to print the elements of Stream
    // without using double colon operator
    public static void main(String[] args)
    {

        // Get the stream
        Stream<String> stream
                = Stream.of("Geeks", "For",
                "Geeks", "A",
                "Computer",
                "Portal");

        // Print the stream
        stream.forEach(s -> System.out.println(s));
    }

    // static function to be called
    static void someFunction(String s)
    {
        System.out.println(s);
    }

    // Java code to print the elements of Stream
    // using double colon operator
    public static void main2(String[] args)
    {

        // Get the stream
        Stream<String> stream
                = Stream.of("Geeks", "For",
                "Geeks", "A",
                "Computer",
                "Portal");

        // Print the stream
        // using double colon operator
        stream.forEach(System.out::println);
    }

    // Java code to show use of double colon operator
    // for static methods
    public static void main3(String[] args)
    {

        List<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("For");
        list.add("GEEKS");

        // call the static method
        // using double colon operator
        list.forEach(ForExperimentsTest::someFunction);
    }

    // Java code to show use of double colon operator
    // for instance methods
    // instance function to be called
    void someFunction2(String s)
    {
        System.out.println(s);
    }

    public static void main4(String[] args)
    {

        List<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("For");
        list.add("GEEKS");

        // call the instance method
        // using double colon operator
        list.forEach((new ForExperimentsTest())::someFunction2);
    }
}

