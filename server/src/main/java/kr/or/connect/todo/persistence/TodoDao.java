package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.domain.Todo;

@Transactional
@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
	
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	public List<Todo> selectAll() {
		Map<String, Object> parameters = new HashMap<>();
		return jdbc.query(TodoSqls.SELECT_ALL, parameters, rowMapper);
	}
	
	public Integer insertTodo(String todo){
		Date presentTime = new Date();
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("todo", todo);
	    parameters.put("completed", 0);
	    parameters.put("date", presentTime);
	    return insertAction.executeAndReturnKey(parameters).intValue();
	}
	
	public void updateComplete(Integer id){
		Map<String, ?> parameters = Collections.singletonMap("id", id);
		jdbc.update(TodoSqls.CHECK_COMPLETE, parameters);
	}
	
	public void updateIncomplete(Integer id){
		Map<String, ?> parameters = Collections.singletonMap("id", id);
		jdbc.update(TodoSqls.CHECK_INCOMPLETE, parameters);
	}
	
	public void deleteTodo(Integer id){
		Map<String, ?> parameters = Collections.singletonMap("id", id);
		jdbc.update(TodoSqls.DELETE_BY_ID, parameters);
	}
	
	public void deleteCompleted(){
		Map<String, ?> parameters = Collections.singletonMap("completed", 1);
		jdbc.update(TodoSqls.DELETE_COMPLETED,parameters);
	}
}
