import java.util.ArrayList;
import java.util.Arrays;
public class Calculator{
    /**
     * In this function, the numbers in cpu are calculate by Reverse Polish Notation (RPN).
     * The last of the stack in cpu is "," or ";".
     * @param cpu       It contains numbers and operands.
     * @return a cpu in which the numbers in stack is calculated.
     */
    public static VirtualCPU calculateReversePolish(VirtualCPU cpu){
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
            int num = 0;
            switch(cpu.getStack(i)){
                case "+":   i = i - 2;
                            num = cpu.popFromValues(i) + cpu.popFromValues(i);
                            cpu.pop(i); // remove "+" from stack.
                            cpu.addToStack(i, num, Integer.toString(num));
                            break;
                case "-":   i = i - 2;
                            num = cpu.popFromValues(i) - cpu.popFromValues(i);
                            cpu.pop(i); // remove "-" from stack.
                            cpu.addToStack(i, num, Integer.toString(num));
                            break;
                case "*":   i = i - 2;
                            num = cpu.popFromValues(i) * cpu.popFromValues(i);
                            cpu.pop(i); // remove "*" from stack.
                            cpu.addToStack(i, num, Integer.toString(num));
                            break;
                case "/":   i = i - 2;
                            num = cpu.popFromValues(i) / cpu.popFromValues(i);
                            cpu.pop(i); // remove "/" from stack.
                            cpu.addToStack(i, num, Integer.toString(num));
                            break;
                case "=":   cpu.setValues(i-1, cpu.getValue(i+1));
                            cpu.pop(i+1);
                            cpu.pop(i);
                            i = i - 2;
                            break;
                case ";":   cpu.pop(i); // remove ";" from stack.
                            i = i - 3;
                            break;
                case ",":   cpu.pop(i); // remove ";" from stack.
                            i = i - 3;
                            break;
                default:    i++;
            }
        }
        return cpu;
    }

    /**
     * In this function, Shunting-yard algorithm is operated.
     * @param cpu           It doesn't have a formula.
     * @param formular      It is a formula.
     * @return a cpu including formula, which is orderey by Shunting-yard algorithm.
     */
    public static VirtualCPU doShuntingYard(VirtualCPU cpu, ArrayList<String> formular){
        ArrayList<String> queue = new ArrayList<String>();
        ArrayList<String> stack = new ArrayList<String>();
        String[] level1 = {"+", "-"}, level2 = {"*", "/"};
        /**
         * In this roop, all of the String in forluma are popped.
         */
        while(formular.size()>0){
            /**
             * If the first of formula is a number, it goes into try.
             * If not, it goes to chatch.
             * @param check     This can only be used to check whether the string can be changed into integer or not.
             */
            try{
                int check = Integer.parseInt(formular.get(0));
                queue.add(formular.get(0));
                formular.remove(0);
            }catch(NumberFormatException e){
                String operator = formular.get(0);
                switch(operator){
                    case "(":
                        stack.add(formular.get(0));
                        break;
                    case ")":
                        while(!stack.get(stack.size()-1).equals("(")){
                            queue.add(stack.get(stack.size()-1));
                            stack.remove(stack.size()-1);
                        }
                        stack.remove(stack.size()-1);
                        break;
                    default:
                        while(stack.size()>0 && Arrays.asList(level2).contains( stack.get(stack.size()-1))){
                            queue.add(stack.get(stack.size()-1));
                            stack.remove(stack.size()-1);
                        }
                        stack.add(formular.get(0));
                }
                formular.remove(0);
            }
        }
        /**
         * In this roop, the rests of stack pop and be pushed onto the queue.
         */
        while(stack.size()>0){
            queue.add(stack.get(stack.size()-1));
            stack.remove(stack.size()-1);
        }
        /**
         * In this roop, the Strings in the queue are pushed onto the cpu.
         */
        while(queue.size()>0){
            int value = 0;
            String symbol = queue.get(0);
            try{
                value = Integer.parseInt(symbol);
            }catch(NumberFormatException e){
                value = 0;
            }
            cpu.addToStack(value, symbol);
            queue.remove(0);
        }
        return cpu;
    }
}