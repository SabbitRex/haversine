package org.lucidity.delivery;

import org.lucidity.delivery.algorithm.PointMatcherAlgorithm;
import org.lucidity.delivery.algorithm.impl.HaversineAlgorithmImpl;
import org.lucidity.delivery.entity.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        //////////////////////////////////////////////////
        // I/O Open
        //////////////////////////////////////////////////

        Scanner scanner = new Scanner(System.in);
        Map<String, Location> locations = new HashMap<>();
        Map<String, Double> preparationTimes = new HashMap<>();

        System.out.println("Enter Aman’s latitude and longitude:");
        locations.put("Aman", Location.builder().latitude(scanner.nextDouble()).longitude(scanner.nextDouble()).build());

        System.out.println("Enter number of restaurants:");
        int numRestaurants = scanner.nextInt();
        for (int i = 1; i <= numRestaurants; i++) {
            System.out.println("Enter R" + i + " latitude and longitude:");
            locations.put("R" + i, Location.builder().latitude(scanner.nextDouble()).longitude(scanner.nextDouble()).build());
            System.out.println("Enter preparation time for R" + i + ":");
            preparationTimes.put("R" + i, scanner.nextDouble());
        }

        System.out.println("Enter number of consumers:");
        int numConsumers = scanner.nextInt();
        for (int i = 1; i <= numConsumers; i++) {
            System.out.println("Enter C" + i + " latitude and longitude:");
            locations.put("C" + i, Location.builder().latitude(scanner.nextDouble()).longitude(scanner.nextDouble()).build());
        }

        scanner.close();

        //////////////////////////////////////////////////
        // I/O Close
        //////////////////////////////////////////////////

        PointMatcherAlgorithm algorithm = HaversineAlgorithmImpl.builder().build();
        List<String> bestRoute = algorithm.findBestRoute(locations, preparationTimes);
        System.out.println("Best delivery route: " + bestRoute);
    }
}