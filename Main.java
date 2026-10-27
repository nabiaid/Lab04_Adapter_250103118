public class Main {
    private static void quote(DeliveryCalculator calculator, String label, int weightKg, int distanceKm) {
        int cost = calculator.calculate(weightKg, distanceKm);
        System.out.println(label + " | weight: " + weightKg + " kg | distance: " + distanceKm
                + " km | cost: " + cost + " KZT");
    }

    public static void main(String[] args) {
        int weight = 2;
        int distance = 10;

        DeliveryCalculator calculator = new DeliveryCalculator(new StandardDelivery());
        quote(calculator, "Standard", weight, distance);

        calculator.setStrategy(new ExpressDelivery());
        quote(calculator, "Express", weight, distance);

        calculator.setStrategy(new EconomyDelivery());
        quote(calculator, "Economy", weight, distance);

        calculator.setStrategy(new StandardDelivery());
        quote(calculator, "Standard again", weight, distance);

        System.out.println();

        DeliveryStrategy promotional = (w, d) -> 400;
        calculator.setStrategy(promotional);
        quote(calculator, "Promotional", 2, 10);
        quote(calculator, "Promotional", 7, 25);
    }
}
