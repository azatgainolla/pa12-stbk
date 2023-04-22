
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class StackNode<E> {
    E data;
    StackNode<E> next;

    public StackNode(E data) {
        this.data = data;
    }
}

class LinkedStack<E> {
    private StackNode<E> top;
    private int size;

    public LinkedStack(E[] objects) {
        for (int i = 0; i < objects.length; i++) {
            push(objects[i]);
        }
    }

    public boolean isEmpty() {
        // your code goes here
        return false;
    }

    public int size() {
        // your code goes here
        return -1;
    }

    public void push(E e) {
        // your code goes here
    }

    public E pop() throws EmptyStackException {
        // your code goes here
        return null;
    }

    public E peek() throws EmptyStackException {
        // your code goes here
        return null;
    }

    @Override
    public String toString() {
        // your code goes here
        return null;
    }

    public Iterator<E> iterator() {
        return new StackIterator();
    }

    class StackIterator implements Iterator<E> {

        boolean canRemove = false;
        int previousLoc = -1;
        StackNode<E> current = top;

        @Override
        public boolean hasNext() {
            // your code goes here
            return false;
        }

        @Override
        public E next() {
            // your code goes here
            return null;
        }
    }

    public ListIterator<E> listIterator() {
        // Only for C task !!!!!!!!
        return null;
        // return new LinkedStack.StackListIterator();
    }
}

public class PA12B {
    public static void main(String[] args) {
        LinkedStack<Integer> linkedStack = new LinkedStack<>(new Integer[]{1,2,3,4,5,6});
        while(linkedStack.size() > 0) {
            System.out.println(linkedStack.size() + "_linkedStack.peek(): " + linkedStack.peek());
            System.out.println(linkedStack.size() + "_linkedStack.pop(): " + linkedStack.pop());
            System.out.println(linkedStack.size() + "_After pop: " + linkedStack.toString() + "\n");
        }

        for (int i = 0; i < 6; i++) {
            linkedStack.push(i+25);
            System.out.println(i + "_After push" + "(" + (i+25) +  ")" + ": " + linkedStack.toString());
        }

        Iterator<Integer> iterator = linkedStack.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        iterator = linkedStack.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
}
