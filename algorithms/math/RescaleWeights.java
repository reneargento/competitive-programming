package algorithms.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/12/17.
 */
public class RescaleWeights {

    // Rescale weights between range [minWeightInScale, maxWeightInScale]
    // Based on https://stackoverflow.com/questions/5294955/how-to-scale-down-a-range-of-numbers-with-a-known-min-and-max-value
    private List<Double> rescaleWeights(List<Double> weights, double minWeightInScale, double maxWeightInScale) {
        if (weights == null || weights.isEmpty()) {
            return new ArrayList<>();
        }

        double minWeightOriginal = weights.get(0);
        double maxWeightOriginal = weights.get(0);

        for (double weight : weights) {
            if (weight < minWeightOriginal) {
                minWeightOriginal = weight;
            }
            if (weight > maxWeightOriginal) {
                maxWeightOriginal = weight;
            }
        }

        List<Double> rescaledWeights = new ArrayList<>();

        for (double weight : weights) {
            double rescaledWeight = ((maxWeightInScale - minWeightInScale) * (weight - minWeightOriginal)
                    / (maxWeightOriginal - minWeightOriginal)) + minWeightInScale;
            rescaledWeights.add(rescaledWeight);
        }

        return rescaledWeights;
    }
}
