package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;

//BoardMapper�� ���۾� �Ǿ�� ��.

//BoardService�� �Ʒ� impl�� �������� ������ ���븸 �ۼ����� ��.
//������ ���� �ڵ���� impl���� ���� Ŭ������ ��ӹ޾� ����ϰ� ��.
public interface BoardService {

	//����ϱ�
	public void register (BoardVO board);
	
	//�� ���� �˻�
	public BoardVO get(Long bno);
	
	//����
	public boolean modify(BoardVO board);
	
	//����
	public boolean remove (Long bno);
	
	//���
	//��ü ����Ʈ�� ���ϴ� getList() ->ó������ �޼����� ����Ÿ���� �����ؼ� �����ϰ� ��.
	public List<BoardVO> getList();
}
