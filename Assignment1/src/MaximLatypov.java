import java.io.*;
import java.util.*;

public class MaximLatypov {

    public static void main(String[] args) {

        String input_1 = "";
        String scenario_1 = "";

        try{
            FileReader fileReader = new FileReader("C:\\Users\\Max\\OneDrive\\Документы\\GitHub\\IntroToAI\\Assignment1\\src\\input.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            input_1 = reader.readLine();
            scenario_1 = reader.readLine();

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

//        Scanner in = new Scanner(System.in);
//        System.out.println("Please enter first string of input:");
//        String input_1 = in.nextLine();
//        System.out.println("Please enter scenario:");
//        int scenario_1 = in.nextInt();

        String input_2 = generateInput();
        int scenario_2 = generateScenario();

        findScenarioError(scenario_1);
        findInputValueError(input_1);

        Actor Harry = new Actor(Character.getNumericValue(input_1.charAt(1)), Character.getNumericValue(input_1.charAt(3)));
        Cell Filch = new Cell(Character.getNumericValue(input_1.charAt(7)), Character.getNumericValue(input_1.charAt(9)));
        Cell Cat = new Cell(Character.getNumericValue(input_1.charAt(13)), Character.getNumericValue(input_1.charAt(15)));
        Cell Book = new Cell(Character.getNumericValue(input_1.charAt(19)), Character.getNumericValue(input_1.charAt(21)));
        Cell Cloak = new Cell(Character.getNumericValue(input_1.charAt(25)), Character.getNumericValue(input_1.charAt(27)));
        Cell Exit = new Cell(Character.getNumericValue(input_1.charAt(31)), Character.getNumericValue(input_1.charAt(33)));

        findLogicError(Harry, Filch, Cat, Book, Cloak, Exit);
//        Actor.followBacktracking(Harry,Filch,Cat,Book,Cloak,Exit,Integer.parseInt(scenario_1));
        Actor.followAStar(Harry,Filch,Cat,Book,Cloak,Exit, Integer.parseInt(scenario_1));
        System.out.println("-> This is the place where output ends");
    }



    public static int generateScenario(){return (int)(1 + Math.random()*2);}

    public static String generateInput(){
        StringBuilder input = new StringBuilder();

        input.append("[").append(0).append(",").append(0).append("] ");
        for (int i = 0; i < 5; ++i){
            input.append("[").append(new Random().nextInt(8)).append(",").append(new Random().nextInt(8)).append("] ");
        }
        return (input.length() == 0) ? null : (input.substring(0, input.length() - 1));
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

    public static void findLogicError(Actor Harry, Cell Filch, Cell Cat, Cell Book, Cell Cloak, Cell Exit){

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

class Cell{

    private int x;
    private int y;

    public Cell(int x, int y){
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

class Actor extends Cell{

    private boolean haveBook;
    private boolean haveCloak;
    public static int[][] map = new int[9][9];
    public static Stack<Cell> stack = new Stack<>();

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

    public static boolean isInLegalZone(int x, int y){
        return x >= 0 && x < 9 && y < 9 && y >= 0;
    }

    public static void checkCloak(Actor Harry, Cell Cloak){
        if (Harry.getX() == Cloak.getX() && Harry.getY() == Cloak.getY()) {
            Harry.setHaveCloak(true);
            for (int i = 0; i < 9; ++i){
                for (int j = 0; j< 9; ++j){
                    if (map[i][j] != 1){
                        map[i][j] = 0;
                    }
                }
            }
        }
    }

    public static void checkBook(Actor Harry, Cell Book){
        if (Harry.getX() == Book.getX() && Harry.getY() == Book.getY()) {
            Harry.setHaveBook(true);
        }
    }


    public static void senseFilchZone(Actor Harry, Cell Filch, int scenario) {
        for (int i = Harry.getX() - scenario; i < Harry.getX() + scenario + 1; ++i) {
            for (int j = Harry.getY() - scenario; j < Harry.getY() + scenario + 1; ++j) {
                if (!isInLegalZone(i,j)) {
                    continue;
                }
                if (i < Math.abs(Harry.getX() - scenario) && j < Math.abs(Harry.getY() - scenario)){
                    continue;
                }
                if (!Harry.isHaveCloak()){
                    if (i == Filch.getX() && j == Filch.getY()){
                        map[i][j] = 1;
                        continue;
                    }
                    if (Math.sqrt(Math.pow(i - Filch.getX(), 2) + Math.pow(j - Filch.getY(), 2)) < 3) {
                        map[i][j] = -1;
                    }
                } else {
                    if (i == Filch.getX() && j == Filch.getY()){
                        map[i][j] = 1;
                    } else if (map[i][j] != 1){
                        map[i][j] = 0;
                    }
                }
            }
        }
    }

    public static boolean isInFilchZone(int x, int y, Actor Harry, Cell Filch){
        if (!Harry.isHaveCloak()) {
            return Math.sqrt(Math.pow(x - Filch.getX(), 2) + Math.pow(y - Filch.getY(), 2)) < 3;
        } else {
            return x == Filch.getX() && y == Filch.getY();
        }
    }

    public static void senseCatZone(Actor Harry, Cell Cat, int scenario){
        for (int i = Harry.getX() - scenario; i < Harry.getX() + scenario + 1; ++i){
            for (int j = Harry.getY() - scenario; j < Harry.getY() + scenario + 1; ++j){
                if (!isInLegalZone(i,j)) {
                    continue;
                }
                if (i < Math.abs(Harry.getX() - scenario) && j < Math.abs(Harry.getY() - scenario)){
                    continue;
                }
                if (!Harry.isHaveCloak()) {
                    if (i == Cat.getX() && j == Cat.getY()){
                        map[i][j] = 1;
                        continue;
                    }
                    if (Math.sqrt(Math.pow(i - Cat.getX(), 2) + Math.pow(j - Cat.getY(), 2)) < 2) {
                        map[i][j] = -1;
                    }
                } else {
                    if (i == Cat.getX() && j == Cat.getY()) {
                        map[i][j] = 1;
                    } else if (map[i][j] != 1){
                        map[i][j] = 0;
                    }
                }
            }
        }
    }

    public static boolean isInCatZone(int x, int y, Actor Harry, Cell Cat){
        if (!Harry.isHaveCloak()){
            return Math.sqrt(Math.pow(x - Cat.getX(), 2) + Math.pow(y - Cat.getY(), 2)) < 2;
        } else {
            return x == Cat.getX() && y == Cat.getY();
        }
    }

    public static double H(int x, int y, Cell Destination){
        return Math.sqrt(Math.pow(x - Destination.getX(), 2) + Math.pow(y - Destination.getY(),2));
    }

    public static ArrayList<Cell> aStar(Actor Start, Cell Finish) {
        Cell Current;
        PriorityQueue<Map.Entry<Cell, Double>> priorityQueue = new PriorityQueue<>(Map.Entry.comparingByValue());
        priorityQueue.add(new AbstractMap.SimpleEntry<>(Start, 0.0));
        int[][] gScore = new int[9][9];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                gScore[i][j] = 100;
            }
        }
        gScore[Start.getX()][Start.getY()] = 0;
        Cell[][] cameFrom = new Cell[9][9];
        ArrayList<Cell> path = new ArrayList<>();
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
                    if (map[i][j] == -1 || map[i][j] == 1){
                        continue;
                    }
                    int newGScore = gScore[Current.getX()][Current.getY()] + 1;
                    if (gScore[i][j] <= newGScore){
                        continue;
                    }
                    gScore[i][j] = newGScore;
                    cameFrom[i][j] = Current;
                    priorityQueue.add(new AbstractMap.SimpleEntry<>(new Cell(i, j), gScore[i][j] + H(i, j, Finish)));
                }
            }
        }
        if (cameFrom[Finish.getX()][Finish.getY()] == null){
            return path;
        }
        Cell Movement = Finish;
        while (Movement != null && (Movement.getX() != Start.getX() || Movement.getY() != Start.getY())) {
            path.add(Movement);
            Movement = cameFrom[Movement.getX()][Movement.getY()];
        }
        Collections.reverse(path);
        return path;
    }

    public static boolean isVisited(int x, int y, ArrayList<Cell> visitedCells){
        for (Cell visitedCell : visitedCells) {
            if (visitedCell.getX() == x && visitedCell.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Cell> backtracking(Actor Harry, ArrayList<Cell> visitedCells){
        ArrayList<Cell> path = new ArrayList<>();
        Actor Current = Harry;
        for (int i = Current.getX() - 1; i < Current.getX() + 2; ++i) {
            for (int j = Current.getY() - 1; j < Current.getY() + 2; ++j) {
                if (!isInLegalZone(i, j)) {
                    continue;
                }
                if (map[i][j] == -1 || map[i][j] == 1) {
                    continue;
                }
                if (isVisited(i, j, visitedCells)) {
                    continue;
                }
                stack.push(new Cell(Current.getX(), Current.getY()));
                path.add(new Cell(i, j));
                return path;
            }
        }
        if (stack.isEmpty()){
            return null;
        }
        path.add(stack.pop());
        return path;
    }

    public static void followBacktracking(Actor Harry, Cell Filch, Cell Cat, Cell Book, Cell Cloak, Cell Exit, int scenario){

        boolean isExitReached = false;
        ArrayList<Cell> visitedCells = new ArrayList<>();
        visitedCells.add(new Cell(0,0));
        ArrayList<Cell> currentPath;

        while (Harry.getX() != Exit.getX() || Harry.getY() != Exit.getY() || !Harry.isHaveBook()) {
            senseCatZone(Harry, Cat, scenario);
            senseFilchZone(Harry, Filch, scenario);
            currentPath = backtracking(Harry, visitedCells);
            Harry.setX(currentPath.get(0).getX());
            Harry.setY(currentPath.get(0).getY());
            System.out.println("[" + Harry.getX() + "," + Harry.getY() + "]");
            if (isInCatZone(Harry.getX(), Harry.getY(), Harry, Cat) || isInFilchZone(Harry.getX(), Harry.getY(),Harry, Filch)){
                System.out.println("Harry has been caught by inspector!!!");
                return;
            }
            if (Harry.getX() == Exit.getX() && Harry.getY() == Exit.getY()){
                isExitReached = true;
            }
            checkBook(Harry, Book);
            checkCloak(Harry, Cloak);
            if (isExitReached && Harry.isHaveBook()){
                currentPath = aStar(Harry, Exit);
                visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
                for (Cell cell : currentPath){
                    Harry.setX(cell.getX());
                    Harry.setY(cell.getY());
                    visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
                    System.out.println("[" + Harry.getX() + "," + Harry.getY() + "]");
                }
//               while (stack.peek().getX() != Exit.getX() || stack.peek().getY() != Exit.getY()){
//                   Cell temp = stack.pop();
//                   Harry.setX(temp.getX());
//                   Harry.setY(temp.getY());
//                   visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
//               }
            } else {
                visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
            }
        }
    }

    public static void followAStar(Actor Harry, Cell Filch, Cell Cat, Cell Book, Cell Cloak, Cell Exit, int scenario) {

        int stepCounter = 0;
        boolean isExitReached = false;
        ArrayList<Cell> currentPath = aStar(Harry, Exit);
        ArrayList<Cell> visitedCells = new ArrayList<>();

        visitedCells.add(new Cell(Harry.getX(), Harry.getY()));

        while (Harry.getX() != Exit.getX() || Harry.getY() != Exit.getY() || !Harry.isHaveBook()) {
            if (!isExitReached) {
                senseCatZone(Harry, Cat, scenario);
                senseFilchZone(Harry, Filch, scenario);
                currentPath = aStar(Harry, Exit);
            }
            if (currentPath.isEmpty()) {
                ArrayList<Cell> shortestPath = null;
                boolean flag =  false;
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        if (map[i][j] == -1 || map[i][j] == 1){
                            continue;
                        }
                        if (isVisited(i, j, visitedCells)) {
                            continue;
                        }
                        currentPath = aStar(Harry, new Cell(i, j));
                        if (currentPath.isEmpty()) {
                            continue;
                        }
                        if (shortestPath == null || shortestPath.size() > currentPath.size()) {
                            shortestPath = currentPath;
                        }
                        if (shortestPath.size() == 1){
                            flag = true;
                            break;
                        }
                    }
                    if (flag){
                        break;
                    }
                }
                if (shortestPath == null) {
                    System.out.println("Impossible to reach exit");
                }
                currentPath = shortestPath;
            }
            if (currentPath == null){
                return;
            }
            Harry.setX(currentPath.get(0).getX());
            Harry.setY(currentPath.get(0).getY());
            if (isInCatZone(Harry.getX(), Harry.getY(),Harry, Cat) || isInFilchZone(Harry.getX(), Harry.getY(),Harry, Filch)){
                System.out.println("Harry has been caught by inspector!!!");
                return;
            }
            stepCounter++;
            if (Harry.getX() == Exit.getX() && Harry.getY() == Exit.getY()){
                isExitReached = true;
            }
            currentPath.remove(0);
            visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
            System.out.println("[" + Harry.getX() + "," + Harry.getY() + "]");
            checkBook(Harry, Book);
            if (Harry.haveBook){
                isExitReached = false;
                currentPath = aStar(Harry, Exit);
            }
            checkCloak(Harry, Cloak);
            senseCatZone(Harry, Cat, scenario);
            senseFilchZone(Harry, Filch, scenario);
        }
        System.out.println(stepCounter);
    }
}