package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;

public interface BoardMapper {
	//getList();�� BoardMapper.xml�� id�� �Ȱ��ƾ� ��.
	//getList()�� ����� ����.
	public List<BoardVO> getList();
	
}
