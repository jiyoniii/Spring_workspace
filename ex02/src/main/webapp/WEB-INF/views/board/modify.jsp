<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<%@ include file="../includes/header.jsp" %>

	<style>
	
		.uploadResult {
			width:100%;
			background-color:gray;
		}
	
		.uploadResult ul{
			display:flex;
			flex-flow:row;
			justify-content: center;
			align-items: center;
		}
		
		.uploadResult ul li{
			list-style : none;
			padding:10px;
			align-content: center;
			text-align : center;
		}
	
		.uploadResult ul li img{
			width : 100px;
		}
		
		.uploadResult ul li span{
			color:white;
		}
		
		.bigPictureWrapper{
			position :absolute;
			display: none;
			justify-content: center;
			align-items: center;
			top :0%;
			width:100%;
			height:100%;
			background-color : gray;
			z-index : 1000;
			background:rgba(255,255,255,0.5);
		}
		
		.bigPicture{
			position : relative;
			display: flex;
			justify-content : center;
			align-items: center;
		}
		
		.bigPicture img{
			width : 600px;
		}
	
	</style>



	<!-- 수정,삭제,목록 버틑에 따라 다르게 이동할 수 있도록 js를 작성 -->
	<script>
		$(document).ready(function(){
			
			$(".uploadResult").on("click", "button", function(e){
				console.log("delete file");
				
				if(confirm("Remove this file? ")){
					var targetLi = $(this).closest("li");
					targetLi.remove();
				}
			});
			
			
			(function(){
				var bno = '<c:out value ="${board.bno}"/>';
				
				$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
					console.log(arr);
					
					var str = "";
					
					$(arr).each(function(i, attach){
						
						//image type
						
						if(attach.filetype){
							var fileCallPath = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);
							
							str += "<li data-path='"+attach.uploadPath+"' data-uuid = '"+attach.uuid+"' "
							str += "data-filename='"+attach.fileName+"' data-type='"+attach.filetype+"'><div>";
							str += "<span>" +attach.fileName+"</span><br/>";
							str += "<button type='button' data-file=\'"+fileCallPath+"\'data-type='image'"
							str += "class='btn btn-warning btn-circle'> <i class='fa fa-times'></i></button><br>";
							str += "<img src ='/display?fileName="+fileCallPath+"'>";
							str += "</div>";
							str + "</li>";
						}else{
							str += "<li data-path='"+attach.uploadPath+"' data-uuid = '"+attach.uuid+"' "
							str += "data-filename='"+attach.fileName+"' data-type='"+attach.filetype+"'><div>";
							str += "<span>" +attach.fileName+"</span><br/>";
							str += "<button type='button' data-file=\'"+fileCallPath+"\'data-type='file'"
							str += "class='btn btn-warning btn-circle'> <i class='fa fa-times'></i></button><br>";
							str += "<img src='/resources/img/attach.png'></a>";
							str += "</div>";
							str + "</li>";
							
						}
					});
					$(".uploadResult ul").html(str);
				})
			})();
			
			
			
			var formObj = $("form");
			$('button').on("click", function(e){
				e.preventDefault();
				
				var operation = $(this).data("oper");
				
				console.log(operation);
				
				if(operation === 'remove'){
					formObj.attr("action", "/board/remove");
					
				}else if (operation === 'list'){
					//move to list
					/* 메소드가 post인것을 get으로 바꾸겠단 소리! */
					formObj.attr("action","/board/list").attr("method","get");
					
					//clone부터 append 까지 코드 설명..백업을 만들어놓고 지운다음에 다시  4개만 추가해서 전송하겠다! 라는 뜻.
					
					//필요한 것 먼저 백업.(clone())
					var pageNumTag= $("input[name='pageNum']").clone();
					var amountTag= $("input[name='amount']").clone();
					var keywordTag= $("input[name='keyword']").clone();
					var typeTag= $("input[name='type']").clone();
					//입력항목 전부 삭제
					formObj.empty();
					//필요한 네가지 다시 추가.
					formObj.append(pageNumTag);
					formObj.append(amountTag);
					formObj.append(keywordTag);
					formObj.append(typeTag);
					
				//게시물 수정하는 이벤트처리 
				}else if(operation === 'modify'){ //수정버튼을 눌렀을 때
					console.log("submit clicked");
					var str ="";
					$(".uploadResult ul li").each(function(i,obj){
						var jobj=$(obj);
						console.dir(jobj);
						
						str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
						str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
						str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
						str += "<input type='hidden' name='attachList["+i+"].filetype' value='"+jobj.data("type")+"'>";
					});
					formObj.append(str).submit();					
				}
				formObj.submit();
			});			
		});
		
		
		////////////////////////첨부파일 추가
		var regex= new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize= 5*1024*1024; //5mb
		
		function checkExtension(fileName, fileSize){
		   
		   if(fileSize >= maxSize){
		      alert("파일 사이즈 초과");
		      return false;
		   }
		   if(regex.test(fileName)){
		      alert("해당 종류의 파일은 업로드 할 수 없습니다.");
		      return false;
		   }
		   return true;
		}
		
		$("input[type='file']").change(function(e){
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			
			for(var i=0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				formData.append("uploadFile",files[i]);
			}
			$.ajax({
				url : '/uploadAjaxAction',
				processData : false,
				contentType : false, 
				data: formData,
				type:'POST',
				dataType:'json',
				success:function(result){
					console.log(result);
				showUploadResult(result); //업로드 결과 처리함수
				}
			});
			
		})
		
		//업로드된 결과를 화면에 섬네일 만들어 처리하는 부분
		function showUploadResult(uploadResultArr){
			if(!uploadResultArr || uploadResultArr.length == 0){return;}
			
			var uploadUL = $(".uploadResult ul");
			
			var str="";
			
			$(uploadResultArr).each(function(i, obj){
				//image type. image파일이 아니면 attach.png가 출력되게
					   
					   if(obj.image){
						   
						   //str += "<li>" + obj.fileName + "</li>";
						   var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_"+obj.uuid+"_"+obj.fileName);
						  str += "<li data-path='"+obj.uploadPath+"'";
						  str += "data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"'data-type='"+obj.image+"'" 
						  str += "><div>";
						  str += "<span>"+obj.fileName+"</span>";
						  str += "<button type='button' data-file=\'"+fileCallPath+"\'"
						  str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						  str += "<img src='/display?fileName="+fileCallPath+"'>";
						  str += "</div>";
						  str +"</li>";
					   }else{
						   var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);			  
						    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
							
							str += "<li "
							str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'><div>";
							str += "<span> "+obj.fileName+"</span>";
							str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file'"
							str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
							str += "<img src='/resources/img/attach.png'></a>";
							str += "</div>";
							str +"</li>";
							   }
							});
					uploadUL.append(str);
		}
		////////////////////////첨부파일 추가 끝
		
		
	</script>



		<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	글내용
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        <form name="form" action="/board/modify" role="form" method="post">
                        
                        	<input  type = 'hidden' name='pageNum' value='<c:out value="${cri.pageNum }"/>'>
                        	<input  type = 'hidden' name='amount' value='<c:out value="${cri.amount }"/>'>
                        	<input  type = 'hidden' name='type' value='<c:out value="${cri.type }"/>'>
                        	<input  type = 'hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'>
                        
                        	<div class="form-group">
                           		<label>Bno</label><input class="form-control" name='bno' value='<c:out value="${board.bno}"/>' readonly="readonly"> 
                           	</div>
                           	
                        
                           	<div class="form-group">
                           		<label>Title</label><input class="form-control" name='title'  value='<c:out value="${board.title}"/>'>
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>Content</label>
                           		<textarea class="form-control" rows="3" name='content' ><c:out value="${board.content}"/></textarea>
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>Writer</label><input class="form-control" name='writer' value='<c:out value="${board.writer}"/>' >
                           		
                           	</div>
                           	
                           		<div class='bigPictureWrapper'>
									<div class='bigPicture'>
									</div>	
								</div>
                     
                           	<!-- data-oper는 사용자정의 속성 -->
                           	<!-- data-oper는 사용자 정의로 내가 알아서 만들 수 있음. -->
                           	<button type="submit" data-oper='modify' class="btn btn-default">수정</button>
                           	<button type="submit" data-oper='remove' class="btn btn-danger"> 삭제 </button>
                           	<button type="submit" data-oper='list' class="btn btn-info">목록</button>
                           	
                           </form>
                       	
                        </div>
                        <!-- /.panel-body -->
                        
                        
                    </div>
                    <!-- /.panel -->
                    
                    
                </div>
						<!-- 첨부파일 ---------------------------------------------------------->
						<div class = "row">
								<div class="col-lg-12">
									<div class="panel panel-default">
										<div class="panel-heading">첨부파일</div>
										<!-- end panel-heading -->
										
										<!-- panel-body  --------------------------------------------->
										<div class="panel-body">
											<div class="form-group uploadDiv">
												<input type="file" name='uploadFile' multiple="multiple">
											</div>
											<div class='uploadResult'>
												<ul>
												
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>	
                <!-- /.col-lg-12 -->
            </div>
          
      
        </div>
        <!-- /#page-wrapper -->


<%@ include file="../includes/footer.jsp" %>