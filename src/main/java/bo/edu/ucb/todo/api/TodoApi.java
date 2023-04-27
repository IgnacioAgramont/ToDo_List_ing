package bo.edu.ucb.todo.api;
import java.util.*;

import org.springframework.web.bind.annotation.*;
import bo.edu.ucb.todo.dto.*;
import bo.edu.ucb.todo.bl.*;
@RestController
@CrossOrigin(origins = "*")
class TodoApi {

    private List<TaskDto> tasks = new ArrayList<>();

    /**
     * Este endpoint retorna todas las tareas
     * @param token El token JWT que se obtuvo al autenticar la aplicación
     * @return
     */
    @GetMapping("/api/v1/task")
    public ResponseDto<List<TaskDto>> getAllTasks(
        @RequestHeader("Authorization") String token) {
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            ResponseDto<List<TaskDto>> response = new ResponseDto<>();
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return response;
        }
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        response.setCode("0000");
        response.setResponse(tasks);
        return response;
    }

    /**
     * Este endpoint obitene el detalle de una tarea por ID
     * @param id La llave primaria de la tarea
     * @param token El token de autenticación
     * @return
     */
    @GetMapping("/api/v1/task/{idTask}")
    public ResponseDto<TaskDto> getTaskById( @PathVariable("idTask") Integer id, @RequestHeader("Authorization") String token) {
        ResponseDto<TaskDto> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return response;
        }
        //Buscamos el elemento en la lista
        TaskDto task = tasks.stream()
                .filter(t -> t.getTaskId().equals(id))
                .findFirst()
                .orElse(null);
        // Si no existe retornamos un error
        if (task == null) {
            //FIXME: Cambiar el codigo de error debe retornar 404
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Task not found");
            return response;
        } else {
            // Si existe retornamos el elemento
            response.setCode("0000");
            response.setResponse(task);
            return response;
        }
    }

    /**
     * Actualiza una tarea por id de tarea.
     * @param idTask La llave primaria de la tarea
     * @param newTask Los nuevos datos para la tarea
     * @param token El token que se obtuvo en la autenticación
     * @return
     */
    @PutMapping("/api/v1/task/{idTask}")
    public ResponseDto<TaskDto> updateTaskById( @PathVariable Integer idTask, @RequestBody TaskDto newTask, @RequestHeader("Authorization") String token) {
        ResponseDto<TaskDto> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return response;
        }
        //Buscamos el elemento en la lista
        TaskDto task = tasks.stream()
                .filter(t -> t.getTaskId().equals(idTask))
                .findFirst()
                .orElse(null);
        // Si no existe retornamos un error
        if (task == null) {
            //FIXME: Cambiar el codigo de error debe retornar 404
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Task not found");
            return response;
        } else {
            // Actualizamos los atributos de task con los de newTask
            
            task.setDescription(newTask.getDescription());
            task.setDate(newTask.getDate());
            task.setLabelIds(newTask.getLabelIds());
            // Si existe retornamos el elemento
            response.setCode("0000");
            response.setResponse(task);
            return response;
        }
    }

    /**
     * Este metodo permite crear una tarea.
     * @param task Todos los datos de una tarea.
     * @param token El token obtenido en la autenticación
     * @return Retorna un mensaje: "Task createed" o error en su defecto.
     */
    @PostMapping("/api/v1/task")
    public ResponseDto<String> createTask(@RequestBody TaskDto task, @RequestHeader("Authorization") String token) {
        ResponseDto<String> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        if (!authBl.validateToken(token)) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid token");
            return response;
        }
        // Obtenemos el ultimo elemento de la lista  y le sumamos 1 para obtener el id
        // del nuevo elemento
        if (tasks.size() > 0) {
            TaskDto lastTask = tasks.get(tasks.size() - 1);
            task.setTaskId(lastTask.getTaskId() + 1);
        } else {
            task.setTaskId(1);
        }
        tasks.add(task);
        
        response.setCode("0000");
        response.setResponse("Task created");
        return response;
    }
}
