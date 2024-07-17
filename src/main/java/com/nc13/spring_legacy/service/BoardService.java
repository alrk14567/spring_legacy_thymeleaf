package com.nc13.spring_legacy.service;

import com.nc13.spring_legacy.model.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    private SqlSession session;
    private final int PAGE_SIZE=20;

    private final String NAMESPACE="mappers.BoardMapper";

    public List<BoardDTO> selectAll(int pageNo) {
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);

        return session.selectList(NAMESPACE + ".selectAll", paramMap);
    }

    public List<BoardDTO> selectAll() {
        return session.selectList(NAMESPACE+".selectAll1");
    }

    public List<BoardDTO> selectSearch(int pageNo, String inputContent) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);
        paramMap.put("inputContent", inputContent);


        return session.selectList(NAMESPACE + ".selectSearch", paramMap);
    }

    public void insert(BoardDTO boardDTO) {

        session.insert(NAMESPACE + ".insert", boardDTO);
    }

    public BoardDTO selectOne(int id) {
        return session.selectOne(NAMESPACE + ".selectOne", id);
    }

    public void update(BoardDTO boardDTO) {
        session.update(NAMESPACE + ".update", boardDTO);
    }

    public void delete(int id) {
        session.delete(NAMESPACE + ".delete", id);
    }

    public int selectMaxPage() {
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRow");
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }

    public int selectMaxPageSearch(String inputContent) {
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRowSearch", inputContent);
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }

}
