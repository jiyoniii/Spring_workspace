package org.zerock.domain;

import lombok.Data;

@Data
//attachfileDTO�� ������ ������ �ϴµ� �ߺ��Ǿ��ִ� �ų� ����������.
public class BoardAttachVO {

	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean filetype;
	private Long bno;
}
