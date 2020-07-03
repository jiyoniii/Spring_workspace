package org.zerock.domain;

import java.util.Date;

import lombok.Data;


@Data //getter, setter 생성을 위한 lombok 어노테이션
public class BoardVO {
	
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
}
