package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	private Long bno; //long은 정수형.
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	private int replyCnt;
	
	//본래 VO는 DB의 table과 match해야 하는게 맞는데, 아래 list의 경우 예외사항으로 개발자들 사이에서 의견이 많음.
	//그래서 첨부파일 리스트(Attachlist)는 BoardVO에 담아서 넘겨줘야 함.
	private List<BoardAttachVO>attachList;
	
}
