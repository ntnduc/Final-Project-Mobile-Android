using System.Collections.Generic;
using Todo.DB;
namespace Todo.Core
{
    public interface ITodoServices
    {
        Todos CreateTodo(Todos todo);
        Todos GetTodo(int id);
        List<Todos> GetTodos();
        void DeleteTodo(int id);

        void EditTodo(Todos todo);

        void DeleteAllTodo();

        void CheckAllTodo();

    }
}
