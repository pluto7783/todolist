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
	
	public void updateComplete(Integer id){
		dao.updateComplete(id);
	}
	
	public void updateIncomplete(Integer id){
		dao.updateIncomplete(id);
	}
	
	public void deleteTodo(Integer id){
		dao.deleteTodo(id);
	}
	
	public void deleteCompleted(){
		dao.deleteCompleted();
	}
	
	public Collection<Todo> findAll() {
		return dao.selectAll();
	}
}
