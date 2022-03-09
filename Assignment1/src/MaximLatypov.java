import java.io.*;

public class MaximLatypov {

    private char x;
    private char y;

    public MaximLatypov(char x, char y){
        this.x = x;
        this.y = y;
    }

    public char getX() {
        return x;
    }

    public char getY() {
        return y;
    }

    public static void main(String[] args) {

        String input = "";
        String scenario = "";
        boolean haveBook = false;
        boolean haveCloak = false;

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

        MaximLatypov Harry = new MaximLatypov(input.charAt(1), input.charAt(3));
        MaximLatypov Filch = new MaximLatypov(input.charAt(7), input.charAt(9));
        MaximLatypov Cat = new MaximLatypov(input.charAt(13), input.charAt(15));
        MaximLatypov Book = new MaximLatypov(input.charAt(19), input.charAt(21));
        MaximLatypov Cloak = new MaximLatypov(input.charAt(25), input.charAt(27));
        MaximLatypov Exit = new MaximLatypov(input.charAt(31), input.charAt(33));

        findLogicError(Harry, Filch, Cat, Book, Cloak, Exit);

        isInBookCell(Harry,Book, haveBook);
        isInExitCell(Harry,Exit);
        isInCloakCell(Harry,Cloak, haveCloak);
        isInFilchZone(Harry,Filch, haveCloak);
        isInCatZone(Harry,Cat,haveCloak);
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
            if (((input.charAt(i) > 57) || (input.charAt(i) < 48)) && ((input.charAt(i) != '[') && (input.charAt(i) != ']') && (input.charAt(i) != ',') && (input.charAt(i) != ' '))){
                System.out.println("Error occurred, invalid input: input value out of legal range");
                System.exit(0);
            }
        }


        for (int i = 1, j = 3; i < 32; i = i + 6, j = i + 2){
            if ((input.charAt(i) > 57) || (input.charAt(i) < 48)) {
                System.out.println("Error occurred, invalid input: input value is illegal");
                System.exit(0);
            }
            if ((input.charAt(j) > 57) || (input.charAt(j) < 48)) {
                System.out.println("Error occurred, invalid input: input value is illegal");
                System.exit(0);
            }
        }


        if (input.length() != 35){
            System.out.println("Error occurred, invalid input: illegal number of input values");
            System.exit(0);
        }
    }

    public static void findLogicError(MaximLatypov Harry, MaximLatypov Filch, MaximLatypov Cat, MaximLatypov Book, MaximLatypov Cloak, MaximLatypov Exit){

        if (Math.sqrt(Math.pow((int)Harry.getX() - (int)Filch.getX(), 2) + Math.pow((int)Harry.getY() - (int)Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Harry is already in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow((int)Book.getX() - (int)Filch.getX(), 2) + Math.pow((int)Book.getY() - (int)Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Book is in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow((int)Cloak.getX() - (int)Filch.getX(), 2) + Math.pow((int)Cloak.getY() - (int)Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Cloak is in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow((int)Exit.getX() - (int)Filch.getX(), 2) + Math.pow((int)Exit.getY() - (int)Filch.getY(), 2)) < 3){
            System.out.println("Error occurred, invalid input: Exit is in inspector's zone");
            System.exit(0);
        }


        if (Math.sqrt(Math.pow((int)Harry.getX() - (int)Cat.getX(), 2) + Math.pow((int)Harry.getY() - (int)Cat.getY(), 2)) == 1){
            System.out.println("Error occurred, invalid input: Harry is already in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow((int)Book.getX() - (int)Cat.getX(), 2) + Math.pow((int)Book.getY() - (int)Cat.getY(), 2)) == 1){
            System.out.println("Error occurred, invalid input: Book is in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow((int)Cloak.getX() - (int)Cat.getX(), 2) + Math.pow((int)Cloak.getY() - (int)Cat.getY(), 2)) == 1){
            System.out.println("Error occurred, invalid input: Cloak is in inspector's zone");
            System.exit(0);
        }
        if (Math.sqrt(Math.pow((int)Exit.getX() - (int)Cat.getX(), 2) + Math.pow((int)Exit.getY() - (int)Cat.getY(), 2)) == 1){
            System.out.println("Error occurred, invalid input: Exit is in inspector's zone");
            System.exit(0);
        }


        if ((Exit.getX() == Book.getX()) && (Exit.getY() == Book.getY())){
            System.out.println("Error occurred, invalid input: Exit and Book can not be on the same cell");
            System.exit(0);
        }
    }


    public static boolean isInBookCell(MaximLatypov Harry, MaximLatypov Book, boolean haveBook){
        if (haveBook){
            return false;
        }
        if ((Harry.getX() == Book.getX()) && (Harry.getY() == Book.getY())){
            haveBook = true;
            return true;
        }
        return false;
    }

    public static boolean isInExitCell(MaximLatypov Harry, MaximLatypov Exit){
        if ((Harry.getX() == Exit.getX()) && (Harry.getY() == Exit.getY())){
            return true;
        }
        return false;
    }

    public static boolean isInCloakCell(MaximLatypov Harry, MaximLatypov Cloak, boolean haveCloak){
        if (haveCloak){
            return false;
        }
        if ((Harry.getX() == Cloak.getX()) && (Harry.getY() == Cloak.getY())){
            haveCloak = true;
            return true;
        }
        return false;
    }

    public static boolean isInFilchZone(MaximLatypov Harry, MaximLatypov Filch, boolean haveCloak) {
        if (!haveCloak) {
            if (Math.sqrt(Math.pow((int) Harry.getX() - (int) Filch.getX(), 2) + Math.pow((int) Harry.getY() - (int) Filch.getY(), 2)) < 3) {
                System.out.println("Game over! Filch has found you!");
                return true;
            }
        } else{
            if ((Harry.getX() == Filch.getX()) && (Harry.getY() == Filch.getY())) {
                System.out.println("Game over! Filch has found you!");
                return true;
            }
        }
        return false;
    }

    public static boolean isInCatZone(MaximLatypov Harry, MaximLatypov Cat, boolean haveCloak) {
        if (!haveCloak) {
            if (Math.sqrt(Math.pow((int) Harry.getX() - (int) Cat.getX(), 2) + Math.pow((int) Harry.getY() - (int) Cat.getY(), 2)) == 1) {
                System.out.println("Game over! Cat has found you!");
                return true;
            }
        } else{
            if ((Harry.getX() == Cat.getX()) && (Harry.getY() == Cat.getY())) {
                System.out.println("Game over! Filch has found you!");
                return true;
            }
        }
        return false;
    }
}