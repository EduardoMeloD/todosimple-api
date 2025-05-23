package com.eduardomelo.todosimple.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardomelo.todosimple.models.Task;
import com.eduardomelo.todosimple.models.User;
import com.eduardomelo.todosimple.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public Task buscarTask(long id){

        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
            "a task nao foi encontrada"
        ));
    }

    @Transactional
    public Task criarTask(Task obj){
        User user = this.userService.BuscarUsuario(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    public List<Task> listarTodasAsTask(Long userId){
        List<Task> task = this.taskRepository.findByUser_id(userId);
        return task;
    }
    
    @Transactional
    public Task atualizarTask(Task obj){
        Task newObj = buscarTask(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void deletarTask(Long id){
        buscarTask(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("nao é possivel excluir pois essa entidade esta relacionada com outras");
        }
    }
}
