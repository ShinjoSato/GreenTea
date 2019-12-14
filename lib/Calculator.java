public class Calculator{
    /**
     * In this function, the numbers in cpu are calculate by Reverse Polish Notation (RPN).
     * The last of the stack in cpu is "," or ";".
     * @param cpu       It contains numbers and operands.
     * @return cpu in which the numbers in stack is calculated.
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
                            num = cpu.pullFromValues(i) + cpu.pullFromValues(i);
                            cpu.pull(i); // remove "+" from stack.
                            cpu.addToStack(i, num, Integer.toString(num));
                            break;
                case "-":   i = i - 2;
                            num = cpu.pullFromValues(i) - cpu.pullFromValues(i);
                            cpu.pull(i); // remove "-" from stack.
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
        }
        return cpu;
    }
}