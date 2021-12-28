package com.example.doorlock.controller;


import com.example.doorlock.model.Board;
import com.example.doorlock.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {

//            제목, 내용 둘다 빈값일 때 전체 데이터 조회
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            List<Board> findallDataList =  repository.findAll();
            System.out.println(findallDataList);
            return repository.findAll();
        }else {
            //제목이나 내용에 해당하는 값 조회 &
            List<Board> findallDataList =  repository.findAll();
            System.out.println(findallDataList);
            return repository.findByTitleOrContent(title, content);

        }
    }

//    @GetMapping("/boards")
//    List<Board> all() {
//        return repository.findAll();
//    }


    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }


    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
