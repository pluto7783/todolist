(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!
	
	//변수 선언
	var $ul = $("ul.todo-list");
	var $newTodo = $("input.new-todo");
	var $itemLeft = $("span.todo-count").children();
	var $filters = $("ul.filters>li>a").css("cursor","pointer");
	var $clearBtn = $("button.clear-completed");
	
	//input박스 키입력시 todos등록
	$newTodo.keypress(function(event) {
		if ( event.which == 13 && $newTodo.val().replace(/\s/g, "") != ""){
			event.preventDefault();
			$.ajax({
					type:"get",
					url:"/api/todos/insert/"+$newTodo.val(),
					success:function(data){
						createTodo($newTodo.val(),data);
						$newTodo.val('');
						checkItemLeft();
					}
			});
		};
	});
	
	//처음 페이지 로딩시 데이터베이스에 저장된 todos를 불러옴
	$.ajax({
		url:'/api/todos',
		success:function(data){
			for(var i = 0; i<data.length; i++){
				if ( data[i].completed == 1){
					createTodo(data[i].todo,data[i].id,"checked");
				}else{
					createTodo(data[i].todo,data[i].id);
				};
			}
			checkItemLeft();
		},
		error:function(){
			alert("연결실패");
		}
	});
	
	//all,active,completed필터 기능
	$filters.on("click",function(){
		if ( this.innerHTML == "Active" && $(this).attr("class") != "selected"){
			$ul.children(".viewTodo").show(500);
			$ul.children(".completed").hide(500);
		}else if ( this.innerHTML == "Completed" && $(this).attr("class") != "selected" ){
			$ul.children(".viewTodo").hide(500);
			$ul.children(".completed").show(500);
		}else if ( this.innerHTML == "All" && $(this).attr("class") != "selected"){
			$ul.children().show(500);
		}
		$filters.removeClass("selected");
		$(this).addClass("selected");
	});

	//Clear completed 버튼 기능
	$clearBtn.on("click",function(){
		$ul.children(".completed").hide("slow",function(){
			$ul.children(".completed").remove();
		});
		$.ajax({
			url:"/api/todos/deleteCompleted",
			success:function(data){
				$clearBtn.fadeOut(300);
			}
		});
	});
	
	// item left를 계산하여 갱신해주는 메소드
	function checkItemLeft(){
		$itemLeft.text($ul.children().length-$ul.children(".completed").length);
		if( $ul.children(".completed").length == 0  ){
			$clearBtn.fadeOut(300);
		}else{
			$clearBtn.fadeIn(300);
		}
	}
	
	//데이터 베이스에서 받아온 정보를 이용해서 화면에 todos리스트 만들어주는 메소드
	function createTodo(todoString,id){
		var $li = $("<li>").addClass("viewTodo");
		var $div = $("<div>").addClass("view");
		var $input = $("<input>").addClass("toggle").attr("type","checkbox")
				                 .on("click",function(event){
				                	 	var todoId = $(this).next().attr("class");
										if ( $(this).is(":checked") == true ){
											$(this).parent().parent().attr("class","completed");
											$.ajax({
												type:"get",
												url:"/api/todos/completed/"+todoId,
												success:function(data){
													checkItemLeft();
										        }
										    });
										}else{
											$(this).parent().parent().attr("class","viewTodo");
											$.ajax({
												type:"get",
												url:"/api/todos/incomplete/"+todoId,
												success:function(data){
										        	checkItemLeft();
										        }
										    });
										};
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
									   $.ajax({
										   type:"get",
										   url:"/api/todos/delete/"+todoId,
										   success:function(data){
											   $delLi.remove();
											   checkItemLeft();
										   }
									   });
									  
								   });
		$div.append($input);
		$div.append($label);
		$div.append($button);
		$li.append($div);
		$ul.append($li);
		$li.insertBefore($ul.children()[0]);
	}
	
	$("h1").hide().fadeIn(1000);
	
})(window);
