import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;

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

    public void setX(char x) {
        this.x = x;
    }

    public void setY(char y) {
        this.y = y;
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

        aStar(Harry,Book,Filch,Cat,Cloak,Exit, haveCloak, haveBook);
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


    public static boolean isInBookCell(int x, int y, MaximLatypov Book, boolean haveBook){ //Might need to change
        if (haveBook){
            return false;
        }
        if ((x == Character.getNumericValue(Book.getX())) && (y == Character.getNumericValue(Book.getY()))){
            haveBook = true;
            return true;
        }
        return false;
    }

    public static boolean isInExitCell(int x, int y, MaximLatypov Exit){//Have not understood how to implement
        return (x == Character.getNumericValue(Exit.getX())) && (y == Character.getNumericValue(Exit.getY()));
    }

    public static boolean isInCloakCell(int x, int y, MaximLatypov Cloak, boolean haveCloak){ //Might need to change
        if (haveCloak){
            return false;
        }
        if ((x == Character.getNumericValue(Cloak.getX()) && (y == Character.getNumericValue(Cloak.getY())))){
            haveCloak = true;
            return true;
        }
        return false;
    }

    public static boolean isInFilchZone(int x, int y, MaximLatypov Filch, boolean haveCloak) {
        if (!haveCloak) {
            if (Math.sqrt(Math.pow(x - Character.getNumericValue(Filch.getX()), 2) + Math.pow(y - Character.getNumericValue(Filch.getY()), 2)) < 3) {
                System.out.println("Game over! Filch has found Harry!");
                System.out.println(x);
                System.out.println(y);
                return true;
            }
        } else{
            if ((x == Character.getNumericValue(Filch.getX())) && (y == Character.getNumericValue(Filch.getY()))) {
                System.out.println("Game over! Filch has found Harry!");
                return true;
            }
        }
        return false;
    }

    public static boolean isInCatZone(int x, int y, MaximLatypov Cat, boolean haveCloak) {
        if (!haveCloak) {
            if (Math.sqrt(Math.pow(x - Character.getNumericValue(Cat.getX()), 2) + Math.pow(y - Character.getNumericValue(Cat.getY()), 2)) < 2) {
                System.out.println("Game over! Cat has found Harry!");
                return true;
            }
        } else{
            if ((x == Character.getNumericValue(Cat.getX())) && (y == Character.getNumericValue(Cat.getY()))) {
                System.out.println("Game over! Filch has found Harry!");
                return true;
            }
        }
        return false;
    }

    public static boolean isInLegalZone(int x, int y){
        return x >= 0 && x < 9 && y < 9 && y >= 0;
    }

    public static int G(int x, int y, MaximLatypov Harry){
        return Math.max(Math.abs(x - Character.getNumericValue(Harry.getX())), Math.abs(y - Character.getNumericValue(Harry.getY())));
    }

    public static int H(int x, int y, MaximLatypov Exit){
        return Math.max(Math.abs(x - Character.getNumericValue(Exit.getX())), Math.abs(y - Character.getNumericValue(Exit.getY())));
    }

    public static void aStar(MaximLatypov Harry, MaximLatypov Book, MaximLatypov Filch, MaximLatypov Cat, MaximLatypov Cloak, MaximLatypov Exit, boolean haveCloak, boolean haveBook){

        ArrayList<Pair<Integer,Integer>> cellList = new ArrayList<>();

        for (int i = Character.getNumericValue(Harry.getX()) - 1 ; i < Character.getNumericValue(Harry.getX()) + 2; ++i){
            for (int j = Character.getNumericValue(Harry.getY()) - 1; j < Character.getNumericValue(Harry.getY()) + 2; ++j){
                if (isInLegalZone(i,j)){
                    if (isInFilchZone(i,j,Filch,haveCloak) || isInCatZone(i,j,Cat,haveCloak)){
                        continue;
                    }

                    if (isInBookCell(i,j,Book,haveBook)){

                    }
                }
            }
        }
    }
}