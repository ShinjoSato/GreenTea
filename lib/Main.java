import java.util.ArrayList;
/**
 * @author Shinjo Sato
 * @version 13-12-2019
 */
public class Main{
    public static void main(String[] args){
        /**
         * @param str       This is the string of program, GreenTea.
         * @param ram       This is a virtual RAM, which has values and symbols.
         * @param symbols   This is the strings, which is spritted, of some program text.
         * @param formular  This contains mathematical operators and numbers.
         */
        String str = "int A = 10 + -3 + 2 + 1 , B = 3 / 3 * 2 ; int C = 12 - -9 ; double D = 1 - 10 ; int E = ( ( 8 + 2 ) * ( 2 + 2 + 3 * 2 ) ) + 5 * 9 ; ";
        VirtualRAM ram = new VirtualRAM();
        String[] symbols = str.split(" ");
        ArrayList<String> formular = new ArrayList<String>();        
        for(int i = 0; i < symbols.length; i++){
            /**
             * Define the type of the variable.
             */
            String s = symbols[i];
            if(s.equals("int")) ram.addToStack(0, "int");
            else if(s.equals("double")) ram.addToStack(0, "double");
            else if(s.equals(";")){
                /**
                 * ; means the end of the line.
                 * Calculate from here.
                 */
                ram = Calculator.doShuntingYard(ram, formular);
                ram.addToStack(0, s);
                ram = Calculator.calculateReversePolish(ram); //ram = calculateReversePolish(ram);
                ram = defineTypeOfValue(ram);
            }
            else if(s.equals(",")){
                /**
                 * , means the end of the variable.
                 * Calculate from here.
                 */
                ram = Calculator.doShuntingYard(ram, formular);
                ram.addToStack(0, s);
                ram = Calculator.calculateReversePolish(ram); //ram = calculateReversePolish(ram);
            }
            else{
                /** Prepare for Shunting-yard algorithm
                 * Stack integer number.
                 * If s is a number, it go into try.
                 * If not, it go into exception.
                 */
                try{
                    int check = Integer.parseInt(s); // This can only be used to check whether the string can be changed into integer or not.
                    formular.add(s);
                }catch(NumberFormatException e){
                    switch(s){
                        case "+": formular.add(s);
                                    break;
                        case "-": formular.add(s);
                                    break;
                        case "*": formular.add(s);
                                break;
                        case "/": formular.add(s);
                                break;
                        case "(": formular.add(s);
                                break;
                        case ")": formular.add(s);
                                break;
                        default: ram.addToStack(0, s);
                    }
                }
            }
        }
        System.out.println(ram);
    }

    /**
     * This function define the type of the variable.
     * @param ram
     * @return ram
     */
    public static VirtualRAM defineTypeOfValue(VirtualRAM ram){
        int index = ram.size()-1;
        /**
         * While the index number doesn't show type, i.e. int and double, 
         * index subtracts 1.
         */
        while(!(ram.getStack(index).equals("int")||ram.getStack(index).equals("double"))){
            index--;
        }
        String type = ram.popFromStack(index);
        /**
         * While the index number is smaller than ram size,
         * the symbols in the index are defined as the type.
         */
        while(index < ram.size()){
            switch(type){
                case "int": 
                    System.out.println("integer "+ram.getValue(index));
                    break;
                case "double":
                    System.out.println("double "+ram.getValue(index));
                    break;
            }
            index++;
        }
        return ram;
    }
}