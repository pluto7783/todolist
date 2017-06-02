package kr.or.connect.todo.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	private final TodoService service;

	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}
	
	@GetMapping
	Collection<Todo> readList() {
		return service.findAll();
	}
	
	@GetMapping("/insert/{todo}")
	Integer insertTodo(@PathVariable  String todo) {
		return service.insertTodo(todo);
	}
	
	@GetMapping("/completed/{id}")
	void checkComplete(@PathVariable  Integer id) {
		service.updateComplete(id);
	}
	
	@GetMapping("/incomplete/{id}")
	void checkIncomplete(@PathVariable  Integer id) {
		service.updateIncomplete(id);
	}
	
	@GetMapping("/delete/{id}")
	void deleteTodo(@PathVariable  Integer id) {
		service.deleteTodo(id);
	}
	
	@GetMapping("/deleteCompleted")
	void deleteCompleted() {
		service.deleteCompleted();
	}
	
}
