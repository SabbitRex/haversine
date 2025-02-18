package org.lucidity.delivery.algorithm.impl;

import lombok.Builder;
import org.lucidity.delivery.algorithm.PointMatcherAlgorithm;
import org.lucidity.delivery.entity.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lucidity.delivery.constant.Constant.AVERAGE_SPEED_KMH;
import static org.lucidity.delivery.constant.Constant.EARTH_RADIUS;

@Builder
public class HaversineAlgorithmImpl implements PointMatcherAlgorithm {

    @Override
    public double getDistance(Location loc1, Location loc2) {

        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    @Override
    public List<String> findBestRoute(Map<String, Location> locations, Map<String, Double> preparationTimes) {

        List<String> locationsToVisit = new ArrayList<>(locations.keySet());
        locationsToVisit.remove("Aman");
        List<List<String>> permutations = new ArrayList<>();
        permute(new ArrayList<>(locationsToVisit), 0, permutations);

        double minTime = Double.MAX_VALUE;
        List<String> bestRoute = null;

        for (List<String> route : permutations) {
            double totalTime = 0;
            Location current = locations.get("Aman");
            Map<String, Double> pickupTimes = new HashMap<>(preparationTimes);

            for (String stop : route) {
                double travelTime = travelTime(current, locations.get(stop));
                totalTime = Math.max(totalTime + travelTime, pickupTimes.getOrDefault(stop, 0.0));
                current = locations.get(stop);
            }

            if (totalTime < minTime) {
                minTime = totalTime;
                bestRoute = new ArrayList<>(route);
            }
        }
        return bestRoute;
    }

    public double travelTime(Location loc1, Location loc2) {
        return this.getDistance(loc1, loc2) / AVERAGE_SPEED_KMH;
    }

    private void permute(List<String> arr, int k, List<List<String>> result) {
        if (k == arr.size() - 1) {
            result.add(new ArrayList<>(arr));
            return;
        }
        for (int i = k; i < arr.size(); i++) {
            Collections.swap(arr, i, k);
            permute(arr, k + 1, result);
            Collections.swap(arr, i, k);
        }
    }
}
