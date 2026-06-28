/**
 * 원형 연결 리스트 구현
 *
 * 마지막 노드를 가리키는 tail만을 사용해서 리스트를 관리
 * 더미노드를 사용하지 않는다.
 *
 */

public class CircularLinkedList implements LinkedListInterface{

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
        length = 0;
        tail = null;
        cur = null;
        before = null;
    }

    /**
     * 빈 리스트에 새로운 노드를 추가할 때 사용할 메소드
     *
     * @param obj 삽입할 데이터
     */
    private void insertFirstNode(Object obj) {

        //새로운 노드 생성 및 초기화
        Node newNode = new Node(obj);
        newNode.next = newNode;

       //인스턴스 변수들 초기화
       length = 1;
       tail = newNode;
       cur = newNode;
       before = newNode;

       return;
    }

    /**
     * 원형 리스트의 특성 상 head, tail측에 새로운 노드 삽입 시 공통된 작업을 수행한다.
     * 이를 수행하는 메소드
     * head측 노드 삽입 기준으로 작성되었다.
     * 따라서 tail측 삽입 시 호출 이후 tail이 가리키는 노드를 조정해주어야한다.
     * @param obj 삽입할 데이터
     */
    private void insertOnHeadOrTail(Object obj) {
        //비어있는 노드인 경우
        if (isEmpty()) {
            insertFirstNode(obj);
            return;
        }

        //비어있지 않은 경우
        //새로운 노드 생성 및 초기화
        Node newNode = new Node(obj);
        newNode.next = tail.next;

        //그 외 값 조정
        tail.next = newNode;

        //cur가 맨 처음 노드를 가리켰다면 before를 조정한다.
        if (cur == newNode.next) {
            before = newNode;
        }

        length++;
    }

    /**
     * cur가 기리키는 위치에 새로운 노드를 추가한다.
     * 기존에 가리키던 노드는 before가 되며 cur는 새로운 노드를 가리키게 된다.
     *
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnCur(Object obj) {
        //비어있는 노드인 경우
        if (isEmpty()) {
            insertFirstNode(obj);
            return;
        }

        //비어있지 않은 경우
        //새로운 노드 생성 및 초기화
        Node newNode = new Node(obj);
        newNode.next = cur.next;

        //그 외 값 조정
        before = cur;
        before.next = newNode;
        cur = newNode;

        length++;
    }


    /**
     * head 측에 새로운 노드를 삽입한다.
     * cur는 기존에 가리키는 노드를 그대로 가리킨다.
     * 만약 cur가 맨 첫 노드를 가리켰다면 before는 새로 추가되는 노드를 가리키게된다.
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnHead(Object obj) {
        insertOnHeadOrTail(obj);
    }

    /**
     * tail 측에 새로운 노드를 삽입한다.
     * cur는 기존에 가리키는 노드를 그대로 가리킨다.
     * 만약 cur가 맨 첫 노드를 가리켰다면 before는 새로 추가되는 노드를 가리키게된다.
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnTail(Object obj) {
        insertOnHeadOrTail(obj);
        tail = tail.next;
    }

    /**
     * 원형 리스트의 특성 상 head, tail측 노드 삭제 시 공통된 작업을 수행한다.
     * 이를 수행하는 메소드
     *
     * head측 노드 삭제를 기준으로 작성되었다.
     * 따라서 tail측 노드 삭제를 원한다면 호출 전에 tail 위치를 조정해야한다.
     *
     * @return 삭제된 데이터
     */
    private Object deleteOnHeadOrTail() {

        //비어있는 경우
        if(isEmpty()) {
            return null;
        }
        Object tmp = tail.next.value;

        //노드가 하나 뿐인 경우
        if(length == 1) {
            init();
            return tmp;
        }

        /*
        1) cur 또는 before가 삭제되는 노드를 가리킬 시 재조정
        2) tail.next 재조정
         */

        //1)
        if(cur == tail.next) {
            //cur가 가리키는 노드가 삭제 될 시 cur는 그 다음 노드를 가리킨다.
            cur = cur.next;
            before = tail;
        }
        else if(before == tail.next) {
            //before가 가리키는 노드가 삭제 될 시 before는 이전 노드를 가리킨다. (cur 직전 노드)
            before = tail;
        }


        //2)
        tail.next = tail.next.next;
        length--;

        return tmp;
    }


    /**
     * cur가 가리키는 노드를 삭제한다.
     * cur는 삭제된 노드의 다음 노드를 가리키게 된다.
     *
     * @return 삭제된 데이터
     */
    public Object deleteOnCur() {

        //비어있는 경우
        if(isEmpty()) {
            return null;
        }


        //삭제하려는 노드가 tail측일 경우 (노드가 하나 뿐인 경우 포함)
        if(cur == tail) {
            tail = before; //tail 위치 조정
            return deleteOnHeadOrTail();
        }

        Object tmp = cur.value;

        cur = cur.next;
        before.next = cur;

        length--;

        return tmp;

    }

    /**
     * head측의 노드를 삭제한다.
     * cur가 head를 가리켰다면 새로운 head를 가리키도록 재조정된다.
     * @return 삭제된 데이터
     */
    public Object deleteOnHead() {
        return deleteOnHeadOrTail();
    }

    /**
     * tail측의 노드를 삭제한다.
     * cur가 tail을 가리켰다면 head를 가리키도록 재조정된다.
     * 단방향 리스트 특성 상 tail을 재지정하기 위해 O(n)의 시간 복잡도가 필요하다.
     * 따라서 deleteOnCur 또는 deleteOnHead 사용을 권장한다.
     * @return 삭제된 데이터
     */
    public Object deleteOnTail() {
        //2개 이상의 노드가 있는 경우에만 tail 위치를 재조정
        if(length >= 2) {
            Node tmp = tail.next;
            for(int i = 0 ; i < length-2;i++) {
                tmp = tmp.next;
            }
            tail = tmp;
        }

        return deleteOnHeadOrTail();
    }


    public int length() {
        return this.length;
    }

    /**
     * 현재 cur가 가리키는 노드의 값을 반환한뒤 cur는 다음 노드로 이동한다.
     * before 또한 갱신된다.
     *
     * @return 비어있다면 null, 그렇지 않다면 노드의 값을 반환
     */
    public Object getNextValue() {
        //비어있을 시
        if(isEmpty()) {
            return null;
        }

        Object tmp = cur.value;
        before = cur;
        cur = cur.next;
        return tmp;
    }

    public void curReset() {
        if(isEmpty()) {
            return;
        }
        before = tail;
        cur = tail.next;
    }

    public boolean isEmpty() {
        return length <= 0;
    }

    public void printList() {
        if(isEmpty()) {
            System.out.println("Empty");
            return;
        }
        Node tmp = tail.next;
        for(int i = 0 ; i < length;i++) {
            System.out.print(tmp.value);
            tmp = tmp.next;
            if(i < length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
