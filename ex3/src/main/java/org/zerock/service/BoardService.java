package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {

	//목록 (BoardMapper.java가 서비스와 한 짝이라고 생각하면 됨.)
//	public List<BoardVO> getList();
	public List<BoardVO> getList(Criteria cri);
	
	//등록
	public void register (BoardVO board);
	
	//상세보기
	 public BoardVO get(Long bno);
	 
	//수정
	 public boolean modify(BoardVO board);
	 
	 //삭제
	 public boolean remove(Long bno);
	 
	 //데이터 개수 처리
	 public int getTotal(Criteria cri);
	 
	 //게시물 조회
	 public List<BoardAttachVO> getAttachList(Long bno);
	 
	 
}
