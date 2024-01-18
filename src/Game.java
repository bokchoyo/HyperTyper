import java.awt.*;
import java.util.*;

public class Game {
    private Race race;
    private int carBonus;
    private int reward;
    private int speedReward;
    private int accuracyReward;
    private final Dealership dealership = new Dealership();
    private final Player player = new Player();
    private Color[] raceTheme = new Color[] {
            new Color(0x000),
            new Color(0xc58aff),
            new Color(0x972fff),
            new Color(0xda3333),
            new Color(0xEBD7FF)
    };
    private final Color[] purple = new Color[] {
            new Color(0x000),
            new Color(0xc58aff),
            new Color(0x972fff),
            new Color(0xda3333),
            new Color(0xEBD7FF)
    };
    private final Color[] shadow = new Color[] {
            new Color(0x000),
            new Color(238, 238, 238),
            new Color(68, 68, 68),
            new Color(255, 255, 255),
            new Color(238, 238, 238)
    };
    private final Color[] matrix = new Color[] {
            new Color(0x000),
            new Color(0x15ff00),
            new Color(0x006500),
            new Color(0xda3333),
            new Color(0xd1ffcd)
    };
    private final Color[] stealth = new Color[] {
            new Color(0x010203),
            new Color(0x383e42),
            new Color(0x5e676e),
            new Color(0xe25303),
            new Color(0x383e42)
    };
    private final Color[] fire = new Color[] {
            new Color(0x0f0000),
            new Color(0xb31313),
            new Color(0x683434),
            new Color(0x2f3cb6),
            new Color(0xffffff)
    };
    private final Color[] hammerhead = new Color[] {
            new Color(0x030613),
            new Color(0x4fcdb9),
            new Color(0x213c53),
            new Color(0xe32b2b),
            new Color(0xe2f1f5)
    };
    private final Color[] ivSpade = new Color[] {
            new Color(0x0c0c0c),
            new Color(0xb7976a),
            new Color(0x404040),
            new Color(0x9d7b7d),
            new Color(0xd3c2c3)
    };
    private final Color[] trance = new Color[] {
            new Color(0x00021b),
            new Color(0xe51376),
            new Color(0x3c4c79),
            new Color(0x02d3b0),
            new Color(0xffffff)
    };
    private final Color[] terra = new Color[] {
            new Color(0x0c100e),
            new Color(0x89c559),
            new Color(0x436029),
            new Color(0xd3ca78),
            new Color(0xf0edd1)
    };
    private final Color[] arch = new Color[] {
            new Color(0x0c0d11),
            new Color(0x7ebab5),
            new Color(0x454864),
            new Color(0xff4754),
            new Color(0xf6f5f5)
    };
    private final Color[] aurora = new Color[] {
            new Color(0x011926),
            new Color(0x00e980),
            new Color(0x245c69),
            new Color(0xb94da1),
            new Color(0xffffff)
    };
    private final Color[] midnight = new Color[] {
            new Color(0x0b0e13),
            new Color(0x60759f),
            new Color(0x394760),
            new Color(0xc27070),
            new Color(0x9fadc6)
    };
    private final Color[] voc = new Color[] {
            new Color(0x190618),
            new Color(0xe0caac),
            new Color(0x4c1e48),
            new Color(0xaf3735),
            new Color(0xeeeae4)
    };
    private final Color[] pulse = new Color[] {
            new Color(0x181818),
            new Color(0x17b8bd),
            new Color(0x53565a),
            new Color(0xda3333),
            new Color(0xe5f4f4)
    };
    private String[] themesList = new String[] {
            "arch", "fire", "hammerhead", "iv spade", "matrix", "midnight", "aurora", "pulse", "purple", "shadow", "stealth", "terra", "trance", "voc"
    };
    public void intro() {
        System.out.println("Welcome to Nitro Monkey, the world's best typing game!\nYou can maximize the console for the best experience\nUse the command 'help' to get started");
    }

    public void play() {
        while (true) {
            System.out.print("Enter command: ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            switch (getFirstWord(command)) {
                case "race" -> {
                    Runnable racing = new Runnable() {
                        @Override
                        public void run() {
                            race = new Race(raceTheme);
                        }
                    };
                    racing.run();
                    do {
                        System.out.print("");
                    } while (!race.isFinished());
                    calculateReward();
                    String speedString = race.getSpeed() + " WPM Speed";
                    String accuracyString = race.getAccuracy() + "% Accuracy";
                    System.out.println("_".repeat(30));
                    System.out.printf("%21s\n", "RACE RESULTS");
                    System.out.printf("%-20s%10s\n".repeat(4), speedString, "$" + speedReward, accuracyString, "$" + accuracyReward, player.getDrivingCar().getName() + " Bonus", "$" + carBonus, "Total", "$" + reward);
                    System.out.println("_".repeat(30));
                }
                case "shop" -> {
                    System.out.println(withBorder(dealership.toString()));
                }
                case "profile" -> {
                    System.out.println(withBorder(player.toString()));
                }
                case "drive" -> {
                    String carName = withoutFirstWord(command);
                    System.out.println(player.selectCar(carName));
                }
                case "buy" -> {
                    String carName = withoutFirstWord(command);
                    System.out.println(player.buyCar(carName));
                }
                case "sell" -> {
                    String carName = withoutFirstWord(command);
                    System.out.println(player.sellCar(carName));
                }
                case "garage" -> {
                    System.out.println(withBorder(String.format("%34s\n\n", "YOUR GARAGE") + player.getGarage().toString()));
                }
                case "help" -> {
                    System.out.println(withBorder(commandsHelp()));
                }
                case "sort" -> {
                    String order = withoutFirstWord(command);
                    sort(order);
                    System.out.println("The dealership and your garage are now sorted by " + order + ".");
                }
                case "themes" -> {
                    System.out.println(withBorder(String.format("%17s\n" + "%-14s%14s\n".repeat(7) + "Use 'theme <name>' to select\n", "THEMES", "Arch", "Aurora", "Fire", "Hammerhead", "IV Spade", "Matrix", "Midnight", "Pulse", "Purple", "Shadow", "Stealth", "Terra", "Trance", "Voc")));
                }
                case "theme" -> {
                    String themeName = withoutFirstWord(command).toLowerCase().trim();
                    if (Arrays.asList(themesList).contains(themeName)) {
                        selectTheme(themeName);
                        System.out.println("You are now using the " + themeName + " theme");
                    }
                    else {
                        System.out.println("That theme does not exist");
                    }
                }
                default -> {
                    System.out.println("That is not a valid command");
                }
            }
        }
    }

