package org.zerock.domain;

import java.util.Date;

import lombok.Data;


@Data //getter, setter ������ ���� lombok ������̼�
public class BoardVO {
	
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
}
