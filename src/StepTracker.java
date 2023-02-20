
import java.util.Random;
import java.util.Scanner;

public class StepTracker {
    private static int targetSteps = 10_000;
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private MonthData[] monthToData = new MonthData[12];

    class MonthData {
        int[] stepsByDays = new int[30];
    }

    public void start() {
        boolean work = true;
        for (int i = 0; i < monthToData.length; i++) {
            monthToData[i] = new MonthData();
        }
        while (work) {
            printMainMenu();
            switch (scanner.nextLine()) {
                case "1" -> enterNumberOfStepsPerDay();
                case "2" -> statistics();
                case "3" -> setTargetSteps();
                case "0" -> work = false;
                case "9" -> secretMenu();
                default -> System.out.println("\033[96mПостарайтесь попасть в клавиши 0-3\033[0m");
            }
        }
        System.out.println("\033[96mДо встречи в эфире.\033[0m");
    }

    private static void printMainMenu() {
        System.out.println("\n" + """
                Главное меню
                1. Ввести количество шагов за определённый день;
                2. Напечатать статистику за определённый месяц;
                3. Изменить цель по количеству шагов в день;
                                
                                
                9. Секретное меню.
                0. Выйти из приложения.""");
        System.out.println();
    }


    private void enterNumberOfStepsPerDay() {
        while (true) {
            System.out.println("За какой месяц будем вносить информацию? (1-12, 0 для выхода)");
            int month = scanner.nextInt();
            if (month >= 1 && month < 13) {
                System.out.println("За какой день будем вносить информацию?(1-30)");
                int day = scanner.nextInt();
                if (day > 0 && day <= 30) {
                    System.out.println("Сколько шагов запишем " + day + "." + month + ".2023г?");
                    int steps = scanner.nextInt();
                    if (steps > 0) {
                        monthToData[month - 1].stepsByDays[day - 1] = steps;
                    } else {
                        System.out.println("Опять ходил задом?");
                    }
                } else {
                    System.out.println("1-30 дни. Другие варианты не в нашей вселенной.");
                }
            } else if (month == 0) {
                return;
            } else {
                System.out.println("Мартобря 86 числа. Попробуйте ввести реальный месяц (1-12)");
            }
        }
    }

    private void statistics() {

        int month;
        while (true) {
            printMenustatistics();
            switch (scanner.nextInt()) {
                case 1:
                    month = selectMonth();
                    for (int i = 0; i < monthToData[month].stepsByDays.length; i++) {
                        System.out.print((i + 1) + " день: " + monthToData[month].stepsByDays[i] + ", ");
                    }
                    System.out.println();
                    break;
                case 2:
                    month = selectMonth();
                    int maxSteps = 0;
                    int iTMP = 0;
                    for (int i = 0; i < monthToData[month].stepsByDays.length; i++) {
                        if (maxSteps < monthToData[month].stepsByDays[i]) {
                            maxSteps = monthToData[month].stepsByDays[i];
                            iTMP = i + 1;
                        }
                    }
                    System.out.println("\033[96mБольшое всего шагов Вы прошли " + iTMP + "." + month + ".2023г. - " + monthToData[month].stepsByDays[iTMP - 1] + "\033[0m");
                    break;
                case 3:
                    month = selectMonth();
                    System.out.println("\033[96mВ среднем Вы проходили " + countSteps(month) / monthToData[month].stepsByDays.length + " шагов в день\033[0m");
                    break;
                case 4:
                    month = selectMonth();
                    System.out.println("\033[96mВ .... месяце (потом поправлю на название месяца) Вы прошли " + Converter.converterDistance(countSteps(month)) / 1000 + " километров или около того\033[0m");
                    break;
                case 5:
                    month = selectMonth();
                    System.out.println("\033[96mВ ..... месяце Вы сожгли " + Converter.converterCalories(countSteps(month)) + " килокалорий. Что явно маловато.\uD83E\uDD14\033[0m");
                    break;
                case 6:
                    month = selectMonth();
                    int countDays = 0;
                    int countDaysMax = 0;
                    for (int i = 0; i < monthToData[month].stepsByDays.length; i++) {
                        if (monthToData[month].stepsByDays[i] > targetSteps) {
                            countDays++;
                            if (countDays > countDaysMax) {
                                countDaysMax = countDays;
                            }
                        }
                    }
                    System.out.println("\033[96mЛучшая серия (количество дней подряд больше целевого количества шагов - " + targetSteps + ") - " + countDaysMax + "\033[0m");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Выберите 0-6");
            }
        }
    }

