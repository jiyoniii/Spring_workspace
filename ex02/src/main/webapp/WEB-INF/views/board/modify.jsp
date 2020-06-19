<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<%@ include file="../includes/header.jsp" %>


	<!-- 수정,삭제,목록 버틑에 따라 다르게 이동할 수 있도록 js를 작성 -->
	<script>
		$(document).ready(function(){
			var formObj = $("form");
			$('button').on("click", function(e){
				e.preventDefault();
				
				var operation = $(this).data("oper");
				
				console.log(operation);
				
				if(operation === 'remove'){
					formObj.attr("acrion", "/board/remove");
				}else if (operation === 'list'){
					//move to list
					/* 메소드가 post인것을 get으로 바꾸겠단 소리! */
					formObj.attr("action","/board/list").attr("method","get");
					formObj.empty();
					
					return;
				}
				formObj.submit();
			});
		});
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
                        <form action="/board/modify" role="form" method="post">
                        
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
                           	
                           	<div class="form-group">
                           		<label>RegDate</label>
                           		<input class="form-control" name='regDate' value='<fmt:formatDate pattern ="yyyy/MM/dd" value="${board.regdate}"/>' readonly="readonly">
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>Update Date</label>
                           		<input class="form-control" name='updateDate' value='<fmt:formatDate pattern ="yyyy/MM/dd" value="${board.updateDate}"/>' readonly="readonly">
                           	</div>
                           	
                           	
                           	<!-- data-oper는 사용자정의 속성 -->
                           	<!-- data-oper는 사용자 정의로 내가 알아서 만들 수 있음. -->
                           	<button type="submit" data-oper='modify' class="btn btn-default">수정</button>
                           	<button type="submit" data-oper='remove' class="btn btn-daner">삭제</button>
                           	<button type="submit" data-oper='list' class="btn btn-info">목록</button>
                           	
                           </form>
                       
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
          
      
        </div>
        <!-- /#page-wrapper -->


<%@ include file="../includes/footer.jsp" %>