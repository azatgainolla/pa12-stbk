

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class MyAbstractList<E> {

    Node<E> head;
    Node<E> tail;
    int size = 0;

    public MyAbstractList(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            addLast(objects[i]);
    }

    /**
     * Return true if this list doesn't contain any elements
     */
    public abstract boolean isEmpty();

    /**
     * Return the number of elements in this list
     */
    public abstract int size();

    /**
     * Clear the list
     */
    public abstract void clear();

    /**
     * Print the list
     */
    public abstract void print();

    /**
     * Return the element from this list at the specified index
     */
    public abstract E get(int index);

    /**
     * Return the first element from this list
     */
    public abstract E getFirst();

    /**
     * Return the last element from this list
     */
    public abstract E getLast();

    /**
     * Add a new element at the beginning of this list
     */
    public abstract void addFirst(E e);

    /**
     * Add a new element at the end of this list
     */
    public abstract void addLast(E e);

    /**
     * Add a new element at the specified index in this list
     */
    public abstract void add(int index, E e);

    /**
     * Remove the element from the beginning in this list.
     * Return the element that was removed from the list.
     */
    public abstract E removeFirst();

    /**
     * Remove the element from the end in this list.
     * Return the element that was removed from the list.
     */
    public abstract E removeLast();

    /**
     * Remove the element at the specified position in this list.
     * Return the element that was removed from the list.
     */
    public abstract E remove(int index);

    /**
     * Return true if this list contains the element
     */
    public abstract boolean contains(E e);

    /**
     * Return the index of the first matching element in this list.
     * Return -1 if no match.
     */
    public abstract int indexOf(E e);

    /**
     * Return the index of the last matching element
     * in this list. Return -1 if no match.
     */
    public abstract int lastIndexOf(E e);

    /**
     * Replace the element at the specified position
     * in this list with the specified element.
     */
    public abstract E set(int index, E e);

    /**
     * Override iterator() defined in Iterable
     * @return
     */
    public Iterator<E> iterator(){
        return new LLIterator();
    }

    private class LLIterator implements Iterator<E> {
        boolean canRemove = false;
        int previousLoc = -1;
        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E data = current.data;
                current = current.next;
                previousLoc++;
                canRemove = true;
                return data;
            }
            throw new NoSuchElementException("There are no next element");
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("You can't delete element before first next() method call");
            }

            MyAbstractList.this.remove(previousLoc);
            canRemove = false;
        }
    }
}


class Node<E> {
    E data;
    Node<E> next;

    public Node(E data) {
        this.data = data;
    }
}

class MyLL<E> extends MyAbstractList<E> {

