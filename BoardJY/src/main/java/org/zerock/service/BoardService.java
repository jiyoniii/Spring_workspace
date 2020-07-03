package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;

//BoardMapper가 선작업 되어야 함.

//BoardService는 아래 impl을 쓰기위해 간단한 내용만 작성됐을 뿐.
//구현을 위한 코드들은 impl에서 여기 클래스를 상속받아 사용하게 됨.
public interface BoardService {

	//등록하기
	public void register (BoardVO board);
	
	//한 건을 검색
	public BoardVO get(Long bno);
	
	//수정
	public boolean modify(BoardVO board);
	
	//삭제
	public boolean remove (Long bno);
	
	//목록
	//전체 리스트를 구하는 getList() ->처음부터 메서드의 리턴타입을 결정해서 진행하게 됨.
	public List<BoardVO> getList();
}
