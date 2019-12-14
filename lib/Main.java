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
         * @param queue     It includes + or -.
         * @param isQueue   It's used in calculation. If it's true, + or - is in "queue".
         */
        String str = "int A = 10 + -3 + 2 + 1 , B = 3 + 1 ; int C = 12 - -9 ; double D = 1 - 10 ;";
        VirtualCPU cpu = new VirtualCPU();
        String[] symbols = str.split(" ");
        ArrayList<String> queue = new ArrayList<String>();
        boolean isQueue = false;
        
        for(String s: symbols){
            /**
             * Define the type of the variable.
             */
            if(s.equals("int")) cpu.addToStack(0, "int");
            else if(s.equals("double")) cpu.addToStack(0, "double");
            else if(s.equals(";")){
                /**
                 * ; means the end of the line.
                 * Calculate from here.
                 */
                cpu.addToStack(0, s);
                cpu = Calculator.calculateReversePolish(cpu); //cpu = calculateReversePolish(cpu);
                cpu = defineTypeOfValue(cpu);
            }
            else if(s.equals(",")){
                /**
                 * , means the end of the variable.
                 * Calculate from here.
                 */
                cpu.addToStack(0, s);
                cpu = Calculator.calculateReversePolish(cpu); //cpu = calculateReversePolish(cpu);
            }
            else{
                /**
                 * Stack integer number.
                 * If s is a number, it go through try.
                 * If not, it go through exception.
                 */
                try{
                    cpu.addToStack(Integer.parseInt(s), s);
                    if(isQueue){
                        cpu.addToStack(0, queue.get(0));
                        queue.remove(0);
                        isQueue = false;
                    }
                }catch(NumberFormatException e){
                    if(s.equals("+") || s.equals("-")){
                        queue.add(s);
                        isQueue = true;
                    }else{
                        cpu.addToStack(0, s);
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
        String type = cpu.pullFromStack(index);
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