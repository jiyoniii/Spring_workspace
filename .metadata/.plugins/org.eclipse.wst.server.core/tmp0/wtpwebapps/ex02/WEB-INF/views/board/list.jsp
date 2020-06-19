<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<%@ include file="../includes/header.jsp" %>

	<script>
	   $(document).ready(function() {
		      var result='<c:out value="${result}"/>';
		      
		      checkModal(result);
		      
		      history.replaceState({},null,null); //history.state를 null로 변경
		      
		      function checkModal(result) {
		         if(result==='' || history.state){ //history.state가 없으면(null)이면 띄우지 말라(return)는 뜻
		            return;
		         }
		         if(parseInt(result)>0){
		            $(".modal-body").html("게시글"+parseInt(result)+
		                  " 번이 등록되었습니다.");
		         }
		         $("#myModal").modal("show");
		      }
		      
		      $("#regBtn").on("click",function(){
		         self.location="/board/register";
		      });
		      
		      var actionForm=$("#actionForm");
		      
		      $(".paginate_button a").on("click", function(e){
		    	  e.preventDefault();
		    	  console.log("click");
		    	  actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		      });
		      
		      $(".paginate_button a").on("click",function(e){
		    	  e.preventDefault();
		    	  
		    	  console.log('click');
		    	  
		    	  actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		    	  actionForm.submit();
		      });
		      
		      
		      
		   });
	</script>

		<!-- 컨텐츠 --------------------- -->
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
                            	글목록
                            <button id='regBtn' type="button" class="btn btn-primary btn-xs pull-right">글쓰기</button>
                            	
                            	
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>번호</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>작성일</th>
                                        <th>수정일</th>
                                    </tr>
                                </thead>
                            <c:forEach items="${list}" var="board">
                            	<tr>
                            	<!-- 38번 라인의 작성법이나, c:out으로 작성하는 것이나 출력되는 결과값은 동일하다. -->
                            		<td>${board.bno}</td>
                            		<td width="330"><a href='/board/get?bno=<c:out value ="${board.bno}" />'><c:out value="${board.title}"/></a></td>
                            		<td><c:out value ="${board.writer}" /></td>
                            		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" /></td>
                            		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}" /></td>
                            	</tr>
                            </c:forEach>
                                 
                            </table>
                            <!-- /.table-responsive -->
							
							
							
							<!-- paging ------------------------------------------------>
							
							<form id='actionForm' action ="/board/list" method='get'>
								<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
								<input type='hidden' name='amount' value='${pageMaker.cri.amount }'>
							</form>
							
							
							<!-- pull-right는 오른쪽 정렬 -->
							<div class='pull-right'> 
								<ul class="pagination">
								
								<c:if test="${pageMaker.prev}">
									<li class="paginate_button previous"><a href="${pageMaker.startPage-1}">Previous</a>
									</li>
								</c:if>
								
								<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
								<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""} "> <a href="${num}">${num}</a></li>
								</c:forEach>
								
								
								<c:if test="${pageMaker.next}">
									<li class="paginate_button next"><a href="${pageMaker.endPage +1 }">Next</a>
									</li>
								</c:if>
								</ul>
								
							</div>
							<!-- paging -->

	                       <!-- 모달창 ---------------------------------------------->
							<div id="myModal" class="modal fade" role="dialog">
							  <div class="modal-dialog">
							
							    <!-- Modal content-->
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal">&times;</button>
							        <h4 class="modal-title">알림</h4>
							      </div>
							      <div class="modal-body">
							        <p>처리가 완료 되었습니다.</p>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							      </div>
							    </div>
							
							  </div>
							</div>
	                       <!-- 모달창 -->
                       
                       
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