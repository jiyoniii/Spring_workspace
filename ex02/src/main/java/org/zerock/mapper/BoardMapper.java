package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	//getList();�� BoardMapper.xml�� id�� �Ȱ��ƾ� ��.
	//getList()�� ����� ����.
	//������θ� �ٸ���Ʈ�� �������̵� �ؾ�������, BoardMapper.xml���� id�� �Ȱ��� getList�� ���߾��� ������ ���� �������̽��� ����Ͽ� �������̵� �� �ʿ䰡 ����.
	public List<BoardVO> getList();
	
	//��� with paging
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//��ü�� ��
	public int getTotalCount(Criteria cri);
	
	//���
//	boardMapper.xml������ id�� �Ʒ��� ������ insert����.
	public void insert (BoardVO board);

	//���(bno �� ���� �˾Ƴ��� ����)
	public void insertSelectKey(BoardVO board);
	
	//�󼼺���
	public BoardVO read(Long bno);
	
	//����
	public int update (BoardVO board);
	
	//����
	public int delete (Long bno);
}
