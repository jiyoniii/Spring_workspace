package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

//service ������̼��� ���������� �ַ� ����Ͻ� ������ ����ϴ� ��ü���� ǥ���ϱ� ���� ����.

@Log4j
@Service 
@AllArgsConstructor

public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;
	
	//���
	@Override
	public void register(BoardVO board) {
		log.info("register......"+board);
		
		//���߿� ������ �Խù��� ��ȣ�� Ȯ���� �� �ְ� ��
		mapper.insertSelectKey(board);
	}

	
	//���
	@Override
	public List<BoardVO> getList() {
		
		log.info("get List....");
		
		return mapper.getList();
	}
	
	
	//��ȸ
	//�з����ͷ� bno�� ����, return���δ� VO->mapper���� �۾��� read(bno)�� ������ ��.
	@Override
	public BoardVO get(Long bno) {

		log.info("get....."+bno);
		
		return mapper.read(bno);
	}

	
	//����&����
	//method�� ����Ÿ���� �����ϰ� ó���ϱ� ���� booleanŸ������ ó���ϰ�����.
	//����&������ ���������� �̷������ 1�̶�� ���� ��ȯ�Ǳ� ������ ==�� �̿��ؼ� true/false�� ������.
	//(����or������ �Ǹ� 1���� �����Ǳ� ������ ��ȯ���� ���� 1�� ��. ����or������ ���� �ʾҴٸ� ó���� �Խñ��� ���� ������ 0�� ��ȯ�ϰ� ��.)
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify...."+board);
		return mapper.update(board)==1;
	}

	@Override
	public boolean remove(Long bno) {
		
		log.info("remove...."+bno);
		return mapper.delete(bno) ==1 ;
	}

	

}
