package com.example.doorlock.controller;


import com.example.doorlock.model.Board;
import com.example.doorlock.repository.BoardRepository;
import com.example.doorlock.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired                  //@컴포넌트사용 해야함
    private BoardValidator boardValidator;

    @GetMapping("/list")  //게시판 호출시 데이터값 넣어주기위해 모델 입력
    public String list(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        System.out.println("test1");
        return "board/list";
    }

    @GetMapping("/form")
        public String form (Model model, @RequestParam(required = false) Long id){
        System.out.println("test2");
        if(id ==null){
            model.addAttribute("board", new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }

        boardRepository.save(board);
        System.out.println("test3");
        System.out.println(boardRepository.findByTitleOrContent(board.getTitle(), board.getContent()));
        return "redirect:/board/list";
    }

}

