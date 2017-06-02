package kr.or.connect.domain;

public class Todo {
	private Integer id;
	private String todo;
	private Integer completed;
	private String date;

	public Todo(){
	}
	
	public Todo( String todo, Integer completed, String date) {
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}
	
	public Todo(Integer id, String todo, Integer completed, String date) {
		this.id = id;
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", todo=" + todo + ", completed=" + completed + ", date=" + date + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
