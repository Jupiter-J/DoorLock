package com.example.doorlock.controller;


import com.example.doorlock.model.Board;
import com.example.doorlock.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String greetingSubmit(@ModelAttribute Board board){
        boardRepository.save(board); //실행이 안되고 있음
        System.out.println("test3");
        System.out.println(boardRepository.findByTitleOrContent(board.getTitle(), board.getContent()));
        return "redirect:/board/list";
    }

}


//글자수 예외처리
//        if(bindingResult.hasErrors()){
//            return "board/form";
//        }