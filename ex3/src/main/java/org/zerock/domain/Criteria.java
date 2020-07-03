package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum=pageNum;
		this.amount=amount;
	}
	
//	split(자르기) 해주는 함수
//	type은 T,C,W를 뜻함.
	public String[] getTypeArr() {
		return type == null? new String[] {}: type.split(""); //split에 구분자가 없으면 한글자씩 자르게 됨.
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type",this.getType())
				.queryParam("keyword", this.getKeyword());
		
		return builder.toUriString();
	}
}
