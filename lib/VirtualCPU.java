import java.util.ArrayList;

public class VirtualCPU{
    
    private ArrayList<Integer> values;
    private ArrayList<String> stack;

    public VirtualCPU(){
        this.values = new ArrayList<Integer>();
        this.stack = new ArrayList<String>();
    }

    public void addToStack(int value, String str){
        addToStack(size(), value, str);
    }

    public void addToStack(int index, int value, String str){
        this.values.add(index, value);
        this.stack.add(index, str);
    }

    public int getValue(int index){
        return values.get(index);
    }

    public String getStack(int index){
        return stack.get(index);
    }

    public void setValues(int index, int num) {
        values.set(index, num);
    }
    
    @Override
    public String toString(){
        String str = "maxIndex:" + (stack.size()-1) + "\n";
        for(int i=0; i<stack.size(); i++){
            str += stack.get(i) + ":" + values.get(i) + "\n";
        }
        return str;
    }

    public void pull(int index){
        stack.remove(index);
        values.remove(index);
    }

    public String pullFromStack(){
        return pullFromStack(stack.size() - 1);
    }

    public String pullFromStack(int index){
        String str = stack.get(index);
        //stack.remove(index);
        pull(index);
        return str;
    }

    public int pullFromValues(){
        return pullFromValues(values.size() - 1);
    }

    public int pullFromValues(int index){
        int num = values.get(index);
        pull(index);
        return num;
    }

    public int size(){
        return stack.size();
    }
}