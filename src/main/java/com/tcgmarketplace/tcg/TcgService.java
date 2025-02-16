package com.tcgmarketplace.tcg;

import com.tcgmarketplace.tcg.dto.CreateTcgDto;
import com.tcgmarketplace.tcg.dto.TcgDto;
import com.tcgmarketplace.tcg.dto.UpdateTcgDto;

import java.util.List;

public interface TcgService {
    List<TcgDto> getAllTcg();
    TcgDto getTcgById(Integer id);
    TcgDto findTcgByName(String name);
    TcgDto createTcg(CreateTcgDto dto);
    TcgDto updateTcg(Integer id, UpdateTcgDto dto);
    void deleteTcg(Integer id);
}
