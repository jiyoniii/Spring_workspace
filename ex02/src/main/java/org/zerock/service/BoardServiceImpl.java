package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j //롬복과 관련된 어노테이션
@Service
@AllArgsConstructor //이걸 넣어야 아래코드에 자동주입이 됨. 롬복과 관련
public class BoardServiceImpl implements BoardService{

	private BoardMapper mapper; //자동주입
	
	@Override
	public List<BoardVO> getList() {
		
		log.info("getList.............");
		
		return mapper.getList();
	}

	@Override
	public void register(BoardVO board) {
		log.info("register...."+board);
		
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get..."+bno);
		
		return mapper.read(bno);
	}
	
}
