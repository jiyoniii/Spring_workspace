package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;

public interface BoardMapper {
	//VO에서 가져온 자료들로 Mapper를 작성하게됨.
	
	
	//VO에서 가져온 글번호, 제목, 글쓴이 등등으로 아래 내용이 작성되게 됨.
	public List<BoardVO> getList();
	
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVO board);
	
}
