package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;

public interface BoardService {

	//목록 (BoardMapper.java가 서비스와 한 짝이라고 생각하면 됨.)
	public List<BoardVO> getList();
	
	//등록
	public void register (BoardVO board);
	
	//상세보기
	 public BoardVO get(Long bno);
}
