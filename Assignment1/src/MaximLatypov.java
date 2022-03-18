import java.util.*;

public class MaximLatypov {

    public static void main(String[] args) {
        Statistic statistic = new Statistic();
//        readFromConsole();
        generateTest(statistic, 10000);
        System.out.println("-> This is the place where output ends");
        statistic.printStatistic();
    }

    /**
     * This function generates random integer value between 1 and
     *
     * @return int random value between 1 and 2
     */
    public static int generateScenario(){return (int)(1 + Math.random()*2);}


    /**
     * This function generates string with random coordinates of necessary objects
     * between 0 and 8 which can be used in the future
     *
     * @return String which is actually map
     */
    public static String generateInput(){
        StringBuilder input = new StringBuilder();

        input.append("[").append(0).append(",").append(0).append("] ");
        for (int i = 0; i < 5; ++i){
            input.append("[").append(new Random().nextInt(8)).append(",").append(new Random().nextInt(8)).append("] ");
        }
        return (input.length() == 0) ? null : (input.substring(0, input.length() - 1));
    }

    /**
     * This function will give a true if scenario is out of legal range,
     *  otherwise it will give false
     *
     * @return boolean value true if it meets input error
     */
    public static boolean findScenarioError(String scenario){
        if(Integer.parseInt(scenario) != 1 && Integer.parseInt(scenario) != 2){
            System.out.println("Error occurred, invalid input: input scenario out of legal range");
            return true;
        }
        return false;
    }

    /**
     * This function will give a true if input chars are incorrect,
     * otherwise it will give false
     *
     * @return boolean value true if it meets input error
     */
    public static boolean findInputValueError(String input) {

        for (int i = 0; i < input.length(); ++i){
            if (((input.charAt(i) > 56) || (input.charAt(i) < 48))
                    && ((input.charAt(i) != '[') && (input.charAt(i) != ']') && (input.charAt(i) != ',') && (input.charAt(i) != ' '))){
                System.out.println("Error occurred, invalid input: input value out of legal range");
                return true;
            }
            return false;
        }

        for (int i = 1, j = 3; i < 32; i = i + 6, j = i + 2){
            if ((input.charAt(i) < '0') || (input.charAt(i) > '8')) {
                System.out.println("Error occurred, invalid input: input value is illegal");
                return true;
            }
            if ((input.charAt(j) < '0') || (input.charAt(j) > '8')) {
                System.out.println("Error occurred, invalid input: input value is illegal");
                return true;
            }
        }

        if (input.length() != 35){
            System.out.println("Error occurred, invalid input: illegal number of input values");
            return true;
        }
        return false;
    }

