import java.util.ArrayList;
/**
 * This is a class which is like the Von Neumann architecture.
 * @author Shinjo Sato
 * @version 15-12-2019
 */
public class CPU{
    /**
     * This function maintais all the process of these programs.
     * @param symbols   This is the strings, which is spritted, of some program text.
     * @param ram       This is a virtual RAM, which has values and symbols.
     * @param formular  This contains mathematical operators and numbers.
     */
    public static void init(String[] symbols){
        VirtualRAM ram = new VirtualRAM();
        ArrayList<String> formular = new ArrayList<String>();
        String function = "";  
        for(int i = 0; i < symbols.length; i++){
            /**
             * Define the type of the function, i.e., int, double and print(ln).
             */
            String s = symbols[i];
            switch(s){
                case "int":{
                    ram.addToStack(0, "int");
                    function = s;
                    break;
                }
                case "double":{
                    ram.addToStack(0, "double");
                    function = s;
                    break;
                }
                case "print":{
                    ram.addToStack(0, "print");
                    function = s;
                    break;
                }
                case "println":{
                    ram.addToStack(0, "println");
                    function = s;
                    break;
                }
                case ";":{
                    /**
                     * ; means the end of the line.
                     * If the function is "int" or "double", Calculate from Calculator.
                     */
                    switch(function){
                        case "int":{
                        }
                        case "double":{
                            ram = Calculator.doShuntingYard(ram, formular);
                            ram.addToStack(0, s);
                            ram = Calculator.calculateReversePolish(ram);
                            ram = Calculator.defineTypeOfValue(ram);
                            break;
                        }
                        case "print":{
                            ram = Output.print(ram, "print");
                            break;
                        }
                        case "println":{
                            ram = Output.println(ram);
                            break;
                        }
                    }
                    function = "";
                    break;
                }
                case ",":{
                    /**
                     * , means the end of the variable.
                     * Calculate from here.
                     */
                    ram = Calculator.doShuntingYard(ram, formular);
                    ram.addToStack(0, s);
                    ram = Calculator.calculateReversePolish(ram);
                    break;
                }
                default:{
                    switch(function){
                        /** Prepare for Shunting-yard algorithm.
                         * Stack integer number.
                         * If s is a number, it go into try.
                         * If not, it go into exception.
                         */
                        case "int":{
                        }
                        case "double":{
                            try{
                                /**
                                 * This can only be used to check whether the string 
                                 * can be changed into integer or not.
                                 */
                                int check = Integer.parseInt(s); 
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
                                    default:{
                                        /**
                                         * If s is the address of a number, the index is greater than or equal to 0.
                                         * If not, it shows -1.
                                         */
                                        int index = ram.getStack().lastIndexOf(s);
                                        if(index == -1){
                                            ram.addToStack(0, s);
                                        }
                                        else{
                                            formular.add(Integer.toString(ram.getValue(index)));
                                        }
                                    }
                                }
                            }
                            break;
                        }
                        case "print":{
                            ram.addToStack(0,s);
                            break;
                        }
                        case "println":{
                            ram.addToStack(0,s);
                            break;
                        }
                    }
                }
            }
        }
    }
}