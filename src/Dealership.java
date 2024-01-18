import java.util.*;

public class Dealership extends CarList{

    public Dealership() {
        addAllCars(List.of(
                new Car("Mitsubishi", 46000, 178, 3.6),
                new Car("Lamborghini", 250000, 221, 2.2),
                new Car("Ferrari", 280000, 249, 1.9),
                new Car("Mclaren", 2250000, 250, 2.5),
                new Car("McMurtry", 1050000, 190, 1.4),
                new Car("Devel", 2200000, 350, 1.8),
                new Car("Bugatti", 1900000, 311, 2.1),
                new Car("Chaos", 14400000, 311, 1.5),
                new Car("Koenigsegg", 3000000, 330, 1.9),
                new Car("Pagani", 2190000, 240, 2.6),
                new Car("Hennessey", 3000000, 311, 2.5),
                new Car("Acura", 169000, 191, 2.9),
                new Car("Audi", 159000, 206, 3.0),
                new Car("Honda", 44000, 171, 4.9),
                new Car("Rimac", 2100000, 258, 1.72),
                new Car("Aspark", 4200000, 260, 1.74),
                new Car("Toyota", 45000, 155, 4.6),
                new Car("Nissan", 120000, 205, 2.6),
                new Car("Porsche", 114000, 214, 2.1),
                new Car("Tesla", 75000, 200, 1.98),
                new Car("Lucid", 79000, 205, 1.89),
                new Car("Aston Martin", 2500000, 250, 2.5),
                new Car("Jaguar", 350000, 217, 3.5),
                new Car("Czinger", 2000000, 281, 1.9),
                new Car("Lexus", 97000, 202, 3.5),
                new Car("Hyundai", 42000, 162, 3.3),
                new Car("Kia", 43000, 161, 3.4),
                new Car("Mercedes", 160000, 217, 2.8),
                new Car("Ford", 400000, 216, 3.0),
                new Car("Chevrolet", 70000, 212, 2.8),
                new Car("Dodge", 33000, 215, 1.66)
        ));
        setIds();
        sortAToZ();
    }

    @Override
    public String toString() {
        return String.format("%33s\n\n", "DEALERSHIP") + super.toString();
    }
}
