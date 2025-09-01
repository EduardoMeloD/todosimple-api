package com.eduardomelo.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardomelo.todosimple.models.User;
import com.eduardomelo.todosimple.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired // serve como construtor para classes que sao interfaces
    private UserRepository userRepository;
    ;
    
    public User BuscarUsuario(Long id){
        //optinal serve para retornar vazio ao inves de null quando nao existe um usuario
        Optional<User> user = this.userRepository.findById(id); // findById esta sendo pego do Jpa
        return user.orElseThrow(() -> new RuntimeException("usuario nao encontrado"));
    }

    @Transactional // usado para funçoes que vao inserir dados no banco de dados, evita erro de enviar somente uma parte das informaçoes
    public User CriarUsuario(User obj){
        obj.setId(null); // evita que o usuario crie um login com id existente e acabe por conseguir alterar o user
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User AtualizarDados(User obj){
        User newObj = BuscarUsuario(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void deletar(Long id){
        BuscarUsuario(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("nao é possivel excluir pois essa entidade esta relacionada com outras");
        }
    }
}
