import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SamLinkedList<T> implements Iterable<T> {

    private Node<T> head, tail;

    @Override
    public Iterator<T> iterator() {
        return new SamIterator<>();
    }

    public SamLinkedList() {

    }

    public void add(T data) {
        Node<T> node = new Node<>(data, null);
        if(head == null) {
            tail = head = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
    }

    private class SamIterator<T> implements Iterator<T> {

        private Node cursor = null;
        private Node next = head;
        private Node previous = null;

        @Override
        public boolean hasNext() {
            return !Objects.isNull(next);
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();
            T data = (T) next.getData();
            if(!Objects.isNull(cursor))
                previous = cursor;
            cursor = next;
            next = next.getNext();
           return data;
        }

        @Override
        public void remove() {
            if(Objects.isNull(cursor)) throw new NoSuchElementException();
            if(head.equals(tail)) {
                head = null;
                tail = null;
                return;
            }
            if(Objects.isNull(previous)) {
                head = cursor.getNext();
                next = head.getNext();
                return;
            }
            previous.setNext(cursor.getNext());
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            if(!Objects.isNull(cursor.getNext())) {
                action.accept((T) cursor.getNext().getData());
            }
            while(hasNext()) {
                action.accept(next());
            }
        }
    }

    public int size() {
        AtomicInteger i = new AtomicInteger();
        forEach(s -> i.getAndIncrement());
        return i.get();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            action.accept(it.next());
        }
    }

    public boolean contains(T data) {
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            if(it.next().equals(data))
                return true;
        }
        return false;
    }

    public void remove(T data) {
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            if(it.next().equals(data)) {
                it.remove();
                return;
            }
        }
    }

    private class Node<T> {

        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public boolean hasNext() {
            return next != null;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

    }

}
