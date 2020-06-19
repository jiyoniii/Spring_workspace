<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<%@ include file="../includes/header.jsp" %>

		<script type="text/javascript">
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