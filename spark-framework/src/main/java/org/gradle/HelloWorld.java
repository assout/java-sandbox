package org.gradle;

import static spark.Spark.*;

// refs : http://qiita.com/disc99/items/3fbdf28ffacaf9b8be8e
public class HelloWorld {
	public static void main(String[] args) {
          get("/hello", (request, response) -> "Hello World!"); 
    }
}
