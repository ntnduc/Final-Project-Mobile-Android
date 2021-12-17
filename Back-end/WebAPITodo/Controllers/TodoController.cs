using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Todo.Core;
using Todo.DB;

namespace WebAPITodo.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class TodoController : ControllerBase
    {
        private readonly ILogger<TodoController> _logger;
        private ITodoServices _todoServices;

        public TodoController(ILogger<TodoController> logger, ITodoServices todoServices)
        {
            _logger = logger;
            _todoServices = todoServices;
        }

        [HttpGet]
        public IActionResult GetTodos()
        {
            return Ok(_todoServices.GetTodos());
        }

        [HttpGet("{id}", Name="GetTodo")]
        public IActionResult GetTodo(int id)
        {
            return Ok(_todoServices.GetTodo(id));
        }

        [HttpPost]
        public IActionResult CreateTodo(Todos todo)
        {
            var newTodo = _todoServices.CreateTodo(todo);
            return CreatedAtRoute("GetTodo", new { newTodo.Id }, newTodo);
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteTodo(int id)
        {
            _todoServices.DeleteTodo(id);
            return Ok();
        }

        [HttpPut]
        public IActionResult EditTodo([FromBody] Todos todo)
        {
            _todoServices.EditTodo(todo);
            return Ok();
        }

        [HttpDelete]
        public IActionResult DeleteAllTodo()
        {
            _todoServices.DeleteAllTodo();
            return Ok();
        }

        [HttpPut("{checkAll}")]
        public IActionResult CheckAllTodo()
        {
            _todoServices.CheckAllTodo();
            return Ok();
        }
    }
}
