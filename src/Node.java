import java.util.Objects;

public class Node<T> {
    private T data;
    private Node<T> head;
    private Node<T> tail;

    public Node(T data) {
        this.data = data;
        this.head = null;
        this.tail = null;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getHead() {
        return this.head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return this.tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(data, node.data) && Objects.equals(head, node.head) && Objects.equals(tail, node.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, head, tail);
    }
}
