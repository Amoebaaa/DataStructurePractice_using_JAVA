/**
 * 연결 리스트의 기본 동작을 정의하는 인터페이스.
 *
 * 단순 연결 리스트, 원형 연결 리스트, 양방향 연결 리스트 등
 * 다양한 연결 리스트 구현체에서 공통으로 사용
 */
public interface LinkedListInterface {

    /**
     * 현재 위치(cur)에 데이터를 삽입한다.
     *
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnCur(Object obj);

    /**
     * 리스트의 시작(head)에 데이터를 삽입한다.
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnHead(Object obj);

    /**
     * 리스트의 끝(tail)에 데이터를 삽입한다.
     * @param obj 삽입할 데이터, 기본형은 오토박싱되어 지원
     */
    public void insertOnTail(Object obj);


    /**
     * 현재 위치(cur)의 데이터를 삭제하고 반환한다.
     * @return 삭제된 데이터 value
     */
    public Object deleteOnCur();

    /**
     * 리스트의 시작(head)의 데이터를 삭제하고 반환한다.
     * @return 삭제된 데이터 value
     */
    public Object deleteOnHead();

    /**
     * 리스트의 끝(tail)의  데이터를 삭제하고 반환한다.
     * @return 삭제된 데이터 value
     */
    public Object deleteOnTail();

    /**
     * 저장된 데이터 개수를 반환한다.
     * @return 저장된 노드의 개수
     */
    public int length();

    /**
     * 현재 위치(cur)의 데이터를 반환하고 cur를 진행시킨다.
     * @return 현재 위치의 데이터
     */
    public Object getNextValue();

    /**
     * cur위치를 리스트 시작으로 초기화
     */
    public void curReset();

    /**
     *리스트가 비어있는지 여부를 반환한다.
     * @return 리스트가 비어있으면 true, 아니면 false
     */
    public boolean isEmpty();

    /**
     *리스트의 모든 데이터를 출력한다.
     */
    public void printList();

}
