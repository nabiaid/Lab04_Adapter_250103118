public class StandardDelivery implements DeliveryStrategy {
    @Override
    public int calculateCost(int weightKg, int distanceKm) {
        return 500 + 100 * weightKg + 50 * distanceKm;
    }
}
