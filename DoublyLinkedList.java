/**
 * 양방향 연결 리스트 구현
 * <p>
 * head와 tail은 더미노드를 가리킨다.
 * <p>
 * cur는 노드가 없지 않다면 항상 특정 값을 가지는 노드를 가리킨다.
 */

public class DoublyLinkedList implements LinkedListInterface {

    /**
     * 각 노드는 내부 클래스로 구현
     */
    private class Node {
        public Object value;
        public Node next;
        public Node prev;

        public Node(Object obj) {
            this.value = obj;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node head;
    private Node tail;
    private Node cur;

    {
        init();
    }

    /**
     * 리스트를 초기 상태로 리셋
     */
    private void init() {
        head = new Node(null);   //head 측 더미노드 형성
        tail = new Node(null);   //tail 측 더미노드 형성
        head.next = tail;
        tail.prev = head;
        length = 0;
        cur = head;
    }

    /**
     * 현재 위치(cur)에 데이터를 삽입한다.
     * <p>
     * cur의 다음 위치에 새로운 노드가 삽입되며 cur는 해당 노드를 가리키게 된다.
     *
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnCur(Object obj) {
        //비어있는 경우
        if (isEmpty()) {
            insertOnHead(obj);
            return;
        }

        Node newNode = new Node(obj);
        newNode.next = cur.next;
        newNode.prev = cur;

        cur.next = newNode;
        newNode.next.prev = newNode;

        cur = newNode;
        length++;

    }

    /**
     * 리스트의 시작(head)에 데이터를 삽입한다.
     *
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnHead(Object obj) {
        Node newNode = new Node(obj);
        newNode.next = head.next;
        newNode.prev = head;

        head.next = newNode;
        newNode.next.prev = newNode;

        //첫 노드라면 cur 조정
        if (isEmpty()) {
            cur = newNode;
        }

        length++;
    }

    /**
     * 리스트의 끝(tail)에 데이터를 삽입한다.
     *
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnTail(Object obj) {
        Node newNode = new Node(obj);
        newNode.next = tail;
        newNode.prev = tail.prev;

        tail.prev.next = newNode;
        tail.prev = newNode;

        //첫 노드라면 cur 조정
        if (isEmpty()) {
            cur = newNode;
        }

        length++;
    }

    /**
     * 현재 위치(cur)의 데이터를 삭제하고 반환한다.
     *
     * cur는 다음 노드를 가리킨다. (tail을 가리킨다면 이전노드를 가리키게 된다.)
     *
     * @return 삭제된 데이터 value
     */
    public Object deleteOnCur() {

        if (isEmpty()) {
            return null;
        }

        if(cur == head.next) {
            return deleteOnHead();
        }
        if(cur == tail.prev) {
            return deleteOnTail();
        }

        Object tmp = cur.value;

        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        cur = cur.next;
        length--;
        return tmp;
    }

    /**
     * 리스트의 시작(head)의 데이터를 삭제하고 반환한다.
     * cur가 가리킨 노드가 삭제된 경우 cur는 다음 노드를 가리킨다.
     *
     * @return 삭제된 데이터 value
     */
    public Object deleteOnHead() {
        if (isEmpty()) {
            return null;
        }

        Object tmp = head.next.value;
        if(cur == head.next) {
            cur = head.next.next;
        }

        head.next = head.next.next;
        head.next.prev = head;

        length--;

        return tmp;
    }

    /**
     * 리스트의 끝(tail)의  데이터를 삭제하고 반환한다.
     * cur가 가리킨 노드가 삭제된 경우 cur는 이전 노드를 가리킨다.
     *
     * @return 삭제된 데이터 value
     */
    public Object deleteOnTail() {
        if (isEmpty()) {
            return null;
        }

        Object tmp = tail.prev.value;
        if(cur == tail.prev) {
            cur = tail.prev.prev;
        }
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;

        length--;

        return tmp;
    }

    /**
     * 저장된 데이터 개수를 반환한다.
     *
     * @return 저장된 노드의 개수
     */
    public int length() {
        return length;
    }

    /**
     * 현재 위치(cur)의 데이터를 반환하고 cur를 진행시킨다.
     * 다음 노드가 없을 경우 cur는 진행하지 않는다.
     *
     * @return 현재 위치의 데이터
     */
    public Object getNextValue() {

        if (isEmpty()) {
            return null;
        }

        Object tmp = cur.value;
        if (cur.next.next != null) {  //다음 노드가 더미노드가 아닌 경우
            cur = cur.next;
        }
        return tmp;
    }

    /**
     * cur위치를 리스트 시작으로 초기화
     */
    public void curReset() {
        if (isEmpty()) {
            return;
        }
        cur = head.next;
    }

    /**
     * 리스트가 비어있는지 여부를 반환한다.
     *
     * @return 리스트가 비어있으면 true, 아니면 false
     */
    public boolean isEmpty() {
        return length <= 0;
    }

    /**
     * 리스트의 모든 데이터를 출력한다.
     */
    public void printList() {
        if (isEmpty()) {
            System.out.println("Empty");
            return;
        }
        Node tmp = head.next;
        for (int i = 0; i < length; i++) {
            System.out.print(tmp.value);
            tmp = tmp.next;
            if (i < length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
