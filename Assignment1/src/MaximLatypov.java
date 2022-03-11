import java.io.*;
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
        Actor.followAStar(Harry,Filch,Cat,Cloak,Exit);
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
    private int length;
    private String path;

    public boolean isHaveBook() {
        return haveBook;
    }

    public boolean isHaveCloak() {
        return haveCloak;
    }

    public int getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }

    public void setHaveBook(boolean haveBook) {
        this.haveBook = haveBook;
    }

    public void setHaveCloak(boolean haveCloak) {
        this.haveCloak = haveCloak;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Actor(int x, int y) {
        super(x, y);
        haveBook = false;
        haveCloak = false;
        length = 0;
        path = "";
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

    public static void checkCloak(int x, int y, Actor Harry, Object Cloak){

        if (x == Cloak.getX() && y == Cloak.getY()) {
            Harry.setHaveCloak(true);
        }
    }

    public static boolean isInFilchZone(int x, int y, Actor Harry, Object Filch) {

        if (!Harry.isHaveCloak()) {
            return Math.sqrt(Math.pow(x - Filch.getX(), 2) + Math.pow(y - Filch.getY(), 2)) < 3;
        } else{
            return x == Filch.getX() && y == Filch.getY();
        }
    }

    public static boolean isInCatZone(int x, int y, Actor Harry, Object Cat) {

        if (!Harry.isHaveCloak()) {
            return Math.sqrt(Math.pow(x - Cat.getX(), 2) + Math.pow(y - Cat.getY(), 2)) < 2;
        } else{
            return x == Cat.getX() && y == Cat.getY();
        }
    }

    public static boolean isInLegalZone(int x, int y){

        return x >= 0 && x < 9 && y < 9 && y >= 0;
    }

    public static double G(int x, int y, Actor Harry){

        return Math.max(Math.abs(x - Harry.getX()), Math.abs(y - Harry.getY()));
    }

    public static double H(int x, int y, Object Destination){

        return Math.sqrt(Math.pow(x - Destination.getX(), 2) + Math.pow(y - Destination.getY(),2));
    }

    public static ArrayList<Object> aStar(Actor Harry, Object Filch, Object Cat, Object Cloak, Object Exit){

        ArrayList<Object> visitedCells = new ArrayList<>();
        ArrayList<Object> uniqueCells = new ArrayList<>();
        PriorityQueue<Map.Entry<Object,Double>> priorityQueue = new PriorityQueue<>(Map.Entry.comparingByValue());
        Object Current = new Object(Harry.getX(), Harry.getY());
        visitedCells.add(Current);
        uniqueCells.add(Current);
        try {
            while (Current != Exit) {
                for (int i = Current.getX() - 1; i < Current.getX() + 2; ++i) {
                    for (int j = Current.getY() - 1; j < Current.getY() + 2; ++j) {
                        if (!isInLegalZone(i, j) || isInCatZone(i, j, Harry, Cat) || isInFilchZone(i, j, Harry, Filch) || i == Current.getX() && j == Current.getY()) {
                            continue;
                        }
                        boolean visited = false;
                        for (Object uniqueCell : uniqueCells) {
                            if (i == uniqueCell.getX() && j == uniqueCell.getY()) {
                                visited = true;
                                break;
                            }
                        }
                        if (visited) continue;

                        checkCloak(i, j, Harry, Cloak);
                        Object Cell = new Object(i, j);
                        uniqueCells.add(Cell);
                        priorityQueue.add(new AbstractMap.SimpleEntry<>(Cell, G(i, j, Harry) + H(i, j, Exit)));
                    }
                }
                if (Math.abs(Current.getX() - priorityQueue.element().getKey().getX()) >= 2 || Math.abs(Current.getY() - priorityQueue.element().getKey().getY()) >= 2) {
                    priorityQueue.remove(priorityQueue.element());
                    continue;
                }
                Current = priorityQueue.poll().getKey();
                if (isInExitCell(Current.getX(), Current.getY(), Exit)) {
                    visitedCells.add(Exit);
                    return visitedCells;
                }
                visitedCells.add(Current);
            }
        } catch (NoSuchElementException e){
            System.out.println("Impossible to reach exit");
            System.exit(0);
        }
        return visitedCells;
    }
    public static void followAStar(Actor Harry, Object Filch, Object Cat, Object Cloak, Object Exit){
        ArrayList<Object> cellsToMove = aStar(Harry, Filch, Cat, Cloak, Exit);

        for (Object object : cellsToMove) {
            System.out.println("[" + String.valueOf(object.getX()) + ", " + String.valueOf(object.getY()) + "]");
        }
    }
}