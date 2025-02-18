package org.lucidity.delivery.algorithm;

import org.lucidity.delivery.entity.Location;

import java.util.List;
import java.util.Map;

public interface PointMatcherAlgorithm {
    double getDistance(Location loc1, Location loc2);
    List<String> findBestRoute(Map<String, Location> locations, Map<String, Double> preparationTimes);
    double travelTime(Location loc1, Location loc2);
}
