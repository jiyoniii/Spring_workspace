console.log("Reply Module........");

//자동호출 함수 function아래로 만들어서 바로 호출
var replyService = (function(){
	
	//등록
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

	//시간출력 함수
		function displayTime(timeValue){
			var today = new Date();
			var gap = today.getTime() - timeValue;  //시간은 되도록이면 서버시간으로 써야 함. 얘는 js에서 시간을 구하고 있음.
			
			var dateObj = new Date(timeValue);
			var str = "";
			
			if (gap < (1000 * 60 * 60 *24)){
				var hh = dateObj.getHours();
				var mi = dateObj.getMinutes();
				var ss = dateObj.getSeconds();
				
				return [ (hh>9 ? '' : '0') + hh, ':', (mi > 9? '' : '0' ) +mi, ':', (ss>9?'':'0') + ss].join('');
			}else {
				var yy = dateObj.getFullYear();
				var mm = dateObj.getMonth() + 1; 
				var dd = dateObj.getDate();
				
				return [yy, '/', (mm > 9 ? '' : '0' ) + mm, '/', (dd>9 ? '' : '0') + dd].join('');
			}
		};
		
	
		//댓글조회
		function get(rno, callback, error){
			$.get("/replies/"+rno+".json",function(result){
				if(callback){
					callback(result);
				}
			}).fail(function(xhr, status, err){
				if(error){
					error();
				}
			});
		}
		
		
		//목록
		function getList(param, callback, error){
				var bno = param.bno;
				var page = param.page || 1;
				
				$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
						function(data){
							if(callback){
								callback(data.replyCnt, data.list); 
							}
				}).fail(function(xhr, status, err){
					if(error){
						error();
					}
				});
			}
		
		//수정
		function update(reply, callback, error){
			console.log("RNO: "+reply.rno);
			
			$.ajax({
				type:'put',
				url : '/replies/' + reply.rno,
				data: JSON.stringify(reply),
				contentType:"application/json; charset=utf-8",
				success : function (result, status, xhr){
					if(callback){
						callback(result);
					}
				},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
			});
		}
		
		//삭제
		function remove(rno, callback, error){
			$.ajax({
				type:'delete',
				url : '/replies/'+rno,
				success : function(deleteResult, status, xhr){
					if(callback){
						callback(deleteResult);
					}
				},
				error : function(xhr, status, er){
					if(error){
						error(er);
					}
				}
			});
		}
		
		
		return{
			add:add,
			getList:getList,
			displayTime : displayTime,
			get : get,
			update : update,
			remove : remove
		};
	})();




//위와 동일한 방식으로 하자면 아래와 같음
//
//var replyService={
//		add:function(){
//						
//		},
//		getList:function(){
//			
//		}, ....
//};
//
//위의 함수를 호출할 때 
//replyaService.add();
