interface Image {
    void show();
}

class RealImage implements Image {
    private final String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        System.out.println("--> [SLOW] Reading " + fileName + " from hard drive...");
    }

    public void show() {
        System.out.println("Displaying " + fileName + " on screen!");
    }
}

class ProxyImage implements Image {
    private final String fileName;
    private final String userRole;
    private RealImage realImage;

    public ProxyImage(String fileName, String userRole) {
        this.fileName = fileName;
        this.userRole = userRole;
    }

    public void show() {
        if (!"ADMIN".equals(userRole)) {
            System.out.println("[ACCESS DENIED] You lack permission to view: " + fileName);
            return;
        }
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.show();
    }
}

interface Video {
    void play();
}

class RealVideo implements Video {
    private final String fileName;

    public RealVideo(String fileName) {
        this.fileName = fileName;
        System.out.println("--> [DOWNLOADING] Connecting to server & buffering 4K video: " + fileName);
    }

    public void play() {
        System.out.println("Playing video: " + fileName);
    }
}

class ProxyVideo implements Video {
    private final String fileName;
    private RealVideo realVideo;
    private int viewCount = 0;

    public ProxyVideo(String fileName) {
        this.fileName = fileName;
    }

    public void play() {
        viewCount++;
        System.out.println("--> [AUDIT] Video has been played " + viewCount + " time(s).");
        if (realVideo == null) {
            realVideo = new RealVideo(fileName);
        }
        realVideo.play();
    }
}

interface Internet {
    void connectTo(String site);
}

class RealInternet implements Internet {
    public void connectTo(String site) {
        System.out.println("Connected to site: " + site);
    }
}

class SchoolProxyInternet implements Internet {
    private RealInternet realInternet;

    public void connectTo(String site) {
        if (site.equals("gaming.com") || site.equals("socialmedia.com")) {
            System.out.println("[BLOCKED BY FIREWALL] Cannot access: " + site);
            return;
        }
        if (realInternet == null) {
            realInternet = new RealInternet();
        }
        realInternet.connectTo(site);
    }
}

interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int level);
}

class Radio implements Device {
    public void turnOn() {
        System.out.println("Radio starts playing static/music.");
    }

    public void turnOff() {
        System.out.println("Radio shuts off.");
    }

    public void setVolume(int level) {
        System.out.println("Radio volume knob turned to: " + level);
    }
}

class SmartLight implements Device {
    public void turnOn() {
        System.out.println("Smart Light glows bright white.");
    }

    public void turnOff() {
        System.out.println("Smart Light shuts off.");
    }

    public void setVolume(int level) {
        System.out.println("Smart Light brightness set to: " + level + "%");
    }
}

class BasicRemote {
    protected Device device;

    public BasicRemote(Device device) {
        this.device = device;
    }

    public void powerOn() {
        device.turnOn();
    }

    public void powerOff() {
        device.turnOff();
    }
}

class AdvancedRemote extends BasicRemote {
    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        System.out.println("[Remote] Mute button pressed!");
        device.setVolume(0);
    }
}

class PartyRemote extends BasicRemote {
    public PartyRemote(Device device) {
        super(device);
    }

    public void partyMode() {
        System.out.println("[Party Remote] Pumping up the party!");
        device.turnOn();
        device.setVolume(100);
    }
}

interface Weapon {
    void use();
}

class Sword implements Weapon {
    public void use() {
        System.out.println("Slash! Dealing 20 physical damage.");
    }
}

class MagicWand implements Weapon {
    public void use() {
        System.out.println("Zap! Dealing 35 magic damage.");
    }
}

abstract class Hero {
    protected Weapon weapon;

    public Hero(Weapon weapon) {
        this.weapon = weapon;
    }
}

class Knight extends Hero {
    public Knight(Weapon weapon) {
        super(weapon);
    }

    public void attack() {
        weapon.use();
    }
}

class Wizard extends Hero {
    public Wizard(Weapon weapon) {
        super(weapon);
    }

    public void attack() {
        weapon.use();
    }
}

public class Lab {
    public static void main(String[] args) {
        System.out.println("=== Task 1 ===");
        new ProxyImage("salary_sheet.png", "GUEST").show();
        new ProxyImage("salary_sheet.png", "ADMIN").show();

        System.out.println("\n=== Task 2 ===");
        Video video = new ProxyVideo("Cat_Memes.mp4");
        video.play();
        video.play();
        video.play();

        System.out.println("\n=== Task 3 ===");
        Internet internet = new SchoolProxyInternet();
        internet.connectTo("wikipedia.org");
        internet.connectTo("gaming.com");

        System.out.println("\n=== Task 4 ===");
        Device light = new SmartLight();
        AdvancedRemote remote = new AdvancedRemote(light);
        remote.powerOn();
        remote.mute();

        System.out.println("\n=== Task 5 ===");
        PartyRemote party = new PartyRemote(new Radio());
        party.partyMode();

        System.out.println("\n=== Task 6 ===");
        new Knight(new Sword()).attack();
        new Knight(new MagicWand()).attack();
    }
}
