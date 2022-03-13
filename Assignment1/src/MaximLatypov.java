import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class MaximLatypov {

    public static void main(String[] args) {

        String input = "";
        String scenario = "";

        try{
            FileReader fileReader = new FileReader("C:\\Users\\Max\\OneDrive\\Документы\\GitHub\\IntroToAI\\Assignment1\\src\\input.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            input = reader.readLine();
            scenario = reader.readLine();

            String illegalLine = reader.readLine();
            if (illegalLine != null) {
                System.out.println("Error occurred, invalid input: illegal number of input strings");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        findScenarioError(scenario);
        findInputValueError(input);

        Actor Harry = new Actor(Character.getNumericValue(input.charAt(1)), Character.getNumericValue(input.charAt(3)));
        Object Filch = new Object(Character.getNumericValue(input.charAt(7)), Character.getNumericValue(input.charAt(9)));
        Object Cat = new Object(Character.getNumericValue(input.charAt(13)), Character.getNumericValue(input.charAt(15)));
        Object Book = new Object(Character.getNumericValue(input.charAt(19)), Character.getNumericValue(input.charAt(21)));
        Object Cloak = new Object(Character.getNumericValue(input.charAt(25)), Character.getNumericValue(input.charAt(27)));
        Object Exit = new Object(Character.getNumericValue(input.charAt(31)), Character.getNumericValue(input.charAt(33)));

        findLogicError(Harry, Filch, Cat, Book, Cloak, Exit);
        Actor.followAStar(Harry,Filch,Cat,Book,Cloak,Exit);
//        Actor.aStar(Harry, Cloak, Exit);
        System.out.println("-> This is the place where output ends");
    }

    public static void findScenarioError(String scenario){
        if(Integer.parseInt(scenario) != 1 && Integer.parseInt(scenario) != 2){
            System.out.println("Error occurred, invalid input: input scenario out of legal range");
            System.exit(0);
        }
    }

    public static void findInputValueError(String input) {

        for (int i = 0; i < input.length(); ++i){
            if (((input.charAt(i) > 56) || (input.charAt(i) < 48))
                    && ((input.charAt(i) != '[') && (input.charAt(i) != ']') && (input.charAt(i) != ',') && (input.charAt(i) != ' '))){
                System.out.println("Error occurred, invalid input: input value out of legal range");
                System.exit(0);
            }
        }

        for (int i = 1, j = 3; i < 32; i = i + 6, j = i + 2){
            if ((input.charAt(i) < '0') || (input.charAt(i) > '8')) {
                System.out.println("Error occurred, invalid input: input value is illegal");
                System.exit(0);
            }
            if ((input.charAt(j) < '0') || (input.charAt(j) > '8')) {
                System.out.println("Error occurred, invalid input: input value is illegal");
                System.exit(0);
            }
        }

        if (input.length() != 35){
            System.out.println("Error occurred, invalid input: illegal number of input values");
            System.exit(0);
        }
    }

    public static void findLogicError(Actor Harry, Object Filch, Object Cat, Object Book, Object Cloak, Object Exit){

        if (Math.sqrt(Math.pow(Harry.getX() - Filch.getX(), 2) + Math.pow(Harry.getY() - Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Harry is already in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow(Book.getX() - Filch.getX(), 2) + Math.pow(Book.getY() - Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Book is in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow(Cloak.getX() - Filch.getX(), 2) + Math.pow(Cloak.getY() - Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Cloak is in inspector's zone");
            System.exit(0);
        }
        if (Exit.getX() == Filch.getX() && Exit.getY() == Filch.getY()){
            System.out.println("Error occurred, invalid input: Exit is in inspector's cell");
            System.exit(0);
        }

        if (Math.sqrt(Math.pow(Harry.getX() - Cat.getX(), 2) + Math.pow(Harry.getY() - Cat.getY(), 2)) < 2){
            System.out.println("Error occurred, invalid input: Harry is already in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow(Book.getX() - Cat.getX(), 2) + Math.pow(Book.getY() - Cat.getY(), 2)) < 2){
            System.out.println("Error occurred, invalid input: Book is in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow(Cloak.getX() - Cat.getX(), 2) + Math.pow(Cloak.getY() - Cat.getY(), 2)) < 2){
            System.out.println("Error occurred, invalid input: Cloak is in inspector's zone");
            System.exit(0);
        }
        if (Exit.getX() == Cat.getX() && Exit.getY() == Cat.getY()){
            System.out.println("Error occurred, invalid input: Exit is in inspector's cell");
            System.exit(0);
        }

        if ((Exit.getX() == Book.getX()) && (Exit.getY() == Book.getY())){
            System.out.println("Error occurred, invalid input: Exit and Book can not be on the same cell");
            System.exit(0);
        }
    }
}

class Object{

    private int x;
    private int y;

    public Object(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class Actor extends Object{

    private boolean haveBook;
    private boolean haveCloak;
    public static int[][] map = new int[9][9];

    public boolean isHaveBook() {
        return haveBook;
    }

    public boolean isHaveCloak() {
        return haveCloak;
    }

    public void setHaveBook(boolean haveBook) {
        this.haveBook = haveBook;
    }

    public void setHaveCloak(boolean haveCloak) {
        this.haveCloak = haveCloak;
    }


    public Actor(int x, int y) {
        super(x, y);
        haveBook = false;
        haveCloak = false;
        for (int[] raw: map){
            Arrays.fill(raw, 0);
        }
    }

    public static boolean isInBookCell(int x, int y, Actor Harry, Object Book){//Might need to change

        if (Harry.isHaveBook()){
            return false;
        }
        if (x == Book.getX() && y == Book.getY()){
            Harry.setHaveBook(true);
            return true;
        }
        return false;
    }

    public static boolean isInExitCell(int x, int y, Object Exit){
        return x == Exit.getX() && y == Exit.getY();
    }

    public static void checkArticle(Actor Harry, Object Article){
        if (Harry.getX() == Article.getX() && Harry.getY() == Article.getY()) {
            Harry.setHaveCloak(true);
        }
    }

    public static boolean isInFilchZone(Actor Harry, Object Filch) {
        for (int i = Harry.getX() - 1; i < Harry.getX() + 2; ++i) {
            for (int j = Harry.getX() - 1; j < Harry.getX() + 2; ++j) {
                if (!Harry.isHaveCloak()) {
                   if (Math.sqrt(Math.pow(Harry.getX() - Filch.getX(), 2) + Math.pow(Harry.getY() - Filch.getY(), 2)) < 3){
                       map[i][j] = -1;
                       return true;
                   }
                } else {
                    if (Harry.getX() == Filch.getX() && Harry.getY() == Filch.getY()){
                        map[i][j] = -1;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isInCatZone(Actor Harry, Object Cat){
        for (int i = Harry.getX() - 1; i < Harry.getX() + 2; ++i){
            for (int j = Harry.getX() - 1; j < Harry.getX() + 2; ++j){
                if (!Harry.isHaveCloak()) {
                    if (Math.sqrt(Math.pow(i - Cat.getX(), 2) + Math.pow(j - Cat.getY(), 2)) < 2){
                        map[i][j] = -1;
                        return true;
                    }
                } else{
                    if (i == Cat.getX() && j == Cat.getY()){
                        map[i][j] = -1;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isInLegalZone(int x, int y){
        return x >= 0 && x < 9 && y < 9 && y >= 0;
    }

    public static double H(int x, int y, Object Destination){
        return Math.sqrt(Math.pow(x - Destination.getX(), 2) + Math.pow(y - Destination.getY(),2));
    }

    public static ArrayList<Object> aStar(Object Start, Object Finish) {
        Object Current;
        PriorityQueue<Map.Entry<Object, Double>> priorityQueue = new PriorityQueue<>(Map.Entry.comparingByValue());
        priorityQueue.add(new AbstractMap.SimpleEntry<>(Start, 0.0));
        int[][] gScore = new int[9][9];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                gScore[i][j] = 100;
            }
        }
        gScore[Start.getX()][Start.getY()] = 0;
        Object[][] cameFrom = new Object[9][9];
        ArrayList<Object> path = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            Current = priorityQueue.poll().getKey();
            if (Current.getX() == Finish.getX() && Current.getY() == Finish.getY()) {
                break;
            }
            for (int i = Current.getX() - 1; i < Current.getX() + 2; ++i) {
                for (int j = Current.getY() - 1; j < Current.getY() + 2; ++j) {
                    if (!isInLegalZone(i, j)) {
                        continue;
                    }
                    if (map[i][j] == -1){
                        continue;
                    }
                    int newGScore = gScore[Current.getX()][Current.getY()] + 1;
                    if (gScore[i][j] <= newGScore){
                        continue;
                    }
                    gScore[i][j] = newGScore;
                    cameFrom[i][j] = Current;
                    priorityQueue.add(new AbstractMap.SimpleEntry<>(new Object(i, j), gScore[i][j] + H(i, j, Finish)));
                }
            }
        }
        if (cameFrom[Finish.getX()][Finish.getY()] == null){
            return path;
        }
        Object Movement = Finish;
        while (Movement != null && Movement.getX() != Start.getX() && Movement.getY() != Start.getY()) {
            path.add(Movement);
            Movement = cameFrom[Movement.getX()][Movement.getY()];
        }
        Collections.reverse(path);
        return path;
    }

    public static void followAStar(Actor Harry, Object Filch, Object Cat, Object Book, Object Cloak, Object Exit) {

        ArrayList<Object> cellsToMove = aStar(Harry, Exit);

        while (Harry.getX() != Exit.getX() || Harry.getY() != Exit.getY()) {
            if (isInCatZone(Harry, Cat) || isInFilchZone(Harry, Filch)) {
               cellsToMove = aStar(Harry, Exit);
            }
            Harry.setX(cellsToMove.get(0).getX());
            Harry.setY(cellsToMove.get(0).getY());
            cellsToMove.remove(cellsToMove.get(0));
            System.out.println(Harry.getX());
            System.out.println(Harry.getY());
            checkArticle(Harry, Book);
            checkArticle(Harry, Cloak);
        }
    }
}