package kr.or.connect.todo.persistence;

import java.util.Collections;
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
				.usingGeneratedKeyColumns("id")
				.usingColumns("todo");
	}
	
	public List<Todo> selectAll() {
		Map<String, Object> parameters = new HashMap<>();
		return jdbc.query(TodoSqls.SELECT_ALL, parameters, rowMapper);
	}
	
	public Integer insertTodo(String todo){
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("todo", todo);
	    return insertAction.executeAndReturnKey(parameters).intValue();
	}
	
	public Integer updateComplete(Integer id, Integer completed){
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("id", id);
		parameters.put("completed", completed);
		return jdbc.update(TodoSqls.CHECK_COMPLETE, parameters);
	}
	
	public Integer deleteTodo(Integer id){
		if( id == -1){
			Map<String, ?> parameters = Collections.singletonMap("completed", 1);
			return jdbc.update(TodoSqls.DELETE_COMPLETED,parameters);
		}else{
			Map<String, ?> parameters = Collections.singletonMap("id", id);
			return jdbc.update(TodoSqls.DELETE_BY_ID, parameters);
		}
	}
}
