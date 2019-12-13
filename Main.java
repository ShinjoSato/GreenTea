import java.util.ArrayList;
/**
 * @author Shinjo Sato
 * @version 12-12-2019
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
        String str = "int A = 10 + 3 + 2 + 1 , B = 3 + 1 ; int C = 12 + 9 ; ";
        VirtualCPU cpu = new VirtualCPU();
        String[] symbols = str.split(" ");
        ArrayList<String> queue = new ArrayList<String>();
        boolean isQueue = false;
        
        for(String s: symbols){
            /**
             * Define the type of the variable.
             */
            if(s.equals("int")) cpu.addToStack(0,"int");
            else if(s.equals(";")){
                /**
                 * ; means the end of the line.
                 * Calculate from here.
                 */
                cpu.addToStack(0, s);
                cpu = calcReversePolish(cpu);
            }
            else if(s.equals(",")){
                /**
                 * , means the end of the variable.
                 * Calculate from here.
                 */
                cpu.addToStack(0, s);
                cpu = calcReversePolish(cpu);
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
     * In this function, the numbers in cpu are calculate by Reverse Polish Notation (RPN).
     * The last of the stack in cpu is "," or ";".
     * @param cpu       It contains numbers and operands.
     * @return cpu in which the numbers in stack is calculated.
     */
    public static VirtualCPU calcReversePolish(VirtualCPU cpu){
        /**
         * First, we find the index of "=".
         * @param border    This is the index where "=" is.
         */
        int border = 0;
        for(int i=0; i<cpu.size(); i++){
            if(cpu.getStack(i).equals("=")) border = i;
        }

        /**
         * Second, we calculate the stack in cpu by RPN.
         * @param i         This is the index of after "=".
         */
        int i = border+1;
        while(cpu.size()-1 > border){

            String key = cpu.getStack(i);
            switch(key){
                case "+":   i = i - 2;
                            int num = cpu.pullFromValues(i) + cpu.pullFromValues(i);
                            cpu.pull(i); // remove "+" from stack.
                            cpu.addToStack(i, num, Integer.toString(num));
                            break;
                case "=":   cpu.setValues(i-1, cpu.getValue(i+1));
                            cpu.pull(i+1);
                            cpu.pull(i);
                            i = i - 2;
                            break;
                case ";":   cpu.pull(i); // remove ";" from stack.
                            i = i - 3;
                            break;
                case ",":   cpu.pull(i); // remove ";" from stack.
                            i = i - 3;
                            break;
                default:    i++;
            }

            /*if(cpu.getStack(i).equals("+")){
                i = i - 2;
                int num = cpu.pullFromValues(i) + cpu.pullFromValues(i);
                cpu.pull(i); // remove "+" from stack.
                cpu.addToStack(i, num, Integer.toString(num));
            }else if(cpu.getStack(i).equals("=")){
                cpu.setValues(i-1, cpu.getValue(i+1));
                cpu.pull(i+1);
                cpu.pull(i);
                i = i - 2;
            }else if(cpu.getStack(i).equals(";") || cpu.getStack(i).equals(",")){
                cpu.pull(i); // remove ";" from stack.
                i = i - 3;
            }else{
                i++;
            }*/
        }
        return cpu;
    }
}