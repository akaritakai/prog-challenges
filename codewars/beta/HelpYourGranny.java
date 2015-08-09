import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * http://www.codewars.com/kata/help-your-granny/
 */
public class HelpYourGranny {

    // Convert polar coordinates to cartesian coordinates
    public static BiFunction<Double, Double, Point2D> polarToCartesian = (radius, angle) ->
            new Point2D.Double(radius * Math.cos(angle), radius * Math.sin(angle));

    // Generate a map from city to (x,y) coordinate
    public static Map<String, Point2D> toCityPointMap(Map<String, Double> cityDistanceMap) {
        double angle = 0; // the current angle of rotation from the line X0X1 to X0Xn
        Map<String, Point2D> cityPointMap = new HashMap<>();
        Map.Entry<String, Double> previousCityDistancePair = null;
        for (Map.Entry<String, Double> cityDistancePair : cityDistanceMap.entrySet()) {
            if (previousCityDistancePair != null) {
                // increase the angle appropriately
                angle += Math.acos(previousCityDistancePair.getValue() / cityDistancePair.getValue());
            }
            // determine the next city's coordinates
            cityPointMap.put(cityDistancePair.getKey(), polarToCartesian.apply(cityDistancePair.getValue(), angle));
            previousCityDistancePair = cityDistancePair;
        }
        cityPointMap.put("X0", new Point2D.Double(0, 0)); // add in the origin (X0) last to make the loop nice
        return cityPointMap;
    }

    public static int tour(String[] friends, String[][] friendTownMap, Map<String, Double> cityDistanceMap) {
        UnaryOperator<String> townFromFriend = friend ->
                Arrays.stream(friendTownMap)
                        .filter(entry -> entry[0].equals(friend))
                        .findFirst()
                        .map(entry -> entry[1])
                        .orElse(null);

        // Get the tour cities in order
        List<String> visitingOrder = Arrays.stream(friends)
                .map(townFromFriend)
                .filter(town -> town != null)
                .collect(Collectors.toList());

        // We start and end at the origin city
        visitingOrder.add(0, "X0");
        visitingOrder.add("X0");

        // Remove any cities we don't visit
        cityDistanceMap.entrySet().removeIf(x -> !visitingOrder.contains(x.getKey()));

        // Calculate the (x,y) positions of every city
        Map<String, Point2D> cityPointMap = toCityPointMap(cityDistanceMap);

        // Calculate the trip distance
        double distance = 0;
        for (int i = 1; i < visitingOrder.size(); i++) {
            Point2D city1 = cityPointMap.get(visitingOrder.get(i - 1));
            Point2D city2 = cityPointMap.get(visitingOrder.get(i));
            distance += city1.distance(city2);
        }

        return (int) Math.floor(distance);
    }

}
