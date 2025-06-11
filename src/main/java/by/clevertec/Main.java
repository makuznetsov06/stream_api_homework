package by.clevertec;

import by.clevertec.model.*;
import by.clevertec.util.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        //Отбираем всех животных от 10 до 20 лет и сортируем по возрасту
        List<Animal> filteredAndSortedAnimals = animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip((3-1) * 7) //так как нужен 3-й зоопарк с распределением по 7 живаотным
                .limit(7)
                .collect(Collectors.toList());
        System.out.println(filteredAndSortedAnimals);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .filter(animal -> animal.getGender().equals("Female"))
                .forEach(animal -> System.out.printf("Порода %s\n", animal.getBread().toUpperCase()));
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        Set<String> originCountriesStartingWithA  = animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin ->origin.startsWith("A"))
                .collect(Collectors.toSet());

        originCountriesStartingWithA.stream()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        long femaleAnimalCount = animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count();
        System.out.println("Количество всех животных пола Female = " + femaleAnimalCount);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        boolean isAnyHungarianAnimal = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));
        System.out.println("Есть ли хотя бы одно животное из Венгрии? - " + isAnyHungarianAnimal);
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        Set<String> validGenders = Set.of("Male", "Female");
        boolean allValidGenders = animals.stream()
                .map(Animal::getGender)
                .allMatch(gender -> validGenders.contains(gender));
        System.out.println("Все животные пола Male/Female? - " + allValidGenders);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        boolean noOceaniaMatch = animals.stream()
                .noneMatch(animal -> animal.getOrigin().equals("Oceania"));
        String message = noOceaniaMatch ?
                "Ни одно животное не имеет страну происхождения Oceania." :
                "Среди животных есть такие, чья страна происхождения — Oceania.";
        System.out.println(message);
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        animals.sort(Comparator.comparing(Animal::getBread));
        List<Animal> firstHundredAnimals = animals.subList(0, Math.min(100, animals.size()));
        Optional<Animal> oldestAnimal = firstHundredAnimals.stream()
                .max(Comparator.comparingInt(Animal::getAge));
        if (oldestAnimal.isPresent()){
            System.out.println("Возраст самого старого животного среди первых 100: " + oldestAnimal.get().getAge());
        } else {
            System.out.println("Нет животных для обработки.");
        }
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        int shortestBreadCharLength = animals.stream()
                .map(animal -> animal.getBread())
                .mapToInt(bread -> bread.toCharArray().length)
                .min()
                .orElse(-1);
        if (shortestBreadCharLength != -1) {
            System.out.println("Длина самой короткой породы: " + shortestBreadCharLength);
        } else {
            System.out.println("Нет животных для обработки.");
        }
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        int totalAge = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println("Суммарный возраст всех животных: " + totalAge);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        OptionalDouble averageAgeOfIndonesianAnimals = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average();
        if (averageAgeOfIndonesianAnimals.isPresent()) { // Проверяем, существует ли среднее значение
            System.out.println(averageAgeOfIndonesianAnimals.getAsDouble());
        }
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        List<Person> selectedCandidates = persons.stream()
                .filter(person -> person.getGender().equals("Male")
                        && Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 18
                        && Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() <= 27)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .toList();

        selectedCandidates.forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        List<Person> evacuationPersonsList = new ArrayList<>();
        List<Person> hospitalizedPeople = houses.stream()
                .filter(house -> house.getBuildingType().equals("Hospital"))
                .flatMap(house -> house.getPersonList().stream())
                .toList();

        evacuationPersonsList.addAll(hospitalizedPeople);

        if (evacuationPersonsList.size() < 500) {
            List<Person> civilianPeople = houses.stream()
                    .filter(house -> !house.getBuildingType().equals("Hospital"))
                    .flatMap(house -> house.getPersonList().stream())
                    .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() < 18
                            || Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 65)
                    .limit(500 - evacuationPersonsList.size())
                    .toList();
            evacuationPersonsList.addAll(civilianPeople);
        } else {
            System.out.println(evacuationPersonsList);
        }

        if (evacuationPersonsList.size() < 500) {
            List<Person> civilianPeople = houses.stream()
                    .filter(house -> !house.getBuildingType().equals("Hospital"))
                    .flatMap(house -> house.getPersonList().stream())
                    .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 18
                            || Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() < 65)
                    .limit(500 - evacuationPersonsList.size())
                    .toList();
            evacuationPersonsList.addAll(civilianPeople);
            System.out.println(evacuationPersonsList);
        }
    }

    public static void task14() {
        List<Car> cars = Util.getCars();
        Map<Integer, List<Car>> carsByCountryMap = cars.stream()
                .collect(Collectors.groupingBy(
                        car -> Main.carGroupSeparator(car),
                        Collectors.toList()
                ));

        BigDecimal totalCost = BigDecimal.ZERO;

        System.out.println("Суммарные затраты по странам:");
        for (int i = 0; i < carsByCountryMap.size(); i++) {
            double countryMass = carsByCountryMap.get(i)
                    .stream()
                    .mapToDouble(Car::getMass)
                    .sum();

            BigDecimal costPerCountry = BigDecimal.valueOf(countryMass / 1000 * 7.14);
            totalCost = totalCost.add(costPerCountry);

            switch (i) {
                case 0:
                    System.out.printf("Туркмения: %.2f USD\n", costPerCountry.doubleValue());
                    break;
                case 1:
                    System.out.printf("Узбекистан: %.2f USD\n", costPerCountry.doubleValue());
                    break;
                case 2:
                    System.out.printf("Казахстан: %.2f USD\n", costPerCountry.doubleValue());
                    break;
                case 3:
                    System.out.printf("Кыргызстан: %.2f USD\n", costPerCountry.doubleValue());
                    break;
                case 4:
                    System.out.printf("Россия: %.2f USD\n", costPerCountry.doubleValue());
                    break;
                case 5:
                    System.out.printf("Монголия: %.2f USD\n", costPerCountry.doubleValue());
                    break;
            }
        }

        System.out.printf("\nОбщая стоимость транспортировки: %.2f USD\n", totalCost.doubleValue());

    }
    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        Function<List<String>, Boolean> isValidVaseMaterials = materials ->
                materials.contains("Glass") ||
                        materials.contains("Aluminum") ||
                        materials.contains("Steel");
        List<Flower> filteredFlowers = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparingInt(Flower::getPrice).reversed()
                        .thenComparingDouble(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> flower.getCommonName().toLowerCase().charAt(0) >= 99
                        && flower.getCommonName().toLowerCase().charAt(0) <= 115)
                .filter(flower -> flower.isShadePreferred())
                .filter(flower -> isValidVaseMaterials.apply(flower.getFlowerVaseMaterial()))
                .peek(flower -> System.out.println(flower.getCommonName()))
                .collect(Collectors.toList());

        double totalCost = filteredFlowers.stream()
                .mapToDouble(flower -> {
                    final double waterPricePerCubicMeter = 1.39;
                    final int years = 5;
                    final double cubicMetersPerLiter = 0.001;

                    double annualWaterCost = flower.getWaterConsumptionPerDay() *
                            365 * cubicMetersPerLiter * waterPricePerCubicMeter;

                    return flower.getPrice() + annualWaterCost * years;
                })
                .sum();

        System.out.println("Количество подходящих растений: " + filteredFlowers.size());
        System.out.println("итого общая сумма расходов: $" + String.format("%.2f", totalCost));
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() <= 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println("Фамилия студента - " + student.getSurname()
                        + ". Возраст = " + student.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        Set<String> groupsList = students.stream()
                .map(student -> student.getGroup())
                .collect(Collectors.toSet());
        System.out.println(groupsList);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        Map<String, Double> averageAgeByFaculty = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getFaculty,
                        Collectors.averagingInt(Student::getAge)));
        averageAgeByFaculty.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf(
                        "Факультет: %-10s | Средний возраст: %.1f%n",
                        entry.getKey(),
                        entry.getValue()
                ));
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        List<Integer> passedStudentsIdList = examinations.stream()
                .filter(examination -> examination.getExam3() > 4)
                .map(examination -> examination.getStudentId())
                .collect(Collectors.toList());
        Map<String, List<Student>> passedStudentsByGroupMap = students.stream()
                .filter(student -> passedStudentsIdList.contains(student.getId()))
                .collect(Collectors.groupingBy(
                        student -> student.getGroup(),
                        Collectors.toList()));
        System.out.println("Сдавшие экзамены студенты группы P-1: " + passedStudentsByGroupMap.get("P-1"));
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        List<StudentFirstExamPerformance> studentFirstExamPerformanceList = examinations.stream()
                .map(examination -> {
                    Optional<Student> matchingStudent = students.stream()
                            .filter(student -> student.getId() == examination.getStudentId())
                            .findFirst();

                    if (matchingStudent.isPresent()) {
                        Student student = matchingStudent.get();
                        return new StudentFirstExamPerformance(
                                student.getId(),
                                examination.getExam1(),
                                student.getFaculty()
                        );
                    }
                    return null;
                })
                .toList();

        Map<String, Double> averagesByFaculty = studentFirstExamPerformanceList.stream()
                .collect(Collectors.groupingBy(
                        StudentFirstExamPerformance::getFaculty,
                        Collectors.averagingDouble(StudentFirstExamPerformance::getFirstExamMark)
                ));

        List<FacultyExamStats> sortedFacultyExamStats = averagesByFaculty.entrySet().stream()
                .map(entry -> new FacultyExamStats(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(FacultyExamStats::getAvgScore).reversed())
                .collect(Collectors.toList());

        sortedFacultyExamStats.forEach(stats -> System.out.printf("Факультет: %-10s | Средний балл за первый экзамен: %.1f%n", stats.getFaculty(), stats.getAvgScore()));
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    //______________________________________________________________________//
    private static int carGroupSeparator(Car car){
        if ("Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor())) {
            return 1;
        } else if ((car.getMass() <= 1500 && Arrays.asList("BMW", "Lexus", "Chrysler", "Toyota").contains(car.getCarMake()))) {
            return 2;
        } else if ((car.getMass() > 4000 && "Black".equals(car.getColor())) || "GMC".equals(car.getCarMake()) || "Dodge".equals(car.getCarMake())) {
            return 3;
        } else if (car.getReleaseYear() <= 1982 || Arrays.asList("Civic", "Cherokee").contains(car.getCarModel())) {
            return 4;
        } else if (!Arrays.asList("Yellow", "Red", "Green", "Blue").contains(car.getColor()) || car.getPrice() >= 40000) {
            return 5;
        } else if (car.getVin().contains("59")) {
            return 6;
        }
        return 0;
    }
}
