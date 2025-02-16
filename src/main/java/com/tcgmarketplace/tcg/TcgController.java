package com.tcgmarketplace.tcg;

import com.tcgmarketplace.tcg.dto.CreateTcgDto;
import com.tcgmarketplace.tcg.dto.TcgDto;
import com.tcgmarketplace.tcg.dto.UpdateTcgDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tcg")
public class TcgController {

    private final TcgService tcgService;

    public TcgController(TcgService tcgService) {
        this.tcgService = tcgService;
    }

    @GetMapping
    public List<TcgDto> getAllTcg() {
        return tcgService.getAllTcg();
    }

    @GetMapping("/{id}")
    public TcgDto getTcgById(@PathVariable Integer id) {
        return tcgService.getTcgById(id);
    }

    @GetMapping("/search")
    public TcgDto searchTcgByName(@RequestParam String name) {
        return tcgService.findTcgByName(name);
    }

    @PostMapping
    public TcgDto createTcg(@RequestBody CreateTcgDto dto) {
        return tcgService.createTcg(dto);
    }

    @PutMapping("/{id}")
    public TcgDto updateTcg(@PathVariable Integer id, @RequestBody UpdateTcgDto dto) {
        return tcgService.updateTcg(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTcg(@PathVariable Integer id) {
        tcgService.deleteTcg(id);
    }
}
