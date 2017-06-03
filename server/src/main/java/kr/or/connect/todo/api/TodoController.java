package kr.or.connect.todo.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping
	Integer insertTodo(@RequestParam("todo") String todo) {
		return service.insertTodo(todo);
	}
	
	@PutMapping
	void checkComplete(@RequestParam("id") Integer id,@RequestParam("completed") Integer completed) {
		service.updateComplete(id,completed);
	}
	
	@DeleteMapping("/{id}")
	void deleteTodo(@PathVariable  Integer id) {
		service.deleteTodo(id);
	}
}
