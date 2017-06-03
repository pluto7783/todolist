package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	static final String DELETE_COMPLETED =
			"DELETE FROM todo WHERE completed= :completed";
	static final String SELECT_ALL =
			"SELECT * FROM todo ORDER BY date ASC";
	static final String INSERT_TODO =
			"INSERT INTO todo (todo) VALUES (:todo)";
	static final String CHECK_COMPLETE =
			"UPDATE todo SET completed = :completed WHERE id = :id";
}
