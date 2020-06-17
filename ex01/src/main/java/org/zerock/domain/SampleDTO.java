package org.zerock.domain;

import lombok.Data;

@Data //@Data는 롬복으로, 자동으로 getter, setter를 생성해주게 됨
public class SampleDTO {

	private String name;
	private int age;
	
}
