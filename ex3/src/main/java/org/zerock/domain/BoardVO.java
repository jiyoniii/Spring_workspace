package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	private Long bno; //long�� ������.
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	private int replyCnt;
	
	//���� VO�� DB�� table�� match�ؾ� �ϴ°� �´µ�, �Ʒ� list�� ��� ���ܻ������� �����ڵ� ���̿��� �ǰ��� ����.
	//�׷��� ÷������ ����Ʈ(Attachlist)�� BoardVO�� ��Ƽ� �Ѱ���� ��.
	private List<BoardAttachVO>attachList;
	
}