    /**
     * This function will give a true if it finds out that some objects are on the wrong positions,
     * otherwise it will give false
     *
     * @return boolean value if it meets logic error
     */
    public static boolean findLogicError(Actor Harry, Cell Filch, Cell Cat, Cell Book, Cell Cloak, Cell Exit){

        if (Math.sqrt(Math.pow(Harry.getX() - Filch.getX(), 2) + Math.pow(Harry.getY() - Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Harry is already in inspector's zone");
            return true;
        }
        if (Math.sqrt(Math.pow(Book.getX() - Filch.getX(), 2) + Math.pow(Book.getY() - Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Book is in inspector's zone");
            return true;
        }
        if (Math.sqrt(Math.pow(Cloak.getX() - Filch.getX(), 2) + Math.pow(Cloak.getY() - Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Cloak is in inspector's zone");
            return true;
        }
        if (Exit.getX() == Filch.getX() && Exit.getY() == Filch.getY()){
            System.out.println("Error occurred, invalid input: Exit is in inspector's cell");
            return true;
        }

        if (Math.sqrt(Math.pow(Harry.getX() - Cat.getX(), 2) + Math.pow(Harry.getY() - Cat.getY(), 2)) < 2){
            System.out.println("Error occurred, invalid input: Harry is already in inspector's zone");
            return true;
        }
        if (Math.sqrt(Math.pow(Book.getX() - Cat.getX(), 2) + Math.pow(Book.getY() - Cat.getY(), 2)) < 2){
            System.out.println("Error occurred, invalid input: Book is in inspector's zone");
            return true;
        }
        if (Math.sqrt(Math.pow(Cloak.getX() - Cat.getX(), 2) + Math.pow(Cloak.getY() - Cat.getY(), 2)) < 2){
            System.out.println("Error occurred, invalid input: Cloak is in inspector's zone");
            return true;
        }
        if (Exit.getX() == Cat.getX() && Exit.getY() == Cat.getY()){
            System.out.println("Error occurred, invalid input: Exit is in inspector's cell");
            return true;
        }

        if ((Exit.getX() == Book.getX()) && (Exit.getY() == Book.getY())){
            System.out.println("Error occurred, invalid input: Exit and Book can not be on the same cell");
            return true;
        }
        return false;
    }


    /**
     * This function generates tests which number can be chosen by user
     * with multiple locations of objects
     */
    public static void generateTest(Statistic statistic, int testsNumber){
        for (int i = 0; i < testsNumber; ++i){
            String input = generateInput();
            int scenario = generateScenario();

            if (findScenarioError(String.valueOf(scenario))){
                statistic.setFailedTestsNumber(statistic.getFailedTestsNumber() + 1);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                continue;
            }
            if (findInputValueError(input)){
                statistic.setFailedTestsNumber(statistic.getFailedTestsNumber() + 1);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                continue;
            }

            Actor Harry = new Actor(Character.getNumericValue(input.charAt(1)), Character.getNumericValue(input.charAt(3)));
            Actor HarryCopy = new Actor(Harry.getX(), Harry.getY());
            Cell Filch = new Cell(Character.getNumericValue(input.charAt(7)), Character.getNumericValue(input.charAt(9)));
            Cell Cat = new Cell(Character.getNumericValue(input.charAt(13)), Character.getNumericValue(input.charAt(15)));
            Cell Book = new Cell(Character.getNumericValue(input.charAt(19)), Character.getNumericValue(input.charAt(21)));
            Cell Cloak = new Cell(Character.getNumericValue(input.charAt(25)), Character.getNumericValue(input.charAt(27)));
            Cell Exit = new Cell(Character.getNumericValue(input.charAt(31)), Character.getNumericValue(input.charAt(33)));

            if (findLogicError(Harry, Filch, Cat, Book, Cloak, Exit)){
                statistic.setFailedTestsNumber(statistic.getFailedTestsNumber() + 1);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                continue;
            }
            statistic.setPassedTestsNumber(statistic.getPassedTestsNumber() + 1);

            Actor.followBacktracking(Harry, Filch, Cat, Book, Cloak, Exit, scenario);
            if (Objects.equals(Harry.getOutcome(), "Win")){
                statistic.setWinOutcomeNumBacktrack(statistic.getWinOutcomeNumBacktrack() + 1);

                statistic.setAvStepNumBacktrack(statistic.getAvStepNumBacktrack() + Harry.getStep());
                statistic.setAvTimeBacktrack(statistic.getAvTimeBacktrack() + Harry.getTimeTaken());
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


            Actor.followAStar(HarryCopy, Filch, Cat, Book, Cloak, Exit, scenario);
            if (Objects.equals(HarryCopy.getOutcome(), "Win")){
                statistic.setWinOutcomeNumAStar(statistic.getWinOutcomeNumAStar() + 1);

                statistic.setAvStepNumAStar(statistic.getAvStepNumAStar() + HarryCopy.getStep());
                statistic.setAvTimeAStar(statistic.getAvTimeAStar() + HarryCopy.getTimeTaken());
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        statistic.setProbWinBacktrack((double) statistic.getWinOutcomeNumBacktrack() / statistic.getPassedTestsNumber());
        statistic.setProbWinAStar((double) statistic.getWinOutcomeNumAStar() / statistic.getPassedTestsNumber());

        statistic.setAvStepNumBacktrack(statistic.getAvStepNumBacktrack() / statistic.getWinOutcomeNumBacktrack());
        statistic.setAvTimeBacktrack(statistic.getAvTimeBacktrack() / statistic.getWinOutcomeNumBacktrack());

        statistic.setAvStepNumAStar(statistic.getAvStepNumAStar() / statistic.getWinOutcomeNumAStar());
        statistic.setAvTimeAStar(statistic.getAvTimeAStar() / statistic.getWinOutcomeNumAStar());
    }


    /**
     * This function allow user manually add from console input and compare algorithms on his/her own
     */
    public static void readFromConsole(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter input:");
        String input = in.nextLine();
        int scenario = in.nextInt();

        if (findScenarioError(String.valueOf(scenario))){
            return;
        }
        if (findInputValueError(input)){
            return;
        }

        Actor Harry = new Actor(Character.getNumericValue(input.charAt(1)), Character.getNumericValue(input.charAt(3)));
        Actor HarryCopy = new Actor(Harry.getX(), Harry.getY());
        Cell Filch = new Cell(Character.getNumericValue(input.charAt(7)), Character.getNumericValue(input.charAt(9)));
        Cell Cat = new Cell(Character.getNumericValue(input.charAt(13)), Character.getNumericValue(input.charAt(15)));
        Cell Book = new Cell(Character.getNumericValue(input.charAt(19)), Character.getNumericValue(input.charAt(21)));
        Cell Cloak = new Cell(Character.getNumericValue(input.charAt(25)), Character.getNumericValue(input.charAt(27)));
        Cell Exit = new Cell(Character.getNumericValue(input.charAt(31)), Character.getNumericValue(input.charAt(33)));

        if (findLogicError(Harry, Filch, Cat, Book, Cloak, Exit)){
            return;
        }
        Actor.followBacktracking(Harry,Filch,Cat,Book,Cloak,Exit,scenario);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Actor.followAStar(HarryCopy,Filch,Cat,Book,Cloak,Exit, scenario);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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

    private int step;
    private long timeTaken;

    private String outcome;
    public static int[][] map = new int[9][9];
    public static Stack<Cell> stack = new Stack<>();

    public boolean isHaveBook() {
        return haveBook;
    }

    public boolean isHaveCloak() {
        return haveCloak;
    }


    public int getStep() {
        return step;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setHaveBook(boolean haveBook) {
        this.haveBook = haveBook;
    }

    public void setHaveCloak(boolean haveCloak) {
        this.haveCloak = haveCloak;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Actor(int x, int y) {
        super(x, y);
        haveBook = false;
        haveCloak = false;

        step = 0;
        timeTaken = 0;
        outcome = "";
        for (int[] raw: map){
            Arrays.fill(raw, 0);
        }
    }

    public static boolean isInLegalZone(int x, int y){return x >= 0 && x < 9 && y < 9 && y >= 0;}

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

    public static void printAlgorithmInfo(Actor Harry, Cell Filch, Cell Cat, Cell Book, Cell Cloak, Cell Exit, int scenario){
        System.out.println("Scenario  : " + scenario);
        System.out.println("Harry : [" + Harry.getX() + "," + Harry.getY() + "]");
        System.out.println("Filch : [" + Filch.getX() + "," + Filch.getY() + "]");
        System.out.println("Cat   : [" + Cat.getX() + "," + Cat.getY() + "]");
        System.out.println("Book  : [" + Book.getX() + "," + Book.getY() + "]");
        System.out.println("Cloak : [" + Cloak.getX() + "," + Cloak.getY() + "]");
        System.out.println("Exit  : [" + Exit.getX() + "," + Exit.getY() + "]");
        System.out.print("Path of the algorithm: ");
    }

    public static ArrayList<Cell> backtracking(Actor Harry, ArrayList<Cell> visitedCells){
        ArrayList<Cell> path = new ArrayList<>();
        for (int i = Harry.getX() - 1; i < Harry.getX() + 2; ++i) {
            for (int j = Harry.getY() - 1; j < Harry.getY() + 2; ++j) {
                if (!isInLegalZone(i, j)) {
                    continue;
                }
                if (map[i][j] == -1 || map[i][j] == 1) {
                    continue;
                }
                if (isVisited(i, j, visitedCells)) {
                    continue;
                }
                stack.push(new Cell(Harry.getX(), Harry.getY()));
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

        System.out.println("Algorithm : Backtracking");
        printAlgorithmInfo(Harry, Filch, Cat, Book, Cloak, Exit, scenario);

        boolean isExitReached = false;
        ArrayList<Cell> visitedCells = new ArrayList<>();
        visitedCells.add(new Cell(0,0));
        ArrayList<Cell> currentPath;

        while (Harry.getX() != Exit.getX() || Harry.getY() != Exit.getY() || !Harry.isHaveBook()) {
            senseCatZone(Harry, Cat, scenario);
            senseFilchZone(Harry, Filch, scenario);
            long time0 = System.nanoTime();
            currentPath = backtracking(Harry, visitedCells);
            Harry.setTimeTaken(Harry.getTimeTaken() + System.nanoTime() - time0);
            if (currentPath == null){
                Harry.setOutcome("Lose");
                System.out.println("\nOutcome: " + Harry.getOutcome() + " (Impossible to reach exit)");
                System.out.println("Number of steps: " + Harry.getStep());
                System.out.println("Time taken to reach the door: " + Harry.getTimeTaken() + " nanoseconds");
                return;
            }
            Harry.setX(currentPath.get(0).getX());
            Harry.setY(currentPath.get(0).getY());
            Harry.setStep(Harry.getStep() + 1);
            System.out.print("[" + Harry.getX() + "," + Harry.getY() + "]");
            if (isInCatZone(Harry.getX(), Harry.getY(), Harry, Cat) || isInFilchZone(Harry.getX(), Harry.getY(),Harry, Filch)){
                Harry.setOutcome("Lose");
                System.out.println("\nOutcome: " + Harry.getOutcome() + " (Harry has been caught by inspector)");
                System.out.println("Number of steps: " + Harry.getStep());
                System.out.println("Time taken to reach the door: " + Harry.getTimeTaken() + " nanoseconds");
                return;
            }
            if (Harry.getX() == Exit.getX() && Harry.getY() == Exit.getY()){
                isExitReached = true;
            }
            checkBook(Harry, Book);
            checkCloak(Harry, Cloak);
            if (isExitReached && Harry.isHaveBook()){
                long time1 = System.nanoTime();
                currentPath = aStar(Harry, Exit);
                Harry.setTimeTaken(Harry.getTimeTaken() + System.nanoTime() - time1);
                visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
                for (Cell cell : currentPath){
                    Harry.setX(cell.getX());
                    Harry.setY(cell.getY());
                    Harry.setStep(Harry.getStep() + 1);
                    visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
                    System.out.print("[" + Harry.getX() + "," + Harry.getY() + "]");
                }
            } else {
                visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
            }
        }
        Harry.setOutcome("Win");
        System.out.println("\nOutcome: " + Harry.getOutcome());
        System.out.println("Number of steps: " + Harry.getStep());
        System.out.println("Time taken to reach the door: " + Harry.getTimeTaken() + " nanoseconds");
    }

    public static void followAStar(Actor Harry, Cell Filch, Cell Cat, Cell Book, Cell Cloak, Cell Exit, int scenario) {

        System.out.println("Algorithm : A*");
        printAlgorithmInfo(Harry, Filch, Cat, Book, Cloak, Exit, scenario);
        boolean isExitReached = false;
        long time0 = System.nanoTime();
        ArrayList<Cell> currentPath = aStar(Harry, Exit);
        Harry.setTimeTaken(Harry.getTimeTaken() + System.nanoTime() - time0);
        ArrayList<Cell> visitedCells = new ArrayList<>();

        visitedCells.add(new Cell(Harry.getX(), Harry.getY()));

        while (Harry.getX() != Exit.getX() || Harry.getY() != Exit.getY() || !Harry.isHaveBook()) {
            if (!isExitReached) {
                senseCatZone(Harry, Cat, scenario);
                senseFilchZone(Harry, Filch, scenario);
                long time1 = System.nanoTime();
                currentPath = aStar(Harry, Exit);
                Harry.setTimeTaken(Harry.getTimeTaken() + System.nanoTime() - time1);
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
                        long time2 = System.nanoTime();
                        currentPath = aStar(Harry, new Cell(i, j));
                        Harry.setTimeTaken(Harry.getTimeTaken() + System.nanoTime() - time2);
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
                    Harry.setOutcome("Lose");
                    System.out.println("\nOutcome: " + Harry.getOutcome() + " (Impossible to reach exit)");
                    System.out.println("Number of steps: " + Harry.getStep());
                    System.out.println("Time taken to reach the door: " + Harry.getTimeTaken() + " nanoseconds");
                }
                currentPath = shortestPath;
            }
            if (currentPath == null){
                return;
            }
            Harry.setX(currentPath.get(0).getX());
            Harry.setY(currentPath.get(0).getY());
            if (isInCatZone(Harry.getX(), Harry.getY(),Harry, Cat) || isInFilchZone(Harry.getX(), Harry.getY(),Harry, Filch)){
                Harry.setOutcome("Lose");
                System.out.println("\nOutcome: " + Harry.getOutcome() + " (Harry has been caught by inspector)");
                System.out.println("Number of steps: " + Harry.getStep());
                System.out.println("Time taken to reach the door: " + Harry.getTimeTaken() + " nanoseconds");
                return;
            }
            Harry.setStep(Harry.getStep() + 1);
            if (Harry.getX() == Exit.getX() && Harry.getY() == Exit.getY()){
                isExitReached = true;
            }
            currentPath.remove(0);
            visitedCells.add(new Cell(Harry.getX(), Harry.getY()));
            System.out.print("[" + Harry.getX() + "," + Harry.getY() + "]");
            checkBook(Harry, Book);
            if (Harry.haveBook){
                isExitReached = false;
                currentPath = aStar(Harry, Exit);
            }
            checkCloak(Harry, Cloak);
            senseCatZone(Harry, Cat, scenario);
            senseFilchZone(Harry, Filch, scenario);
        }
        Harry.setOutcome("Win");
        System.out.println("\nOutcome: " + Harry.getOutcome());
        System.out.println("Number of steps: " + Harry.getStep());
        System.out.println("Time taken to reach the door: " + Harry.getTimeTaken() + " nanoseconds");
    }
}

class Statistic{
    private int passedTestsNumber;
    private int failedTestsNumber;

    private double probWinAStar;
    private double probWinBacktrack;

    private int winOutcomeNumAStar;
    private int winOutcomeNumBacktrack;

    private double avStepNumAStar;
    private double avStepNumBacktrack;
    private long avTimeAStar;
    private long avTimeBacktrack;

    public int getPassedTestsNumber() {
        return passedTestsNumber;
    }

    public int getFailedTestsNumber() {
        return failedTestsNumber;
    }

    public double getProbWinAStar() {
        return probWinAStar;
    }

    public double getProbWinBacktrack() {
        return probWinBacktrack;
    }

    public int getWinOutcomeNumAStar() {
        return winOutcomeNumAStar;
    }

    public int getWinOutcomeNumBacktrack() {
        return winOutcomeNumBacktrack;
    }

    public double getAvStepNumAStar() {
        return avStepNumAStar;
    }

    public double getAvStepNumBacktrack() {
        return avStepNumBacktrack;
    }

    public long getAvTimeAStar() {
        return avTimeAStar;
    }

    public long getAvTimeBacktrack() {
        return avTimeBacktrack;
    }

    public void setPassedTestsNumber(int passedTestsNumber) {
        this.passedTestsNumber = passedTestsNumber;
    }

    public void setFailedTestsNumber(int failedTestsNumber) {
        this.failedTestsNumber = failedTestsNumber;
    }

    public void setProbWinAStar(double probWinAStar) {
        this.probWinAStar = probWinAStar;
    }

    public void setProbWinBacktrack(double probWinBacktrack) {
        this.probWinBacktrack = probWinBacktrack;
    }

    public void setWinOutcomeNumAStar(int winOutcomeNumAStar) {
        this.winOutcomeNumAStar = winOutcomeNumAStar;
    }

    public void setWinOutcomeNumBacktrack(int winOutcomeNumBacktrack) {
        this.winOutcomeNumBacktrack = winOutcomeNumBacktrack;
    }

    public void setAvStepNumAStar(double avStepNumAStar) {
        this.avStepNumAStar = avStepNumAStar;
    }

    public void setAvStepNumBacktrack(double avStepNumBacktrack) {
        this.avStepNumBacktrack = avStepNumBacktrack;
    }

    public void setAvTimeAStar(long avTimeAStar) {
        this.avTimeAStar = avTimeAStar;
    }

    public void setAvTimeBacktrack(long avTimeBacktrack) {
        this.avTimeBacktrack = avTimeBacktrack;
    }

    public void printStatistic(){
        System.out.println("\nStatistical Analysis:");
        System.out.println(getPassedTestsNumber() + " tests have been passed");
        System.out.println(getFailedTestsNumber() + " tests have been failed");
        System.out.printf("Probability of winning test using Backtracking method: %.3f %n", getProbWinBacktrack());
        System.out.printf("Probability of winning test using A* method: %.3f %n", getProbWinAStar());
        System.out.println("There was " + getWinOutcomeNumBacktrack() + " winning outcomes when Backtracking method was used");
        System.out.println("There was " + getWinOutcomeNumAStar() + " winning outcomes when A* method was used");
        System.out.printf("There were %.3f steps on average to reach exit using Backtracking method %n", getAvStepNumBacktrack());
        System.out.printf("There were %.3f steps on average to reach exit using A* method %n", getAvStepNumAStar());
        System.out.println("On average, " + getAvTimeBacktrack() + " nanoseconds needed to reach exit using Backtracking method");
        System.out.println("On average, " + getAvTimeAStar() + " nanoseconds needed to reach exit using A* method");
    }
}