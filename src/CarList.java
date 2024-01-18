import java.util.*;

public class CarList {
    private final ArrayList<Car> carList;

    public CarList() {
        carList = new ArrayList<Car>();
    }

    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append(String.format("%-15s%-15s%-15s%s\n", "CAR", "PRICE", "TOP SPEED", "0-60 MPH (S)"));
        for (Car car : carList) {
            display.append(String.format("%-15s$%,-14d%-15d%s\n", car.getName(), car.getValue(), car.getTopSpeed(), String.valueOf(car.getAcceleration())));
        }
        return String.valueOf(display);
    }

    public void setIds() {
        sortAToZ();
        for (int i = 0; i < carList.size(); i++) {
            carList.get(i).setId(i + 1);
        }
    }

    public boolean contains(String name) {
        return carList.contains(getCar(name));
    }

    public boolean contains(Car car) {
        return carList.contains(car);
    }

    public int size() {
        return carList.size();
    }

    public int indexOf(Car car) {
        return carList.indexOf(car);
    }

    public void addAllCars(List<Car> cars) {
        for (Car car : cars) {
            addCar(car);
        }
    }

    public void addCar(Car car) {
        carList.add(car);
    }

    public void removeCar(Car car) {
        carList.remove(car);
    }

    public Car getCar(int id) {
        for (Car car : carList) {
            if (car.getId() == id) {
                return car;
            }
        }
        return getCar("Mitsubishi");
    }

    public Car getCarByIndex(int index) {
        if (index < carList.size()) {
            return carList.get(index);
        }
        else {
            return new Car();
        }
    }

    public Car getCar(String name) {
        if (getId(name) == -1) {
            return new Car();
        }
        return getCar(getId(name));
    }

    public Car getCar(Car car) {
        return getCar(car.getName());
    }

    public int getId(String name) {
        for (Car car : carList) {
            if (car.getName().equalsIgnoreCase(name)) {
                return car.getId();
            }
        }
        return -1;
    }

    public void sortAToZ() {
        carList.sort((car1, car2) -> car1.getName().toLowerCase().compareTo(car2.getName().toLowerCase()));
    }

    public void sortZToA() {
        carList.sort((car1, car2) -> car2.getName().toLowerCase().compareTo(car1.getName().toLowerCase()));
    }

    public void sortValueDecreasing() {
        carList.sort(new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return car2.getValue() - car1.getValue();
            }
        });
    }

    public void sortValueIncreasing() {
        carList.sort(new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return car1.getValue() - car2.getValue();
            }
        });
    }

    public void sortTopSpeedDecreasing() {
        carList.sort(new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return car2.getTopSpeed() - car1.getTopSpeed();
            }
        });
    }

    public void sortTopSpeedIncreasing() {
        carList.sort(new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return car1.getTopSpeed() - car2.getTopSpeed();
            }
        });
    }

    public void sortAccelerationDecreasing() {
        carList.sort(new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return (int) ((car2.getAcceleration() - car1.getAcceleration()) * 100);
            }
        });
    }

    public void sortAccelerationIncreasing() {
        carList.sort(new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return (int) ((car1.getAcceleration() - car2.getAcceleration()) * 100);
            }
        });
    }
}
