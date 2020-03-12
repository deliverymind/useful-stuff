package uk.co.automatictester.java9;

import org.testng.annotations.Test;

public class PrivateInterfaceMethodTest {

    interface MyInterface {

        default void doStuff() {
            doPublicStuff();
            doPrivateStuff();
            doStaticStuff();
        }

        private void doPublicStuff() {
            System.out.println("Do public stuff");
        }

        private void doPrivateStuff() {
            System.out.println("Do private stuff");
        }

        private static void doStaticStuff() {
            System.out.println("Do static stuff");
        }
    }

    @Test
    public void test() {
        new MyInterface() {
        }.doStuff();
    }
}
