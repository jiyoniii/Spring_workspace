<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<%@ include file="../includes/header.jsp" %>

<script src="/resources/js/reply.js"></script>

		<script>
		$(document).ready(function(){
			
			var operForm = $("#operForm");
			
			$("button[data-oper='modify']").on("click", function(e){
				operForm.attr("action","/board/modify").submit();
			});
			
			$("button[data-oper='list']").on("click", function(e){
				operForm.find("#bno").remove();
				operForm.attr("action","/board/list")
				operForm.submit();
				});
			

			console.log("================");
			console.log("JS TEST");
			
			var bnoValue = '<c:out value="${board.bno}"/>';
			
			replyService.add(
					{reply:"JS Test", replyer:"tester", bno:bnoValue},
						function(result){
							alert("RESULT: "+result);
						}			
			);
		
		
			replyService.getList({bno:bnoValue, page:1}, function(list){
				
				for(var i=0, len=list.length||0; i<len; i++){ //list(배열)의 크기가 없다면 0으로 하겠단 뜻.
					console.log(list[i]);
				}
			})
		
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
                        
                        	<div class="form-group">
                           		<label>Bno</label><input class="form-control" name='bno' value='<c:out value="${board.bno}"/>' readonly="readonly"> 
                           	</div>
                           	
                        
                           	<div class="form-group">
                           		<label>Title</label><input class="form-control" name='title'  value='<c:out value="${board.title}"/>' readonly="readonly">
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>Content</label>
                           		<textarea class="form-control" rows="3" name='content'  readonly="readonly"><c:out value="${board.content}"/></textarea>
                           	</div>
                           	
                           	<div class="form-group">
                           		<label>Writer</label><input class="form-control" name='writer' value='<c:out value="${board.writer}"/>' readonly="readonly">
                           		
                           	</div>
                           	
                           	<!-- data-oper는 사용자정의 속성 -->
                           	<button data-oper='modify' class="btn btn-default" onclick="location.href='/board/modify?bno=<c:out value="${board.bno}"/>'">수정</button>
                           	<button data-oper='list' class="btn btn-info" onclick="location.href='/board/list'">목록</button>
                           	
                           	<!-- 게시물 조회 페이지에서는 수정과 삭제가 필요한 페이지로 링크를 처리해야 하기때문에 form 태그를 이용하여 작성 -->
                           	<form id='operForm' action="/board/modify" method="get">
                           		<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
                           		<!-- 히든태그로 pageNum, amount도 넣어 목록으로 되돌아 갔을 때 무조건 1페이지로 이동하는게 아닌 해당 페이지로 다시 되돌아갈 수 있도록 함 -->
                           		<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
                           		<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
                           		<input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
                           		<input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>
                           	
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