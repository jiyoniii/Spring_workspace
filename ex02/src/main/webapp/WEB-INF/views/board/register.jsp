    <%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp" %>

<style>

	.uploadResult {
		width:100%;
		height:150px;
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
	
	#list{
		text-decoration-color: none;
	}

</style>

<style>
	
	.bigPictureWrapper{
		position :absolute;
		display: none;
		justify-content: center;
		align-items: center;
		top :0%;
		width:100%;
		height:100%;
		background-color : gray;
		z-index : 100;
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

<script>

$(document).ready(function(){
	var formObj = $("form[role='form']");
	$("button[type='submit']").on("click",function(e){
		e.preventDefault();
		console.log("submit clicked");
		
		var str="";
		
		$(".uploadResult ul li").each(function(i,obj){
			var jobj=$(obj);
			console.dir(jobj);
			
			str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].filetype' value='"+jobj.data("type")+"'>";
		});
		formObj.append(str).submit();
	});
	
	////////////////////////////* drag&drop을 이용한 파일 업로드*///////////////////////////////////////////////////
	
	$(".uploadResult").on("dragenter dragover", function(e){
		e.preventDefault(); //파일이 새롭게 열리는걸 막는다.
	});
	$(".uploadResult").on("drop",function(e){
		e.preventDefault();
		
		var files=e.originalEvent.dataTransfer.files;
		//formData객체에 업로드할 파일들을 추가
		
		var formData = new FormData();
		for (i=0; i<files.length; i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			formData.append("uploadFile",files[i]);
		}
		
		$.ajax({
			url: '/uploadAjaxAction',
			processData:false,
			contentType:false,
			data:formData,
			type:'POST',
			dataType:'json',
			success:function(result){
				console.log(result);
				
				//파일목록
				showUploadResult(result);
				
				//<input type='file'>초기화
				//$(".uploadDiv").html(cloneObj.html());
			}
		});
		
	})
	////////////////////////////* drag&drop을 이용한 파일 업로드 끝*///////////////////////////////////////////////////
	
	
	
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
	
	
	$(".uploadResult").on("click", "button", function(e){
		console.log("delete file");
		
		var targetFile = $(this).data("file"); //data-file의 값 추출
		var type = $(this).data("type");//data-type의 값 추출
		
		var targetLi = $(this).closest("li"); //클릭한 x버튼의 부모 li태그 선택
		
		$.ajax({
			url:'/deleteFile',
			data:{fileName: targetFile, type:type},
			dataType:'text',
			type:'POST',
				success:function(result){
					alert(result);
					targetLi.remove();
				}
		}); //$.ajax
	});
	
	
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
	//uploadResult.empty();
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
	
	
});
</script>


      <!-- 컨텐츠시작 -------------------------->
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
                           	글등록
                    </div>
                    <!-- /.panel-heading -->
			        <div class="panel-body">
			           <form role="form" action="/board/register" method="post">
			              <div class="form-group">
			                 <label>제목</label> <input class="form-control" name="title">
			              </div>
			              <div class="form-group">
			                 <label>내용</label>
			                 <textarea class="form-control" rows="3" name="content"></textarea>
			              </div>
			              <div class="form-group">
			                 <label>작성자</label> <input class="form-control" name="writer">
			              </div>
			              
			              <button type="submit" class="btn btn-default">등록</button>
			              <button type="reset" class="btn btn-default">취소</button>
			              <button id="list" class="btn btn-default"><a href="http://localhost:8181/board/list">목록</a></button>
			           </form>
			
			        </div>
           		 <!-- /.panel-body -->
         		</div>
               <!-- /.panel -->
      		</div>
           <!-- /.col-lg-12 -->
   		</div>
	<!-- 첨부파일 ------------------------------------>
	<div class = "row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">첨부파일</div>
				<!-- end panel-heading -->
				
				<!-- panel-body  --------------------------------------------->
				<div class="panel-body">
					<div class="form-group uploadDiv">
						<input type="file" name="uploadFile" multiple>
					</div>
					<div class='uploadResult'>
						<ul>
						
						</ul>
					</div>
					
				</div>
				<!-- end panel-body -->
			</div>		
		</div>
	</div>

</div>
        <!-- 컨텐츠끝 -->

<%@include file="../includes/footer.jsp" %>
