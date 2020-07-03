<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

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
				<div class="panel-heading">글내용</div>
				<!-- /.panel-heading -->
				<div class="panel-body">

					<div class="form-group">
						<label>Bno</label>
						<input class="form-control" name='bno'value='<c:out value="${board.bno}"/>' readonly="readonly">
					</div>

					<div class="form-group">
						<label>Title</label><input class="form-control" name='title'value='<c:out value="${board.title}"/>' readonly="readonly">
					</div>

					<div class="form-group">
						<label>Content</label>
						<textarea class="form-control" rows="3" name='content'readonly="readonly"><c:out value="${board.content}" /></textarea>
					</div>

					<div class="form-group">
						<label>Writer</label><input class="form-control" name='writer'value='<c:out value="${board.writer}"/>' readonly="readonly">

					</div>

					<!-- data-oper는 사용자정의 속성 -->
					<button data-oper='modify' class="btn btn-default"onclick="location.href='/board/modify?bno=<c:out value="${board.bno}"/>'">수정</button>
					<button data-oper='delete' class="btn btn-default" >삭제</button>				
					<button data-oper='list' class="btn btn-info"onclick="location.href='/board/list'">목록</button>

					<!-- 게시물 조회 페이지에서는 수정과 삭제가 필요한 페이지로 링크를 처리해야 하기때문에 form 태그를 이용하여 작성 -->
					<form id='operForm' action="/board/modify" method="get">
						<input type='hidden' id='bno' name='bno'
							value='<c:out value="${board.bno}"/>'>
						<!-- 히든태그로 pageNum, amount도 넣어 목록으로 되돌아 갔을 때 무조건 1페이지로 이동하는게 아닌 해당 페이지로 다시 되돌아갈 수 있도록 함 -->
						<input type='hidden' name='pageNum'
							value='<c:out value="${cri.pageNum}"/>'> <input
							type='hidden' name='amount'
							value='<c:out value="${cri.amount}"/>'> <input
							type='hidden' name='keyword'
							value='<c:out value="${cri.keyword}"/>'> <input
							type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

					</form>

				</div>
				<!-- /.panel-body -->
			</div>
			
			<div class='bigPictureWrapper'>
				<div class='bigPicture'>
				</div>	
			</div>
			
			<!-- 첨부파일 ---------------------------------------------------------->
			<div class = "row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">첨부파일</div>
							<!-- end panel-heading -->
							
							<!-- panel-body  --------------------------------------------->
							<div class="panel-body">
								<div class='uploadResult'>
									<ul>
									
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>			
			
			<!-- /.panel -->
			<div class="row">
				<div class="col-lg-12">
					<!-- 댓글목록 ----------------------------------------------------------------->

					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-comments fa-fw"></i> Reply
							<button id='addReplyBtn'
								class='btn btn-primary btn-xs pull-right'>New Reply</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<ul class="chat">
								<!-- start reply -->
								<li class="left clearfix" data-rno='12'>
									<div>
										<div class="header">
											<strong class="primary-font"> </strong> <small
												class="pull-right text-muted"> </small>
										</div>
										<p></p>
									</div>
								</li>
							</ul>

							<div>
								<div>
									<!-- /.panel-body -->
									
								</div>
								<!-- /.panel -->
							</div>
							<!-- /.col-lg-12 -->
						</div>
						<!-- 댓글 페이징 ------------------ -->
						<div class = "panel-footer"></div>
						<!-- 댓글 페이징 -->
					</div>
					<!-- /#page-wrapper -->
				</div>
			</div>

			<!-- 모달창 ---------------------------------------------->
			<div id="myModal" class="modal fade" role="dialog" tabindex="-1"
				aria-labelledby="myModalLabel" aria-hidden="true">
				console.log("myModal");
				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
						</div>
						<div class="modal-body">
							<!-- form-group은 한줄 띄우는 역할을 함 -->
							<div class="form-group">
								<label>Reply</label>
								<!-- form-control은 input태그의 모서리를 동그랗게 표시해주는 역할 -->
								<input class="form-control" name="reply" value='new Reply'>
							</div>
							<div class="form-group">
								<label>Replyer</label>
								<!-- form-control은 input태그의 모서리를 동그랗게 표시해주는 역할 -->
								<input class="form-control" name="replyer" value='replyer'>
							</div>
							<div class="form-group">
								<label>Reply Date</label>
								<!-- form-control은 input태그의 모서리를 동그랗게 표시해주는 역할 -->
								<input class="form-control" name="replyDate" value=''>
							</div>
						</div>
						<div class="modal-footer">
							<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
							<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
							<button id='modalRegisterBtn' type="button"
								class="btn btn-primary">Register</button>

							<button id='modalCloseBtn' type='button' class='btn btn-default'
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div>
			<!-- 모달창 -->


			<script src="/resources/js/reply.js"></script>
			
			<script>
			$(document).ready(function() {
				(function(){
				var bno = '<c:out value="${board.bno }"/>';
				
				$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
					console.log(arr);
					var str="";
					
					$(arr).each(function(i, attach){
						//image type
						
						if(attach.filetype){
							var fileCallPath = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid + "_"+attach.fileName);
							
							str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.filetype+"'><div>";
							str += "<img src='/display?fileName="+fileCallPath+"'>";
							str += "</div>";
							str +"</li>";
						}else{
							str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.filetype+"'><div>";
							str += "<span>" +attach.fileName+"</span><br/>";
							str += "<img src='/resources/img/attach.png'>";
							str += "</div>";
							str + "</li>";
						}
					});
					$(".uploadResult ul").html(str);
				});
			})(); 
		});

			
			</script>
			


			<script>
				$(document).ready(function() {
					
									$(".uploadResult").on("click","li", function(e){
										console.log("view image");
										
										var liObj = $(this);
										
										var path= encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
										
										if(liObj.data("type")){
											showImage(path.replace(new RegExp(/\\/g),"/"));
										}else{
											//download
											self.location = "/download?fileName="+path
										}
									});
									
									function showImage(fileCallPath){
										//alert(fileCallPath);
										
										$(".bigPictureWrapper").css("display","flex").show();
										
										$(".bigPicture").html("<img src='/display?fileName="+fileCallPath+"'>")
										.animate({width:'100%', height:'100%'},1000);
									}
									
									$(".bigPictureWrapper").on("click",function(e){
										$(".bigPicture").animate({width:'0%',height:'0%'}, 1000);
										setTimeout(function(){
											$('.bigPictureWrapper').hide();
										},1000);
									})
									
					
									var bnoValue = '<c:out value="${board.bno}"/>';
									var replyUL = $(".chat");

									showList(1);

									function showList(page) {
										console.log("show list "+page);
										
										replyService.getList({bno : bnoValue,page : page || 1},
														function(replyCnt, list) {
															
															console.log("replyCnt: "+replyCnt);
															console.log("list: "+list);
															console.log(list);
															
															//page가 -1이면 마지막 페이지로 이동. 등록 후 사용
															if (page == -1){
																pageNum = Math.ceil(replyCnt/10.0);
																showList(pageNum);
																return;
															}
															
															var str = "";
															
															if (list == null|| list.length == 0) {
																return;
															}
															for (var i = 0, len = list.length || 0; i < len; i++) {
																str += "<li class='left clearfix' data-rno = '"+list[i].rno+"'>";
																str += "<div><div class='header'><strong class='primary-font'>["
																	+list[i].rno+"] "+list[i].replyer+ "</strong>";
																str += " <small class='pull-right text-muted'>"+ replyService
																				.displayTime(list[i].replyDate)+ "</small></div>";
																str += " <p>"+ list[i].reply+ "</p></div></li>";
															}
															replyUL.html(str);
															
															showReplyPage(replyCnt);
														});//end function
									}//end showList

									var operForm = $("#operForm");

									$("button[data-oper='modify']").on("click",function(e) {
										operForm.attr("action","/board/modify").submit();
											});

									$("button[data-oper='list']").on(
											"click",function(e) {
												operForm.find("#bno").remove();
												operForm.attr("action","/board/list")
												operForm.submit();
											});
									 $("button[data-oper='delete']").on("click", function(e){
										 
										  if (confirm("삭제하시겠습니까?") == true){ 
											  operForm.attr("action","/board/remove").attr("method","post");
				                              operForm.submit();
									 	  	}else{
										  		return ;
										  	} 
										  
			                             
			                           }); 

									
									

									console.log("================");
									console.log("JS TEST");

									var bnoValue = '<c:out value="${board.bno}"/>';

								/* 	replyService.add({
										reply : "JS Test",
										replyer : "tester",
										bno : bnoValue
									}, function(result) {
										alert("RESULT: " + result);
									}); */

									replyService.getList({bno : bnoValue, page : 1
													},function(list) {	
														for (var i = 0, len = list.length || 0; i < len; i++) { //list(배열)의 크기가 없다면 0으로 하겠단 뜻.
															console.log(list[i]);
														}
													})

													
									//변수들을 미리 찾아둔다.
									//미리 찾아두는게 속도 면에서도 좋음.
													
									var modal = $("#myModal");
									var modalInputReply = modal.find("input[name='reply']");
									var modalInputReplyer = modal.find("input[name='replyer']");
									var modalInputReplyDate = modal.find("input[name='replyDate']");

									var modalModBtn = $("#modalModBtn");
									var modalRemoveBtn = $("#modalRemoveBtn");
									var modalRegisterBtn = $("#modalRegisterBtn");

									$("#addReplyBtn").on("click",function(e) {
														console.log("addReplyBtn");

														modal.find("input").val("");
														modalInputReplyDate.closest("div").hide();
														modal.find("button[id != 'modalCloseBtn']").hide();

														modalRegisterBtn.show();

														$("#myModal").modal("show");
													});
									
									
									/* 새로운 댓글 등록 */
									modalRegisterBtn.on("click",function(e){
										
										var reply = {
												reply : modalInputReply.val(),
												replyer : modalInputReplyer.val(),
												bno:bnoValue
										};
										replyService.add(reply, function(result){
											alert(result);
											
											modal.find("input").val("");
											modal.modal("hide");
											
											showList(-1);
										});
									});
									
									/* 댓글 클릭이벤트 처리 */
									$(".chat").on("click", "li", function(e){
										console.log("댓글클릭");
										var rno = $(this).data("rno");
										
										console.log(rno);
									});			
									
									/* 댓글 이벤트 처리 */
									$(".chat").on("click","li", function(e){
										var rno = $(this).data("rno");
										
										replyService.get(rno, function(reply){
											
											modalInputReply.val(reply.reply);
											modalInputReplyer.val(reply.replyer)
											modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
											modal.data("rno",reply.rno);
											
											modal.find("button[id != 'modalCloseBtn']").hide();
											modalModBtn.show();
											modalRemoveBtn.show();
											
											$(".modal").modal("show");
										});
									});
									
									//수정
									modalModBtn.on("click",function(e){
										var reply = {rno:modal.data("rno"),reply : modalInputReply.val()};
																				
										replyService.update(reply, function(result){
											alert(result);
											modal.modal("hide");
											showList(pageNum);
										});
									});
									
									//삭제
									modalRemoveBtn.on("click",function(e){
										var rno = modal.data("rno");
										
										replyService.remove(rno, function(result){
											alert(result);
											modal.modal("hide");
											showList(pageNum);
										});
									});
									
									//댓글 페이징 처리
									var pageNum = 1;
									var replyPageFooter = $(".panel-footer");
									
									function showReplyPage(replyCnt){
										
										
										var endNum = Math.ceil(pageNum/10.0)*10;
										var startNum = endNum -9;
										//prev 필요여부 Flag
										var prev = startNum != 1;
										//next 필요여부 Flag
										var next = false;
										
										// 계산으로 구한 endPage보다 실제페이지가 적으면 보정
										if(endNum *10 >= replyCnt){
											endNum = Math.ceil(replyCnt/10.0);
										}
										//endPage보다 실제페이지가 많으면 next를 true로 변경
										if(endNum *10 <replyCnt){
											next =true;
										}
										
										var str = "<ul class = 'pagination pull-right'>";
										
										if(prev){
											str += "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
										}
										for(var i=startNum; i <= endNum; i++){
											
											// 현재페이지번호이면 active 클래스 설정
											var active = pageNum == i? "active" : "";
											str += "<li class = 'page-item "+active+" '><a class='page-link'href='"+i+"'>"+i+"</a></li>";
										}
										if(next){
											str+= "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
										}
										
										str += "</ul></div>";
										
										console.log(str);
										replyPageFooter.html(str);
									}
									
									//새댓글 가져오기
									
									replyPageFooter.on("click","li a", function(e){
										e.preventDefault();
										console.log("page click");
										
										var targetPageNum = $(this).attr("href");
										
										console.log("targetPageNum:  "+targetPageNum);
										
										pageNum = targetPageNum;
										
										showList(pageNum);
									});
								});
			</script>

			<%@ include file="../includes/footer.jsp"%>