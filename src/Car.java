public class Car {
    private int id;
    private String name;
    private int value;
    private int topSpeed;
    private double acceleration;

    public Car(String name, int value, int topSpeed, double acceleration) {
        this.id = 0;
        this.name = name;
        this.value = value;
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
    }

    public Car() {}

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
