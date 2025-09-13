import java.util.Arrays;

class Stack {
    private final int[] Array;
    public int top;


    public Stack(int m) {
        Array = new int[m];
        top = -1;
    }

    public void push(int x) {
        top += 1;
        Array[top] = x;
    }

    public int pop() {
        int lastEl = Array[top];
        Array[top] = 0;
        top -= 1;
        return lastEl;
    }

    public boolean isTwoElements() {
        return top > 0;
    }
    public String element() {
        return Arrays.toString(Array);
    }
    public int lookElement() {
        return Array[top];
    }
}

