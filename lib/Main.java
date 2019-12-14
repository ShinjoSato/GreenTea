import java.util.ArrayList;
/**
 * @author Shinjo Sato
 * @version 13-12-2019
 */
public class Main{
    public static void main(String[] args){
        /**
         * @param str       This is the string of program, GreenTea.
         * @param cpu       This is a virtual CPU, which has values and symbols.
         * @param symbols   This is the strings, which is spritted, of some program text.
         * @param formular  This contains mathematical operators and numbers.
         */
        String str = "int A = 10 + -3 + 2 + 1 , B = 3 / 3 * 2 ; int C = 12 - -9 ; double D = 1 - 10 ; int E = ( ( 8 + 2 ) * ( 2 + 2 + 3 * 2 ) ) + 5 * 9 ; ";
        VirtualCPU cpu = new VirtualCPU();
        String[] symbols = str.split(" ");
        ArrayList<String> formular = new ArrayList<String>();        
        for(int i = 0; i < symbols.length; i++){
            /**
             * Define the type of the variable.
             */
            String s = symbols[i];
            if(s.equals("int")) cpu.addToStack(0, "int");
            else if(s.equals("double")) cpu.addToStack(0, "double");
            else if(s.equals(";")){
                /**
                 * ; means the end of the line.
                 * Calculate from here.
                 */
                cpu = Calculator.doShuntingYard(cpu, formular);
                cpu.addToStack(0, s);
                cpu = Calculator.calculateReversePolish(cpu); //cpu = calculateReversePolish(cpu);
                cpu = defineTypeOfValue(cpu);
            }
            else if(s.equals(",")){
                /**
                 * , means the end of the variable.
                 * Calculate from here.
                 */
                cpu = Calculator.doShuntingYard(cpu, formular);
                cpu.addToStack(0, s);
                cpu = Calculator.calculateReversePolish(cpu); //cpu = calculateReversePolish(cpu);
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
                        default: cpu.addToStack(0, s);
                    }
                }
            }
        }
        System.out.println(cpu);
    }

    /**
     * This function define the type of the variable.
     * @param cpu
     * @return cpu
     */
    public static VirtualCPU defineTypeOfValue(VirtualCPU cpu){
        int index = cpu.size()-1;
        /**
         * While the index number doesn't show type, i.e. int and double, 
         * index subtracts 1.
         */
        while(!(cpu.getStack(index).equals("int")||cpu.getStack(index).equals("double"))){
            index--;
        }
        String type = cpu.popFromStack(index);
        /**
         * While the index number is smaller than cpu size,
         * the symbols in the index are defined as the type.
         */
        while(index < cpu.size()){
            switch(type){
                case "int": 
                    System.out.println("integer "+cpu.getValue(index));
                    break;
                case "double":
                    System.out.println("double "+cpu.getValue(index));
                    break;
            }
            index++;
        }
        return cpu;
    }
}