package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

//service 어노테이션은 계층구조상 주로 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용됨.

@Log4j
@Service 
@AllArgsConstructor

public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;
	
	//등록
	@Override
	public void register(BoardVO board) {
		log.info("register......"+board);
		
		//나중에 생성된 게시물의 번호를 확인할 수 있게 함
		mapper.insertSelectKey(board);
	}

	
	//목록
	@Override
	public List<BoardVO> getList() {
		
		log.info("get List....");
		
		return mapper.getList();
	}
	
	
	//조회
	//패러미터로 bno가 오고, return으로는 VO->mapper에서 작업된 read(bno)가 리턴이 됨.
	@Override
	public BoardVO get(Long bno) {

		log.info("get....."+bno);
		
		return mapper.read(bno);
	}

	
	//수정&삭제
	//method의 리턴타입을 엄격하게 처리하기 위해 boolean타입으로 처리하고있음.
	//수정&삭제가 정상적으로 이루어지면 1이라는 값이 반환되기 때문에 ==를 이용해서 true/false를 구분함.
	//(수정or삭제가 되면 1건이 삭제되기 때문에 반환값이 숫자 1이 됨. 삭제or수정이 되지 않았다면 처리된 게시글이 없기 때문에 0을 반환하게 됨.)
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
