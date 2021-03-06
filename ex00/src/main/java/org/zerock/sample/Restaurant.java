package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
@Data
public class Restaurant {
	//주입하는 코드임. (롬복에서 사용됨)
	@Setter(onMethod_ = @Autowired)
	private Chef chef;
	//롬복이 getter,setter를 자동생성 해줌.
}
