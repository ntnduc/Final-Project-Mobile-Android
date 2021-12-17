
using System.Collections.Generic;
using System.Linq;
using Todo.DB;

namespace Todo.Core
{
    public class TodoServices : ITodoServices
    {
        private AppDbContext _context;
        public TodoServices(AppDbContext context)
        {
            _context = context;
        }
        public Todos CreateTodo(Todos todo)
        {
            _context.Add(todo);
            _context.SaveChanges();

            return todo;
        }

        public  Todos GetTodo(int id)
        {
            return _context.Todo.First(n => n.Id == id);
        }

        public List<Todos> GetTodos()
        {
            return _context.Todo.ToList();
        }
        
        public void DeleteTodo(int id)
        {
            var todo = _context.Todo.First(n => n.Id == id);
            _context.Todo.Remove(todo);
            _context.SaveChanges();
        }

        public void EditTodo(Todos todo)
        {
            var editedTodo = _context.Todo.First(n => n.Id == todo.Id);
            editedTodo.Value = todo.Value;
            editedTodo.Check = todo.Check;
            editedTodo.Mark = todo.Mark;
            _context.SaveChanges();

        }

        public void DeleteAllTodo()
        {
            foreach(var todo in _context.Todo)
            {
                _context.Todo.Remove(todo);
            }
            _context.SaveChanges();
        }

        public void CheckAllTodo()
        {
            foreach(var todo in _context.Todo)
            {
                todo.Check = true;
            }
            _context.SaveChanges();
        }
    }
}
