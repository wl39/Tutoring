public class MyLinkedList {
    int value;
    MyLinkedList next;

    MyLinkedList(int value) {
        this.value = value;
        this.next = null;
    }

    void add(int nextValue) {
        this.next = new MyLinkedList(nextValue);
    }

    int getValue() {
        return this.value;
    }

    MyLinkedList getNext() {
        return this.next;
    }
}
