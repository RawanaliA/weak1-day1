package com.example.demo.Service;

import com.example.demo.ApiExeption.ApiExeption;
import com.example.demo.Model.Todo;
import com.example.demo.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;
    public List<Todo> getTodos(Integer id) {
//
      return todoRepository.findTodoByUserId(id);
    }
    public void addTodo(Integer userid, Todo todo) {

      todo.setUserId(userid);
      todoRepository.save(todo);
    }
  public void updateTodo(Integer userid, Todo todo,Integer todoId) {
    Todo oldTodo = todoRepository.findTodoById(todoId);
    if (oldTodo == null) {

      throw new ApiExeption("todo Not found");
    }
    if (oldTodo.getUserId() != userid) {
      throw new ApiExeption("Erorre,Unauthorize");
    }
    oldTodo.setMassage(todo.getMassage());
    todoRepository.save(oldTodo);
  }
  public void deleteTodo(Integer userid,Integer todoId) {
    Todo todo = todoRepository.findTodoById(todoId);
    if (todo.getUserId() == null) {

      throw new ApiExeption("todo Not found");
    }
    todoRepository.delete(todo);

  }
}
