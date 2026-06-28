/**
 * 단순 연결 리스트 구현
 *
 * head 측 더미노드를 사용하여 처리를 단순화함
 *
 * cur는 리스트가 비어있는 경우를 제외하면 항상 값을 가지는 노드를 가리킨다.
 *
 */

public class SinglyLinkedList implements LinkedListInterface{

    /**
     * 각 노드는 내부 클래스로 구현
     */
    private class Node {
        public Object value;
        public Node next;

        public Node(Object obj) {
            this.value = obj;
            this.next = null;
        }
    }

    private int length;
    private Node head; //맨 앞의 dummy node를 가리킴
    private Node tail;
    private Node cur;
    private Node before; //cur의 직전 노드

    {
        init();
    }

    /**
     * 리스트를 초기 상태로 리셋
     */
    private void init() {
        Node dummy = new Node(null);
        dummy.next = null;
        head = dummy;
        tail = dummy;
        cur = dummy;
        before = dummy;
        length = 0;
    }

    /**
     * 빈 리스트에 새로운 노드를 추가할 때 사용할 메소드
     *
     * @param obj 삽입할 데이터
     */
    private void insertFirstNode(Object obj) {
        Node newNode = new Node(obj);
        newNode.next = null;
        head.next = newNode;
        before = head;
        cur = newNode;
        length = 1;
        tail = newNode;
    }

    /**
     * 현재 위치(cur)에 데이터를 삽입
     *
     * 비어있던 리스트라면 첫 번째 노드로 추가된다.
     * 그 외의 경우 cur의 다음 위치에 삽입된 뒤 cur는 해당 노드를 가리키게 갱신된다.
     *
     * @param obj 삽입할 데이터
     */
    public void insertOnCur(Object obj) {
        //비어있을 경우
        if(isEmpty()) {
            insertFirstNode(obj);
            return;
        }

        //tail == cur인 경우 (노드가 1개뿐인 경우 포함)
        if(tail == cur) {
            insertOnTail(obj);
            before = cur;
            cur = cur.next;
            return;
        }

        Node newNode = new Node(obj);
        newNode.next = cur.next;
        cur.next = newNode;
        before = cur;
        cur = newNode;
        length++;


    }

    /**
     * 리스트의 시작(Head)에 데이터를 삽입한다.
     *
     * @param obj 삽입할 데이터 (기본형은 오토박싱됨)
     */
    public void insertOnHead(Object obj) {

        //비어있을 경우
        if(isEmpty()) {
            insertFirstNode(obj);
            return;
        }

        //비어있지 않은 경우
        Node newNode = new Node(obj);
        newNode.next = head.next;
        head.next = newNode;

        //before 위치 조정
        if(cur == newNode.next) {
            before = newNode;
        }

        length++;

    }

    /**
     * 리스트의 끝(Tail)에 데이터를 삽입한다.
     *
     * @param obj 삽입할 데이터 (기본형은 오토박싱됨)
     */
    public void insertOnTail(Object obj) {
        //비어있을 경우
        if(isEmpty()) {
            insertFirstNode(obj);
            return;
        }

        //비어있지 않은 경우
        Node newNode = new Node(obj);
        newNode.next = null;
        tail.next = newNode;
        tail = newNode;

        length++;

    }

    /**
     * 현재 위치(Cur)의 노드를 삭제한다.
     *
     * 삭제 후 Cur은 다음 노드를 가리키며, 다음 노드가 없으면 Head 다음 노드로 이동한다. (첫번째 노드)
     * 노드가 하나 뿐이었다면 init()이 호출되어 리셋된다.
     * 리스트가 비어있으면 null을 반환한다.
     *
     * @return 삭제된 노드의 데이터, 리스트가 비어있으면 null
     */
    public Object deleteOnCur() {

        //비어있는 경우
        if(isEmpty()) {
            return null;
        }

        Object tmp = cur.value;

        //마지막 노드를 삭제하는 경우
        if(length == 1) {
            init();
            return tmp;
        }

        //tail을 삭제하는 경우
        if(cur == tail) {
            tail = before;
            before.next = null;
            cur = head.next;
            before = head;

            length--;
            return tmp;
        }

        //tail이 아닌 경우
        cur = cur.next;
        before.next = cur;
        length--;
        return tmp;
    }

    /**
     * 리스트 시작(Head) 다음의 첫 번째 노드를 삭제한다. (더미노드 다음)
     *
     * Cur 또는 Before가 가리키던 노드가 삭제되었다면 함께 갱신된다.
     * (cur의 경우 맨 처음 노드를 가리키도록 갱신)
     *
     * 리스트가 비어있으면 null을 반환한다.
     *
     * @return 삭제된 노드의 데이터, 리스트가 비어있으면 null
     */
    public Object deleteOnHead() {

        //비어있는 경우
        if(isEmpty()) {
            return null;
        }
        Object tmp = head.next.value;

        //마지막 노드를 삭제하는 경우
        if(length == 1) {
            init();
            return tmp;
        }

        //마지막 노드가 아닌 경우

        //before와 겹치는 경우
        if(head.next == before) {
            before = head;
        }

        //cur와 겹치는 경우
        if(head.next == cur) {
            cur = head.next.next;
        }

        head.next = head.next.next;

        length--;
        return tmp;
    }
    /**
     * 리스트 끝(Tail)의 노드를 삭제한다.
     *
     * Tail을 새로 갱신하기 위해 필요한 시간 복잡도는 O(n)이다.
     * 리스트가 비어있으면 null을 반환한다.
     *
     * O(n) 성능 개선을 위해 양방향 연결 리스트 사용을 권장
     *
     * @return 삭제된 노드의 데이터, 리스트가 비어있으면 null
     */
    public Object deleteOnTail() {

        //비어있는 경우
        if (isEmpty()) {
            return null;
        }
        Object tmp = tail.value;

        //마지막 노드를 삭제하는 경우
        if (length == 1) {
            init();
            return tmp;
        }

        //마지막 노드가 아닌 경우

        //cur와 겹치는 경우
        if (tail == cur) {
            tail = before;
            tail.next = null;

            cur = head.next;
            before = head;

            length--;
            return tmp;
        }

        //cur와 겹치지 않는 경우
        //tail 직전 노드 찾기
        Node count = head;
        for (int i = 0; i < length-1; i++) {
            count = count.next;
        }
        tail = count;
        tail.next = null;


        length--;
        return tmp;
    }

    public int length() {
        return this.length;
    }

    /**
     * 현재 위치(Cur)의 데이터를 반환하고 Cur을 다음 노드로 이동시킨다.
     *
     * Cur의 다음 노드가 없으면 Cur은 이동하지 않는다.
     *
     * @return 현재 위치의 데이터
     */
    public Object getNextValue() {

        Object value = cur.value;

        //이동할 노드가 있을 경우만 cur 이동
        if(cur.next != null) {
            cur = cur.next;
            before = before.next;
        }

        return value;
    }

    public void curReset() {
        if(isEmpty()) {
            return;
        }
        cur = head.next;
        before = head;

    }

    public boolean isEmpty() {
        return length <= 0;
    }

    /**
     * 리스트의 모든 값들을 출력한다.
     *
     * cur는 변화시키지 않는다.
     */
    public void printList() {
        Node tmpNode = head;
        for(int i = 0; i < length;i++) {
            tmpNode = tmpNode.next;
            System.out.print(tmpNode.value.toString());
            if(i < length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