    public MyLL(E[] objects) {
        super(objects);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void print() {
        if (size == 0) {
            System.out.println("[]");
            return;
        }

        StringBuilder result = new StringBuilder("[");
        Node<E> current = head;
        result.append(current.data.toString());
        result.append(", ");
        while (current.next != null) {
            current = current.next;
            result.append(current.data.toString());
            result.append(", ");
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.append("]");
        System.out.println(result.toString());
    }

    @Override
    public E get(int index) {
        // CASE 1
        if (index == 0) {
            return getFirst();
        }

        // CASE 2
        if (index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("Bad index " + index);
        }

        // CASE 3
        Node<E> current = head;
        int loopCounter = 0;
        while (loopCounter < index) {
            current = current.next;
            loopCounter++;
        }
        return current.data;
    }

    @Override
    public E getFirst() {
        if (head == null) {
            return null;
        }
        return head.data;
    }

    @Override
    public E getLast() {
        if (tail == null) {
            return null;
        }
        return tail.data;
    }

    @Override
    public void addFirst(E e) {
        // CASE 1: when linked list is empty
        if (head == null && tail == null) {
            Node<E> newNode = new Node<>(e);
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        // CASE 2: when linked list is not empty
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        // CASE 1: when linked list is empty
        if (head == null && tail == null) {
            Node<E> newNode = new Node<>(e);
            head = newNode;
            tail = newNode;
            size++;
            return;
        } 

        // CASE 2: when linked list is not empty
        Node<E> newNode = new Node<>(e);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(int index, E e) {
        // CASE 1: insert as first element
        if (index == 0) {
            addFirst(e);
        }

        // CASE 2: check index
        if (index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        // CASE 3
        Node<E> current = head;
        int loopCounter = 0;
        while (loopCounter < index - 1) {
            current = current.next;
            loopCounter++;
        }

        Node<E> newNode = new Node<>(e);
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        // CASE 1: linked list is empty
        if (head == null && tail == null) {
            return null;
        }

        // CASE 2: linked list has a single element
        if (size == 1) {
            E data = head.data;
            size = 0;
            head = null;
            tail = null;
            return data;
        }

        // CASE 3: linked list has more than one element
        E data = head.data;
        size--;
        Node<E> temp = head;
        head = head.next;
        temp.next = null;
        return data;
    }

    @Override
    public E removeLast() {
        // CASE 1: linked list is empty
        if (head == null && tail == null) {
            return null;
        }

        // CASE 2: linked list has a single element
        if (size == 1) {
            E data = tail.data;
            size = 0;
            head = null;
            tail = null;
            return data;
        }

        // CASE 3: linked list has more than one element
        E data = tail.data;
        size--;
        
        Node<E> current = head;
        while (current.next != tail) {
            current = current.next;
        }
        tail = current;
        tail.next = null;

        return data;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<E> current = head;
        int loopCounter = 0;
        while (loopCounter < index - 1) {
            current = current.next;
            loopCounter++;
        }
        
        Node<E> toRemove = current.next;
        current.next = toRemove.next;
        toRemove.next = null;
        size--;

        return toRemove.data;
    }

    @Override
    public boolean contains(E e) {
        if (head == null && tail == null) {
            return false;
        }

        Node<E> current = head;
        if (current.data.equals(e)) {
            return true;
        }
        while (current.next != null) {
            current = current.next;
            if (current.data.equals(e)) {
                return true;
            }    
        }
        
        return false;
    }

    @Override
    public int indexOf(E e) {
        Node<E> current = head;
        if (current.data.equals(e)) {
            return 0;
        }
        
        int index = 0;
        while (current.next != null) {
            current = current.next;
            index++;
            if (current.data.equals(e)) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        // your code goes here
        return -1;
    }


    @Override
    public E set(int index, E e) {
        // your code goes here
        // like get()
        return null;
    }
}

public class PA12A {
    public static void main(String[] args) {
        MyLL myLL = new MyLL(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 5, 9});
        System.out.println("myLL.size(): " + myLL.size());
        System.out.println("myLL.isEmpty(): " + myLL.isEmpty());
        myLL.print();
        System.out.println("myLL.get(2): " + myLL.get(2));
        System.out.println("myLL.getFirst(): " + myLL.getFirst());
        System.out.println("myLL.getLast(): " + myLL.getLast());

        myLL.add(2, 222);
        System.out.println("After : add(2, 222): " );
        myLL.print();
        myLL.addFirst(111);
        myLL.addFirst( 999);
        System.out.println("After : addFirst(111) and addFirst(999): " );
        myLL.print();

        System.out.println("myLL.remove(2): " + myLL.remove(2));
        System.out.println("myLL.removeFirst(): " + myLL.removeFirst());
        System.out.println("myLL.removeLast(): " + myLL.removeLast());
        myLL.print();

        System.out.println("myLL.contains(111): " + myLL.contains(111));
        System.out.println("myLL.contains(5): " + myLL.contains(5));
        System.out.println("myLL.indexOf(5): " + myLL.indexOf(5));
        System.out.println("myLL.lastIndexOf(5): " + myLL.lastIndexOf(5));
        System.out.println("myLL.lastIndexOf(88): " + myLL.lastIndexOf(88));

        System.out.println("myLL.set(2, 22): " + myLL.set(2, 22));
        System.out.println("myLL.set(1, 3333): " + myLL.set(1, 3333));

        Iterator<Integer> iterator = myLL.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        myLL.clear();
        System.out.println("myLL.isEmpty(): " + myLL.isEmpty());
        myLL.print();
    }
}
