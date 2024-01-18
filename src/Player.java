import java.util.*;

public class Player {
    private int balance;
    private int races;
    private Car drivingCar;
    private final CarList garage;
    private final Dealership carList = new Dealership();

    public Player() {
        balance = 100000;
        races = 0;
        garage = new CarList();
        garage.addCar(carList.getCar("Mitsubishi"));
        drivingCar = garage.getCar("Mitsubishi");
    }

    public String toString() {
        return String.format("%s: $%,d\n%s: %,d\n%s: %s\n%s: %d\n", "Balance", balance, "Races", races, "Driving", drivingCar.getName(), "Cars Owned", garage.size());
    }

    public Car getDrivingCar() {
        return drivingCar;
    }

    public void addRace() {
        races++;
    }

    public void addToBalance(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int newBalance) {
        balance = newBalance;
    }

    public CarList getGarage() {
        return garage;
    }

    public String selectCar(String name) {
        if (garage.getCar(name).getName() == null && carList.contains(name)) {
            return "You do not own that car";
        }
        else if (!carList.contains(name)) {
            return "That car does not exist";
        }
        drivingCar = garage.getCar(name);
        return "You are now driving your " + drivingCar.getName();
    }

    private void selectCarByIndex(int index) {
        Car carToSelect = garage.getCarByIndex(index);
        if (carToSelect.getName() == null && carList.contains(carToSelect)) {
            return;
        }
        else if (!carList.contains(carToSelect)) {
            return;
        }
        drivingCar = garage.getCar(carToSelect);
    }

    public String buyCar(String name) {
        Car carToBuy = carList.getCar(name);
        if (garage.contains(carToBuy)) {
            return "You already own that car";
        }
        else if (!carList.contains(name)) {
            return "That car does not exist";
        }
        else if (getBalance() < carToBuy.getValue()) {
            return "You do not have enough money! You need $" + (carToBuy.getValue() - getBalance()) + " more";
        }
        garage.addCar(carToBuy);
        balance -= carToBuy.getValue();
        return "You purchased a " + carToBuy.getName() + " for $" + carToBuy.getValue();
    }

    public String sellCar(String name) {
        Car carToBuy = carList.getCar(name);
        if (!carList.contains(name)) {
            return "That car does not exist";
        }
        else if (!garage.contains(carToBuy)) {
            return "You do not own that car";
        }
        else if (garage.size() == 1) {
            return "You can't sell the only car you own";
        }
        if (carToBuy == drivingCar) {
            Random random = new Random();
            int randomIndex;
            do {
                randomIndex = random.nextInt(garage.size());
            } while (randomIndex == garage.indexOf(drivingCar));
            selectCarByIndex(randomIndex);
        }
        garage.removeCar(carToBuy);
        int sellPrice = (int) Math.ceil(0.75 * carToBuy.getValue());
        balance += sellPrice;
        return "You sold your " + carToBuy.getName() + " for $" + sellPrice;
    }
}
