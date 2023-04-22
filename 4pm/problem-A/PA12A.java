

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

        // Node<E> pointer = head;
        // while (pointer.next != null) {
        //     pointer = pointer.next;
        // }

        // Node<E> pointer = head;
        // for (int i = 0; i < index; i++) {
        //     pointer = pointer.next;
        // }
        
        // if (!(0 <= index && index <= size - 1)) {
        //     throw new ArrayIndexOutOfBoundsException();
        // }

        // Node<E> newNode = new Node<>(e);
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
        Node<E> pointer = head;
        if (pointer == null) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        System.out.print(pointer.data + ", ");
        while (pointer.next != null) {
            pointer = pointer.next;
            if (pointer.next == null) {
                System.out.print(pointer.data);
            } else {
                System.out.print(pointer.data + ", ");
            }
        }
        System.out.println("]");

    }

    @Override
    public E get(int index) {
        if (!(0 <= index && index <= size - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Node<E> pointer = head;
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }

        return pointer.data;
    }

    @Override
    public E getFirst() {
        return head.data;
    }

    @Override
    public E getLast() {
        return tail.data;
    }

    @Override
    public void addFirst(E e) {
        // CASE 1: empty
        if (head == null && tail == null) {
            Node<E> newNode = new Node<>(e);
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        // CASE 2: not empty
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        // CASE 1: empty
        if (head == null && tail == null) {
            Node<E> newNode = new Node<>(e);
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        // CASE 2: not empty
        Node<E> newNode = new Node<>(e);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(int index, E e) {
        // boundary check
        if (!(0 <= index && index <= size)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        // special case
        if (index == 0) {
            addFirst(e);
        }
        
        // special case
        if (index == size) {
            addLast(e);
        }

        Node<E> previousToInsert = head;
        for (int i = 0; i < index - 1; i++) {
            previousToInsert = previousToInsert.next;
        }

        Node<E> newNode = new Node<>(e);
        newNode.next = previousToInsert.next;
        previousToInsert.next = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        if (head == null && tail == null) {
            return null;
        }

        if (size == 1) {
            E data = head.data;
            clear();
            return data;
        }

        E data = head.data;
        Node<E> newHead = head.next;
        head.next = null;
        head = newHead;
        size--;

        return data;
    }

    @Override
    public E removeLast() {
        if (head == null && tail == null) {
            return null;
        }

        if (size == 1) {
            E data = head.data;
            clear();
            return data;
        }

        E data = tail.data;
        int indexOfPreLast = size - 2;
        Node<E> preLast = head;
        for (int i = 0; i < indexOfPreLast; i++) {
            preLast = preLast.next;
        }
        preLast.next = null;
        tail = preLast;
        size--;
        
        return data;
    }

    @Override
    public E remove(int index) {
        if (!(0 <= index && index <= size - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<E> previousToDelete = head;
        for (int i = 0; i < index - 1; i++) {
            previousToDelete = previousToDelete.next;
        }
        E data = previousToDelete.next.data;
        previousToDelete.next = previousToDelete.next.next;
        return data;
    }

    @Override
    public boolean contains(E e) {
        if (head == null && tail == null) {
            return false;
        }

        Node<E> pointer = head;
        if (pointer.data.equals(e)) {
            return true;
        }
        
        while (pointer.next != null) {
            pointer = pointer.next;
            if (pointer.data.equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(E e) {
        if (isEmpty()) {
            return -1;
        }

        if (head.data.equals(e)) {
            return 0;
        }

        Node<E> pointer = head;
        int index = 0;
        while (pointer.next != null) {
            pointer = pointer.next;
            index++;

            if (pointer.data.equals(e)) {
                return index;
            }
        }
        
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        if (isEmpty()) {
            return -1;
        }

        if (head.data.equals(e)) {
            return 0;
        }

        int lastIndex = -1;
        Node<E> pointer = head;
        int index = 0;
        while (pointer.next != null) {
            pointer = pointer.next;
            index++;

            if (pointer.data.equals(e)) {
                lastIndex = index;
            }
        }
        
        return lastIndex;
    }


    @Override
    public E set(int index, E e) {
        if (!(0 <= index && index <= size - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Node<E> pointer = head;
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }

        E data = pointer.data;
        pointer.data = e;

        return data;
    }
}

public class PA12A {
    public static void main(String[] args) {
        MyLL myLL = new MyLL(new Integer[]{0, 1, 2, 3, 4, 5, 6, 5, 8, 5, 9});
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
        System.out.println("myLL.contains(500): " + myLL.contains(500));
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
