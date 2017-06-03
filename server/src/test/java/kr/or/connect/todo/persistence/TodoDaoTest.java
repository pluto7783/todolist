package kr.or.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.domain.Todo;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoDaoTest {

	@Autowired
	private TodoDao dao;
		
	@Test
	public void shouldInsertAndSelectAll() {
		dao.insertTodo("과제하기");
		dao.insertTodo("제출하기");
		
		List<Todo> allTodos = dao.selectAll();
		assertThat(allTodos, is(notNullValue()));
	}
	
	@Test
	public void shouldInsertAndDelete(){
		Integer id = dao.insertTodo("삭제하기");
		
		assertThat(dao.deleteTodo(id), is(1));
	}
	
	@Test
	public void shouldInsertAndUpdateComplete(){
		Integer id = dao.insertTodo("완료하기");
		
		assertThat(dao.updateComplete(id,1), is(1));
	}
	
}
