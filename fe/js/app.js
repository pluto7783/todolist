(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	
	//변수 선언
	var $p_todoList = $("ul.todo-list");
	var $p_newTodo = $("input.new-todo");
	var $p_itemLeft = $("span.todo-count").children();
	var $p_filters = $("ul.filters>li>a").css("cursor","pointer");
	var $p_clearBtn = $("button.clear-completed");
	var $p_mainSection = $(".main");
	var $p_footer = $(".footer");
	
	//input박스에 할 일 등록 후 엔터 키입력시 todos등록
	$p_newTodo.keypress(function(event) {
		if ( event.which == 13 && $p_newTodo.val().replace(/\s/g, "") != ""){
			event.preventDefault();
			var inputText = $p_newTodo.val();
			var inputData = {"todo" : inputText};
			
			doAjax({method:"POST",data:inputData},function(data){
				insertCreatedTodoIntoUl(inputText,data);
				$p_newTodo.val('');
				refreshItemLeftForEvents();
				//할 일이 등록 될 때 filter중 completed가 선택되있으면 새로 등록한것을 자동 숨김
				if( $($p_filters[2]).attr("class") == "selected"){
					$p_todoList.children(".viewTodo").hide();
				} 
			});
		};
	});
	
	//처음 페이지 로딩시 데이터베이스에 저장된 todos를 불러옴
	doAjax({method:"get"},function(data){
		for(var i = 0; i<data.length; i++){
			if ( data[i].completed == 1){
				insertCreatedTodoIntoUl(data[i].todo,data[i].id,"checked");
			}else{
				insertCreatedTodoIntoUl(data[i].todo,data[i].id);
			};
		}
		refreshItemLeftForEvents();
	});
	
	
	//all,active,completed필터 기능
	$p_filters.on("click",function(){
		if ( this.innerHTML == "Active" && $(this).attr("class") != "selected"){
			$p_todoList.children(".viewTodo").show(500);
			$p_todoList.children(".completed").hide(500);
		}else if ( this.innerHTML == "Completed" && $(this).attr("class") != "selected" ){
			$p_todoList.children(".viewTodo").hide(500);
			$p_todoList.children(".completed").show(500);
		}else if ( this.innerHTML == "All" && $(this).attr("class") != "selected"){
			$p_todoList.children().show(500);
		}
		$p_filters.removeClass("selected");
		$(this).addClass("selected");
	});

	//Clear completed 버튼 기능
	$p_clearBtn.on("click",function(){
		$p_todoList.children(".completed").hide("slow",function(){
			$p_todoList.children(".completed").remove();
			refreshItemLeftForEvents();
		});
		
		doAjax({method:"DELETE",url:"/api/todos/"+(-1),},function(data){
			$p_clearBtn.fadeOut(300);
		});
	});
	
	//param과 콜백 함수를 파라미터로 받는 defaults값이 설정된 ajax실행 메소드
	function doAjax(param, callbackFunc, insertData){
		var defaults = {
				method:"GET",
				data:insertData,
				url:"/api/todos",
				success:function(data){
					if ( callbackFunc!=undefined ) callbackFunc(data);
				},
				error:function(){
					alert("연결실패");
				}
		};
		
		var settings = $.extend(true, defaults, param);		
		$.ajax(settings);
	}
	
	// item left를 계산하여 갱신해주며 todo가 0일때  main section과 footer를 숨겨주는 메소드
	function refreshItemLeftForEvents(){
		$p_itemLeft.text($p_todoList.children().length-$p_todoList.children(".completed").length);
		if( $p_todoList.children(".completed").length == 0  ){
			$p_clearBtn.fadeOut(300);
		}else{
			$p_clearBtn.fadeIn(300);
		}
		if( $p_todoList.children().length == 0 ){
			$p_mainSection.hide(200);
			$p_footer.hide(200);
		}else{
			$p_mainSection.show(300);
			$p_footer.show(300);
		}
	}
	
	//데이터베이스에 insert된 데이터를 success 함수에서 받아와, todos리스트 만들어주는 메소드
	function insertCreatedTodoIntoUl(todoString,id){
		var $li = $("<li>").addClass("viewTodo");
		var $div = $("<div>").addClass("view");
		var $input = $("<input>").addClass("toggle").attr("type","checkbox")
				                 .on("click",function(event){
						                	 	var todoId = $(this).next().attr("class");
												if ( $(this).is(":checked") == true ){
													$(this).parent().parent().attr("class","completed");
													var updateData = {"id":todoId,"completed":1};
												}else{
													$(this).parent().parent().attr("class","viewTodo");
													var updateData = {"id":todoId,"completed":0};
												};
												doAjax({method:"PUT"},function(data){
													refreshItemLeftForEvents();
												},updateData);
						                 	});
		if ( arguments[2] == "checked"){
			$li.attr("class","completed");
			$input.prop("checked",true);
		};
		var $label = $("<label>").addClass(""+id).text(todoString);
		var $button = $("<button>").addClass("destroy")
								   .on("click",function(){
									   var todoId = $(this).prev().attr("class");
									   var $delLi = $(this).parent().parent();
									   
									   doAjax({method:"DELETE",url:"/api/todos/"+todoId,},function(data){
										   $delLi.remove();
										   refreshItemLeftForEvents();
									   });
								   });
		$div.append($input);
		$div.append($label);
		$div.append($button);
		$li.append($div);
		$p_todoList.append($li);
		$li.insertBefore($p_todoList.children()[0]);
	}
	
	$("h1").hide().fadeIn(1000);
	
})(window);
