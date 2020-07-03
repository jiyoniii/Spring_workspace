package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j //롬복과 관련된 어노테이션
@Service  //Inpl.java에서 이 어노테이션 걸지 않으면, 500번 에러 걸림.
@AllArgsConstructor //이걸 넣어야 아래코드에 자동주입이 됨. 롬복과 관련
public class BoardServiceImpl implements BoardService{

	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper; //자동주입
	
	@Setter(onMethod_=@Autowired)
	private BoardAttachMapper attachMapper;
	
//	@Override
//	public List<BoardVO> getList() {
//		
//		log.info("getList.............");
//		
//		return mapper.getList();
//	}

	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("register...."+board);
		
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		//첨부파일이 insert 되는 부분
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get..."+bno);
		
		return mapper.read(bno);
	}

	/* 수정 */
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify....."+ board );
		
		attachMapper.deleteAll(board.getBno());
		
		boolean modifyResult = mapper.update(board) ==1;
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
			
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
		
		return modifyResult;
	}

	/* 삭제 */
	@Transactional  //첨부파일 삭제와 실제 게시물의 삭제가 같이 처리되도록 해야해서 transactional 어노테이션 추가
	@Override
	public boolean remove(Long bno) {
		log.info("remove...."+bno);
		
		//첨부파일 모두 삭제
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList with criteria: " + cri);
		
		
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("get total count");
		
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);
		
		return attachMapper.findByBno(bno);
	}
	
	
	

	
}
