package main;

import static com.google.common.base.MoreObjects.firstNonNull;

public class Main {

    public static void main(String[] args) {
        String login = "Kate";
        String name = null;

        System.out.println(firstNonNull(name,login));
    }
}
