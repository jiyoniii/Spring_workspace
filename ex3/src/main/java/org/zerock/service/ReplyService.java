package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

public interface ReplyService {
	//등록
	public int register (ReplyVO vo);
	
	public ReplyVO get(Long rno);
	
	public int modify (ReplyVO vo);

	//목록 list를 replyPageDTO로 변경.
//	public List<ReplyVO> getList(Criteria cri, Long bno);
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
	
	//삭제
	public int remove(Long rno);
	

}
