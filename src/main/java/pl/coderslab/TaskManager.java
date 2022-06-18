package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {
    static final String PATH = "tasks.csv";
    static File file = new File(PATH);
    static int arrayIndexCounter = 0;
    static int taskCounter = 0;
    static String[][] taskArray = null;
    static boolean deleter = false;


    static void fileAccess(Scanner scannerfile) {


        //   System.out.println("Access granted");
          /*  while (scanner1.hasNextLine()) {
                System.out.println(scanner1.nextLine());
            }*/

//????????????????????????????????????
    }

    static void checkIfEmpty(Scanner scannerIn) {
        if (taskCounter == 0) {
            System.out.println(ConsoleColors.RED + "I Dont have any task. File is empty.");
            arrayIndexCounter = 0;
            menuBack(scannerIn);
        }
    }

    static void copyDataToArray(Scanner scannerIn) {


        try (Scanner scanner1 = new Scanner(file); Scanner scanner2 = new Scanner(file)) {
            // liczenie wierszy i kolumn
            while (scanner1.hasNextLine()) {
                scanner1.nextLine();
                //System.out.println(counterArray + "   " + scanner1.nextLine());
                taskCounter++;


            }
            checkIfEmpty(scannerIn);
            arrayIndexCounter = taskCounter - 1;
// tablica z danymi z pliku
            //  String[][] taskArray =new String[counterArray][3];
//            taskArray = new String[counterArray][3]; // zamiast 3, licba pol w wierszu
//            int lanesCounter = 0;
            // dzielenie linii na komorki i dodawanie do poszczegolnych komorek tablicy 2wymiarowej
            int columnsCounter = 0;
            String[] lanes;
//taskArray=new String[counterArray][]
            while (scanner2.hasNextLine()) {
//STRINGBUILDER

                for (int j = 0; j < taskCounter; j++) {
                    lanes = scanner2.nextLine().split(",");
                    if (j == 0) {
                        columnsCounter = lanes.length; // ilosc kolumn = drugi wymiar tablicy
                        taskArray = new String[taskCounter][lanes.length];
                    }


                    // System.out.println(lanes[i]);  linie podzielone na komorki
                    //      tableString=tableString.concat(String.valueOf(taskArray[lanesCounter][i]).concat(","));
                    // wyswietlanie komorek
                    //               System.out.println(taskArray[j][i]);
                    System.arraycopy(lanes, 0, taskArray[j], 0, columnsCounter);
                    //  System.out.println(Arrays.toString(taskArray[j]));


                }
                //         System.out.println(Arrays.deepToString(taskArray));
                //   System.out.println(Arrays.deepToString(taskArray));


//                System.out.println(Arrays.toString(taskArray[1]));
//                System.out.println(Arrays.toString(taskArray[2]));


            }
            //  String tab2[][]=Arrays.copyOf(taskArray,taskArray.length+1);

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        }


    }


    static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Welcome in Task Manager. What action do You want ?" + ConsoleColors.RESET + "\n");

        System.out.println("1. Task list:");
        System.out.println("2. Add task:");
        System.out.println("3. Delete task:");
        System.out.println("4. Close app:");
    }

    static void deleteTask(Scanner scannerIn) throws IndexOutOfBoundsException {
        checkIfEmpty(scannerIn);

        int taskDelete;

        do {
            deleter = true; // lista zadan bez menuBack
            taskList(scannerIn);
            deleter = false;
            System.out.println(ConsoleColors.RED + "\n" + "Which task want You delete? If You want go back use 0.");
            System.out.println(ConsoleColors.RESET);
            taskDelete = validation(new Scanner(System.in));

            if (taskDelete < 0 || taskDelete > taskCounter) {
                System.out.println(ConsoleColors.RED + "I don't have this task.");
                System.out.println(ConsoleColors.RESET);
            }


        } while (!((taskDelete >= 0) && (taskDelete <= taskCounter)));

        if (taskDelete == 0) {
            menuBack(scannerIn);

        } else {
            String[] formatedTasks;
            //  System.out.println(Arrays.deepToString(taskArray));  wyswietlenie tablicy przed usunieciem
            taskArray = ArrayUtils.remove(taskArray, taskDelete - 1);
            //  System.out.println(Arrays.deepToString(taskArray));  wyswietlenie tablicy po usunieciu

            taskCounter--;   // zmniejszenie licznika zadan
            arrayIndexCounter--; // zmniejszenie licznika tablicy

            while (taskCounter > 0) {

                System.out.println(ConsoleColors.RED + "Task " + taskDelete + " deleted.");
                System.out.print(ConsoleColors.RESET);
                String squareBracket = "\\[";
                formatedTasks = Arrays.deepToString(taskArray).split("], " + squareBracket);
                formatedTasks[0] = formatedTasks[0].substring(2);
                formatedTasks[formatedTasks.length - 1] = formatedTasks[formatedTasks.length - 1].substring(0, formatedTasks[formatedTasks.length - 1].length() - 2);

                try (FileWriter fileWriter = new FileWriter(PATH)) {

                    for (int i = 0; i < formatedTasks.length; i++) {
                        //System.out.println(formatedTasks[i]=formatedTasks[i].replaceAll(" {2}"," "));
                        formatedTasks[i] = formatedTasks[i].replaceAll(" {2}", " ");
                        fileWriter.append(formatedTasks[i]);
                        if (i < formatedTasks.length - 1) {
                            fileWriter.append(("\n"));
                        }
                    }


                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                menuBack(scannerIn);
            }
            if (taskCounter == 0) {
                System.out.println(ConsoleColors.RED + "I dont have any tasks.");
                System.out.print(ConsoleColors.RESET);

                try (FileWriter fileWriter = new FileWriter(PATH)) {
                    fileWriter.append("");
                    // pusty plik
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            menuBack(scannerIn);
        }
    }


    static int validation(Scanner scan) throws InputMismatchException {
        String input2;

        while (!scan.hasNextInt()) {
            input2 = scan.next();
            System.out.println(input2 + ConsoleColors.RED + " is not a valid number.");
            System.out.print(ConsoleColors.RESET);
            // scan.next();
        }
        int input = scan.nextInt();


        return input;
    }

    static void getInstruction(@NotNull Scanner scannerIn) {

        String wybor;
        showMenu();
        do {

            wybor = scannerIn.nextLine();
            //
            System.out.println();
            switch (wybor) {
                case "1" -> taskList(scannerIn);
                case "2" -> addTasks(scannerIn);
                case "3" -> deleteTask(scannerIn);
                case "4" -> exitProgram();
                default -> {
                    System.out.println(ConsoleColors.RED + "Invalid input data. Please choose from [1-4]");
                    System.out.print(ConsoleColors.RESET);
                }
            }

        }


        while (!wybor.equals("4"));
    }

    static void taskList(Scanner scannerIn) {
        checkIfEmpty(scannerIn);
        System.out.println(ConsoleColors.BLUE + "All tasks:" + "\n");
        System.out.print(ConsoleColors.RESET);
        try (Scanner scanner1 = new Scanner(file)) {
            int counter = 1;
            while (scanner1.hasNextLine()) {
                System.out.println(counter + " : " + scanner1.nextLine());
                counter++;
            }
            if (!deleter) {
                menuBack(scannerIn);
            }


        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();

        }


    }


    static void addTasks(Scanner scannerIn) {

        try ( Scanner scanner2 = new Scanner(System.in)) {
            String newTask = "";
            if (taskCounter > 0) {

                taskArray = Arrays.copyOf(taskArray, taskArray.length + 1);
                taskArray[taskArray.length - 1] = new String[]{"", "", ""};
                arrayIndexCounter++;
            } else {

                taskArray = new String[1][3];

            }
            System.out.print(ConsoleColors.BLUE);
            System.out.println("Please add task description: "); // taskCounter jako nowy element tablicy ??
            System.out.print(ConsoleColors.RESET);
            taskArray[arrayIndexCounter][0] = scanner2.nextLine();
            System.out.println(ConsoleColors.BLUE);
            System.out.println("Please add task due date:");
            System.out.print(ConsoleColors.RESET);

            taskArray[arrayIndexCounter][1] = scanner2.nextLine();
            System.out.println(ConsoleColors.BLUE);
            System.out.println(("Is Your task is important (Y/N):"));
            System.out.print(ConsoleColors.RESET);
            taskArray[arrayIndexCounter][2] = scanner2.nextLine();
            System.out.println(ConsoleColors.BLUE);
            System.out.println(("Task added correctly."));
            //uciecie nawiasow kwadratowych przy dopisaniu do pliku
            newTask = newTask.concat(Arrays.deepToString(taskArray[taskArray.length - 1]));
            newTask = newTask.substring(1, newTask.length() - 1);

            //  System.out.println(newTask);

            try (FileWriter fileWriter = new FileWriter(PATH, true)) {
                if (taskCounter == 0) {
                    fileWriter.append(newTask);
                } else {
                    fileWriter.append("\n").append(newTask)
                }
                //     arrayIndexCounter++;
                taskCounter++; // zwiekszenie ilosci zadan podczas trwania programu

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
//superCounter++;
            //    System.out.println(Arrays.deepToString(taskArray));
            menuBack(scannerIn);

        }
    }

    private static void menuBack(@NotNull Scanner scannerIn) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println(ConsoleColors.PURPLE + "\n" + "Want You go to MAIN MENU or QUIT ? [M/Q]");
        System.out.print(ConsoleColors.RESET);

        String wybor;
        do {
            wybor = scanner2.next();
            System.out.println();
            switch (wybor.toUpperCase()) {
                case "M" -> getInstruction(scannerIn);
                case "Q" -> exitProgram();
                default -> {
                    System.out.println(ConsoleColors.RED + "Invalid input data. Please choose [M] or [Q]");
                    System.out.print(ConsoleColors.RESET);
                }
            }
        } while (!(wybor.equalsIgnoreCase("M")) || !(wybor.equalsIgnoreCase("Q")));
    }

    static void exitProgram() {
        System.out.println(ConsoleColors.RED + "Thanks for using my app.");
        System.out.print(ConsoleColors.RESET);
        System.exit(0);
    }

    public static void main(String[] args) {

        try (Scanner scannerIn = new Scanner(System.in);
             Scanner scannerFile = new Scanner(file)) {

            fileAccess(scannerFile);
            copyDataToArray(scannerIn);
            getInstruction(scannerIn);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


}



