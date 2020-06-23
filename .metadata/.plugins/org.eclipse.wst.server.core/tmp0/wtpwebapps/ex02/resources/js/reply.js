console.log("Reply Module........");

//자동호출 함수 function아래로 만들어서 바로 호출
var replyService = (function(){
	
	function add(reply, callback,error){
		console.log("add reply......");
		
		
		$.ajax({
			type:"post",
			url : "/replies/new",
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8", //컨트롤러에서 에서도 json으로 보낸다고 했기 때문에 맞춤.
			success : function(result, status, xhr){
				if(callback){
					callback(result);
				}
			},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		})
	}

		
		//목록
		function getList(param, callback, error){
				var bno = param.bno;
				var page = param.page || 1;
				$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
						function(data){
							if(callback){
								callback(data);
							}
				}).fail(function(xhr, status, err){
					if(error){
						error();
					}
				});
			}
		
		return{
			add:add,
			getList:getList
		};
	})();




//위와 동일한 방식으로 하자면 아래와 같음
//
//var replyService={
//		add:function(){
//						
//		}
//}
//
//위의 함수를 호출할 때 
//replyaService.add();
