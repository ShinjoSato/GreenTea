import java.util.ArrayList;
/**
 * This class represents Output which is the Von Neumann architecture.
 * @author Shinjo Sato
 * @version 15-12-2019
 */
public class Output{
    /**
     * This function stacks strings which we want to print on the terminal in the ram, and finally print them.
     * @param strings               This is a stack which includs strings between double quotations we'd like to print.
     * @param symbol                This stack includes strings in the ram.
     * @param inDoubleQuotation     This judges whether the string on top of the ram is included between double quotation or not.
     */
    public static VirtualRAM print(VirtualRAM ram, String function){
        ArrayList<String> strings = new ArrayList<String>();
        String symbol = ram.getStack(ram.size()-1);
        boolean inDoubleQuotation = false;
        /**
         * This loop doesn't finish while the string on top of the ram is not
         * functions, i.e., print or println. In this loop, @param symbol contains
         * strings on top of the ram.
         */
        while(!symbol.equals(function)){
            switch(symbol){
                case "(":{
                }
                case ")":{
                    ram.pop(ram.size()-1);
                    break;
                }
                case "\"":{
                    ram.pop(ram.size()-1);
                    inDoubleQuotation =(inDoubleQuotation)? false : true;
                    if(!inDoubleQuotation && strings.get(strings.size()-1).equals(" ")){
                        strings.remove(strings.size()-1);
                    }
                    break;
                }
                default:{
                    strings.add(ram.popFromStack(ram.size()-1));
                    if(inDoubleQuotation){
                        strings.add(" ");
                    }
                }
            }
            symbol = ram.getStack(ram.size()-1);
        }
        /**
         * Function name on the top of the ram is popped from the ram.
         */
        ram.pop(ram.size()-1);
        /**
         * In this loop, strings in @param strings are printed.
         * If index is greather than -1, the symbol of the string is already defined in ram.
         */
        while(strings.size() > 0){
            int index = ram.getStack().lastIndexOf(strings.get(strings.size()-1));
            if(index > -1){
                System.out.print(ram.getValue(index));
            }else{
                System.out.print(strings.get(strings.size()-1));
            }
            strings.remove(strings.size()-1);
        }
        return ram;
    }

    /**
     * This function print strings in the ram by using print().
     * @param ram       This includes strings which uses want to println.
     * @return the ram which extrudes strings which is printted.
     */
    public static  VirtualRAM println(VirtualRAM ram){
        ram = print(ram, "println");
        System.out.println();
        return ram;
    }
}