import java.util.ArrayList;
import java.util.Arrays;
public class Calculator{
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
                case "int":{
                    //System.out.println("integer "+ram.getValue(index));
                    break;
                }
                case "double":{
                    //System.out.println("double "+ram.getValue(index));
                    break;
                }
            }
            index++;
        }
        return ram;
    }

    /**
     * In this function, the numbers in ram are calculate by Reverse Polish Notation (RPN).
     * The last of the stack in ram is "," or ";".
     * @param ram       It contains numbers and operands.
     * @return a ram in which the numbers in stack is calculated.
     */
    public static VirtualRAM calculateReversePolish(VirtualRAM ram){
        /**
         * First, we find the index of "=".
         * @param border    This is the index where "=" is.
         */
        int border = 0;
        for(int i=0; i<ram.size(); i++){
            if(ram.getStack(i).equals("=")) border = i;
        }
        /**
         * Second, we calculate the stack in ram by RPN.
         * @param i         This is the index of after "=".
         */
        int i = border+1;
        while(ram.size()-1 > border){
            int num = 0;
            switch(ram.getStack(i)){
                case "+":   i = i - 2;
                            num = ram.popFromValues(i) + ram.popFromValues(i);
                            ram.pop(i); // remove "+" from stack.
                            ram.addToStack(i, num, Integer.toString(num));
                            break;
                case "-":   i = i - 2;
                            num = ram.popFromValues(i) - ram.popFromValues(i);
                            ram.pop(i); // remove "-" from stack.
                            ram.addToStack(i, num, Integer.toString(num));
                            break;
                case "*":   i = i - 2;
                            num = ram.popFromValues(i) * ram.popFromValues(i);
                            ram.pop(i); // remove "*" from stack.
                            ram.addToStack(i, num, Integer.toString(num));
                            break;
                case "/":   i = i - 2;
                            num = ram.popFromValues(i) / ram.popFromValues(i);
                            ram.pop(i); // remove "/" from stack.
                            ram.addToStack(i, num, Integer.toString(num));
                            break;
                case "=":   ram.setValues(i-1, ram.getValue(i+1));
                            ram.pop(i+1);
                            ram.pop(i);
                            i = i - 2;
                            break;
                case ";":   ram.pop(i); // remove ";" from stack.
                            i = i - 3;
                            break;
                case ",":   ram.pop(i); // remove ";" from stack.
                            i = i - 3;
                            break;
                default:    i++;
            }

            for(int j=0; j<ram.size(); j++){
                System.out.print(ram.getStack(j) + " ");
            }
            System.out.println();
        }
        return ram;
    }

    /**
     * In this function, Shunting-yard algorithm is operated.
     * @param ram           It doesn't have a formula.
     * @param formular      It is a formula.
     * @return a ram including formula, which is orderey by Shunting-yard algorithm.
     */
    public static VirtualRAM doShuntingYard(VirtualRAM ram, ArrayList<String> formular){
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
                    /*case "+":
                    case "-":
                        if(stack.size()>0){
                            queue.add(stack.get(stack.size()-1));
                            stack.remove(stack.size()-1);
                        }*/
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
         * In this roop, the Strings in the queue are pushed onto the ram.
         */
        while(queue.size()>0){
            int value = 0;
            String symbol = queue.get(0);
            try{
                value = Integer.parseInt(symbol);
            }catch(NumberFormatException e){
                value = 0;
            }
            ram.addToStack(value, symbol);
            queue.remove(0);
        }
        return ram;
    }
}