    private void printMenustatistics() {
        System.out.println("\n" + """
                Вывод статистики
                1. Количество пройденных шагов по дням;
                2. Максимальное количество шагов в месяце;
                3. Среднее количество шагов за месяц;
                4. Пройденная дистанция;
                5. Количество сожжённых килокалорий;
                6. Лучшая серия: максимальное количество подряд идущих дней, в течение которых количество шагов за день было равно или выше целевого;
                0. Выход в предыдущее меню.""");
    }

    private int selectMonth() {
        System.out.println("Выберите месяц для анализа: (1-12)");
        return (scanner.nextInt() - 1);
    }

    private int countSteps(int month) {
        int countSteps = 0;
        for (int i = 0; i < monthToData[month].stepsByDays.length; i++) {
            countSteps += monthToData[month].stepsByDays[i];
        }
        return countSteps;
    }

    private void setTargetSteps() {
        while (true) {
            System.out.println("\n" + """
                    1. Показать текущее целевое количество шагов;
                    2. Сменить целевое количество шагов;
                    0. Выход из меню.""");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("\033[96mСейчас нужно пройти " + targetSteps + " шагов в день.\033[0m");
                    break;
                case 2:
                    System.out.println("Введите новое значение цели шагов в день :\033[0m");
                    int tmp = scanner.nextInt();
                    if (tmp > 0) {
                        targetSteps = tmp;
                    } else {
                        System.out.println("Введены ошибочные данные (ходить задом вредно).\033[0m");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Введите цифры от 0 до 2.");
            }
        }
    }

    private void secretMenu() {
        while (true) {
            System.out.println("\n" + """
                    1. Заполнить целый год случайными данными;
                    2. Заполнить месяц случайными данными;
                    3. Очистить историю ходьбы;
                    0. Выход в обычное меню.""");

            switch (scanner.nextLine()) {
                case "1":
                    fillYearRandomSteps();
                    break;
                case "2":
                    fillMonthRandomSteps();
                    break;
                case "3":
                    clearAllData();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Введите цифру 0-3");
            }
        }
    }


    private void fillYearRandomSteps() {
        for (MonthData monthToDatum : monthToData) {
            for (int j = 0; j < monthToData[0].stepsByDays.length; j++) {
                monthToDatum.stepsByDays[j] = random.nextInt(targetSteps / 2, (int) (targetSteps * 1.5));
            }
            System.out.print("##");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void fillMonthRandomSteps() {
        System.out.println("Какой месяц будем заполнять? (1-12)");
        int num = Integer.parseInt(scanner.nextLine());
        if (num < 1 || num > 12) {
            System.out.println("Нет такого месяца, извините.");
        } else {
            for (int i = 0; i < monthToData[num - 1].stepsByDays.length; i++) {
                monthToData[num - 1].stepsByDays[i] = random.nextInt(5000, 12000);
            }
        }
    }

    private void clearAllData() {
        System.out.println("\033[95mСейчас все данные о шагах будут обнулены. " +
                "Если уверены - нажмите цифру 1\033[0m");
        if (Integer.parseInt(scanner.nextLine()) == 1) {
            for (MonthData monthToDatum : monthToData) {
                for (int j = 0; j < monthToData[0].stepsByDays.length; j++) {
                    monthToDatum.stepsByDays[j] = 0;
                }
            }
        } else {
            return;
        }
        System.out.println("\033[95mЧисто, начинай сначала.\033[0m");
    }
}
