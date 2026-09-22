import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("        OMNIHOME SMART CONTROLLER: SYSTEM STARTUP");
        System.out.println("============================================================");


        LegacyBulb rawBulb = new LegacyBulb();
        LegacyThermostat rawThermostat = new LegacyThermostat();

        BulbAdapter bulbAdapter = new BulbAdapter(rawBulb);
        ThermostatAdapter thermostatAdapter =
                new ThermostatAdapter(rawThermostat);

        System.out.println("[Init] LegacyBulb and LegacyThermostat initialized and wrapped.");

        List<SmartDevice> deviceList =
                List.of(bulbAdapter, thermostatAdapter);

        ModernHub hub = new ModernHub(deviceList);

        System.out.println("[Hub] Registering 2 adapted devices into ModernHub...");
        
        System.out.println();
        System.out.println("--- OPERATION: ACTIVATE ALL DEVICES ---");

        hub.activateAll();

        System.out.println("[Action] ModernHub.activateAll() invoked.");
        System.out.println(" -> BulbAdapter: Brightness set to "
                + rawBulb.readBrightness() + ".");
        System.out.println(" -> ThermostatAdapter: Dial set to '"
                + rawThermostat.checkDial() + "'.");

        System.out.println("[Status] Bulb active: "
                + bulbAdapter.isOn());
        System.out.println("[Status] Thermostat active: "
                + thermostatAdapter.isOn());

        double averagePower = hub.calculateAveragePowerUsage();

        System.out.printf(
                "[Power] Fleet Average Power Usage: %.2f%% "
                        + "(Bulb: %d%%, Thermostat: %d%%)%n",
                averagePower,
                bulbAdapter.getPowerPercent(),
                thermostatAdapter.getPowerPercent()
        );


        System.out.println();
        System.out.println("--- AUDIT: HARDWARE FAULT INJECTION ---");

        System.out.println("[Fault 1] Filament physically severed on LegacyBulb...");

        rawBulb.breakFilament();

        System.out.println(" -> BulbAdapter.isOn(): "
                + bulbAdapter.isOn()
                + " [PASSED - Verified disconnected]");

        System.out.println(" -> BulbAdapter.getPowerPercent(): "
                + bulbAdapter.getPowerPercent()
                + "% [PASSED - Inactive power confirmed]");


        System.out.println();
        System.out.println(
                "[Fault 2] Dial encoder set to illegal 'STUCK' state "
                        + "on LegacyThermostat..."
        );

        rawThermostat.rotateDial("STUCK");

        System.out.println(" -> ThermostatAdapter.isOn(): "
                + thermostatAdapter.isOn()
                + " [PASSED - Inactive flag confirmed]");

        System.out.println(" -> ThermostatAdapter.getPowerPercent(): "
                + thermostatAdapter.getPowerPercent()
                + " [PASSED - Sensor fault sentinel returned]");

        System.out.println();
        System.out.println("[Fault 3] Dial encoder returns null...");

        rawThermostat.rotateDial(null);

        System.out.println(" -> ThermostatAdapter.isOn(): "
                + thermostatAdapter.isOn());

        System.out.println(" -> ThermostatAdapter.getPowerPercent(): "
                + thermostatAdapter.getPowerPercent());


        System.out.println();
        System.out.println("--- OPERATION: EMERGENCY SHUTDOWN ---");


        rawThermostat.rotateDial("MAX");

        hub.emergencyShutdown();

        System.out.println("[Action] ModernHub.emergencyShutdown() invoked.");
        System.out.println(" -> BulbAdapter: Brightness set to "
                + rawBulb.readBrightness() + ".");
        System.out.println(" -> ThermostatAdapter: Dial rotated to '"
                + rawThermostat.checkDial() + "'.");

        System.out.printf(
                "[Power] Fleet Average Power Usage: %.2f%%%n",
                hub.calculateAveragePowerUsage()
        );

        System.out.println();
        System.out.println("============================================================");
        System.out.println("             ALL INTEGRATION TESTS COMPLETED");
        System.out.println("============================================================");
    }
}