package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	//getList();는 BoardMapper.xml의 id와 똑같아야 함.
	//getList()는 목록을 뜻함.
	//본래대로면 겟리스트를 오버라이딩 해야하지만, BoardMapper.xml에서 id를 똑같이 getList로 맞추었기 때문에 따로 인터페이스를 상속하여 오버라이딩 할 필요가 없음.
	public List<BoardVO> getList();
	
	//목록 with paging
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//전체글 수
	public int getTotalCount(Criteria cri);
	
	//등록
//	boardMapper.xml에서의 id가 아래와 동일한 insert였음.
	public void insert (BoardVO board);

	//등록(bno 의 값을 알아내기 위한)
	public void insertSelectKey(BoardVO board);
	
	//상세보기
	public BoardVO read(Long bno);
	
	//수정
	public int update (BoardVO board);
	
	//삭제
	public int delete (Long bno);
}
