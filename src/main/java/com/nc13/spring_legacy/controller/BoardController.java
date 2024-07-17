package com.nc13.spring_legacy.controller;

import com.nc13.spring_legacy.model.BoardDTO;
import com.nc13.spring_legacy.model.UserDTO;
import com.nc13.spring_legacy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("showAll")
    public String searchResult(Model model, String inputContent) {
        String check = inputContent;
        model.addAttribute("inputContent", check);

        return "redirect:/board/showAll/1";
    }

    @GetMapping("showAll")
    public String moveToFirstPage(Model model, Authentication authentication) {
        System.out.println("showAll()");
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        model.addAttribute("logIn", logIn);
        List<BoardDTO> list = boardService.selectAll();
        model.addAttribute("list", list);
        return "board/showAll";
    }

    @GetMapping("showAll/{pageNo}")
    public String showAll(Model model, @PathVariable("pageNo") int pageNo, Authentication authentication) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }
        String inputContent = (String) model.getAttribute("inputContent");
        model.addAttribute("inputContent", inputContent);
        int maxPage;
        if (inputContent != null) {
            int checkPage = boardService.selectMaxPageSearch(inputContent);
            maxPage = checkPage;
            model.addAttribute("maxPage", maxPage);
        } else {
            maxPage = boardService.selectMaxPage();
            model.addAttribute("maxPage", maxPage);
        }


        int startPage;
        int endPage;

        if (maxPage < 5) {
            startPage = 1;
            endPage = maxPage;
        } else if (pageNo <= 3) {
            startPage = 1;
            endPage = 5;
        } else if (pageNo >= maxPage - 2) {
            startPage = maxPage - 4;
            endPage = maxPage;
        } else {
            startPage = pageNo - 2;
            endPage = pageNo + 2;
        }

        model.addAttribute("curPage", pageNo);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        if (inputContent != null) {
            List<BoardDTO> list = boardService.selectSearch(pageNo, inputContent);
            model.addAttribute("list", list);
        } else {
            List<BoardDTO> list = boardService.selectAll(pageNo);
            model.addAttribute("list", list);
        }

        return "board/showAll";
    }

    @GetMapping("showOne/{id}")
    public String showOne(@PathVariable("id") int id, Model model, Authentication authentication) {
        BoardDTO selected = boardService.selectOne(id);
        model.addAttribute("boardDTO", selected);
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        model.addAttribute("logIn", logIn);
        return "board/showOne";
    }

    @GetMapping("write")
    public String showWrite(Model model, Authentication authentication) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        model.addAttribute("logIn", logIn);
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setWriterId(logIn.getId());
        model.addAttribute("boardDTO", boardDTO);
        return "board/write";
    }

    @PostMapping("write")
    public String write(BoardDTO boardDTO) {
        boardService.insert(boardDTO);

        return "redirect:/board/showOne/" + boardDTO.getId();
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id, Authentication authentication, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        BoardDTO boardDTO = boardService.selectOne(id);
        if (boardDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 글번호");
            return "redirect:/showMessage";
        }

        if (boardDTO.getWriterId() != logIn.getId()) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }
        boardService.delete(id);

        return "redirect:/board/showAll";

    }

    @GetMapping("update/{id}")
    public String showUpdate(@PathVariable("id") int id, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        BoardDTO boardDTO = boardService.selectOne(id);
        if (boardDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 글 번호입니다.");
            return "redirect:/showMessage";
        }

        if (boardDTO.getWriterId() != logIn.getId()) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }
        model.addAttribute("boardDTO", boardDTO);
        return "board/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") int id, Authentication authentication, RedirectAttributes redirectAttributes, Model model, BoardDTO boardDTO) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        boardService.update(boardDTO);

        return "redirect:/board/showOne/" + id;
    }

}
