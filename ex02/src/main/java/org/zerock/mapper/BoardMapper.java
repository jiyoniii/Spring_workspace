package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;

public interface BoardMapper {
	//getList();는 BoardMapper.xml의 id와 똑같아야 함.
	//getList()는 목록을 뜻함.
	public List<BoardVO> getList();
	
}
