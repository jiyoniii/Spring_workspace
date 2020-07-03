package org.zerock.domain;

import lombok.Data;

@Data
//attachfileDTO와 동일한 역할을 하는데 중복되어있는 거나 마찬가지임.
public class BoardAttachVO {

	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean filetype;
	private Long bno;
}