    private void selectTheme(String name) {
        switch(name.toLowerCase()) {
            case "arch" -> raceTheme = arch;
            case "aurora" -> raceTheme = aurora;
            case "fire" -> raceTheme = fire;
            case "hammerhead" -> raceTheme = hammerhead;
            case "iv spade" -> raceTheme = ivSpade;
            case "matrix" -> raceTheme = matrix;
            case "midnight" -> raceTheme = midnight;
            case "pulse" -> raceTheme = pulse;
            case "purple" -> raceTheme = purple;
            case "shadow" -> raceTheme = shadow;
            case "stealth" -> raceTheme = stealth;
            case "terra" -> raceTheme = terra;
            case "trance" -> raceTheme = trance;
            case "voc" -> raceTheme = voc;
        }
    }

    private void sort(String order) {
        switch (order) {
            case "top speed increasing" -> {
                player.getGarage().sortTopSpeedIncreasing();
                dealership.sortTopSpeedIncreasing();
            }
            case "top speed decreasing" -> {
                player.getGarage().sortTopSpeedDecreasing();
                dealership.sortTopSpeedDecreasing();
            }
            case "acceleration increasing" -> {
                player.getGarage().sortAccelerationIncreasing();
                dealership.sortAccelerationIncreasing();
            }
            case "acceleration decreasing" -> {
                player.getGarage().sortAccelerationDecreasing();
                dealership.sortAccelerationDecreasing();
            }
            case "price increasing" -> {
                player.getGarage().sortValueIncreasing();
                dealership.sortValueIncreasing();
            }
            case "price decreasing" -> {
                player.getGarage().sortValueDecreasing();
                dealership.sortValueDecreasing();
            }
            case "a to z" -> {
                player.getGarage().sortAToZ();
                dealership.sortAToZ();
            }
            case "z to a" -> {
                player.getGarage().sortZToA();
                dealership.sortZToA();
            }
        }
    }

    private String commandsHelp() {
        String help = "";
        help += String.format("%-20s%s\n".repeat(15),
                "COMMAND", "DESCRIPTION",
                "race", "Type a text to earn money",
                "profile", "View your player information",
                "garage", "View the cars you own",
                "drive <car name>", "Select a car to drive",
                "shop", "View the dealership",
                "buy <car name>", "Buy a car",
                "sell <car name>", "Sell a car for 75% of its value",
                "themes", "View all typing text color themes",
                "help", "View all commands",
                "sort", "Sort the dealership and your garage in order of:",
                "", "     top speed increasing / decreasing",
                "", "     acceleration increasing / decreasing",
                "", "     price increasing / decreasing",
                "", "     a to z / z to a");
        return help;
    }

    private String withBorder(String content) {
        int length = getWidth(content);
        return "_".repeat(length) + "\n" + content + "_".repeat(length);
    }

    private static int getWidth(String text) {
        String[] lines = text.split("\n");
        int maxLength = 0;
        for (String line : lines) {
            int lineLength = line.length();
            maxLength = Math.max(maxLength, lineLength);
        }
        return maxLength;
    }

    private String getFirstWord(String command) {
        String[] words = command.trim().split(" ");
        return words[0];
    }

    private String withoutFirstWord(String command) {
        String[] words = command.trim().split(" ");
        words[0] = "";
        return String.join(" ", words).trim();
    }

    private void calculateReward() {
        double accuracy = race.getAccuracy();
        double speed = race.getSpeed();
        speedReward = (int) (speed * 10);
        accuracyReward = (int) Math.floor(1000 * Math.pow(accuracy / 100, 25));
        calculateCarBonus(player.getDrivingCar(), speedReward + accuracyReward);
        reward = speedReward + accuracyReward + carBonus;
        player.addRace();
        if ((long) player.getBalance() + reward > 2147483647) {
            player.setBalance(2147483647);
        }
        else {
            player.addToBalance(reward);
        }
    }

    private void calculateCarBonus(Car car, double reward) {
        int rewardTopSpeed = car.getTopSpeed() - 150;
        int sign = 1;
        if (rewardTopSpeed < 0) {
            rewardTopSpeed = 150 - car.getTopSpeed();
            sign = -1;
        }
        double accelerationBonus = reward * ((4.5 / (car.getAcceleration() - 0.5)) - 1);
        double topSpeedBonus = sign * (Math.pow(rewardTopSpeed, 1.303764)) * 10;
        carBonus =  (int) (accelerationBonus + topSpeedBonus);
    }
}
