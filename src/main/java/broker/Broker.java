package broker;

import org.joda.time.LocalTime;

public class Broker {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);

        System.out.println("Welcome to the broker of the event system");
    }
}
