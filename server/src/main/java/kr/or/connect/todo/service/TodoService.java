package kr.or.connect.todo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	private TodoDao dao;
	
	TodoService(){
	}
	
	@Autowired
	public TodoService(TodoDao dao) {
		this.dao = dao;
	}
	
	public Integer insertTodo(String todo){
		return dao.insertTodo(todo);
	}
	
	public void updateComplete(Integer id,Integer completed){
		dao.updateComplete(id,completed);
	}
	
	public void deleteTodo(Integer id){
		dao.deleteTodo(id);
	}
	
	public Collection<Todo> findAll() {
		return dao.selectAll();
	}
}
