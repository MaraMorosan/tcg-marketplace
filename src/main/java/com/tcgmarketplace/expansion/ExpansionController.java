package com.tcgmarketplace.expansion;

import com.tcgmarketplace.expansion.dto.CreateExpansionDto;
import com.tcgmarketplace.expansion.dto.UpdateExpansionDto;
import com.tcgmarketplace.expansion.dto.ExpansionDto;
import com.tcgmarketplace.tcg.dto.TcgDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expansion")
public class ExpansionController {

    private final ExpansionService expansionService;

    public ExpansionController(ExpansionService expansionService) {
        this.expansionService = expansionService;
    }

    @GetMapping
    public List<ExpansionDto> getAllExpansions() {
        return expansionService.getAllExpansions();
    }

    @GetMapping("/{id}")
    public ExpansionDto getExpansionById(@PathVariable Integer id) {
        return expansionService.getExpansionById(id);
    }

    @GetMapping("/search")
    public ExpansionDto searchExpansionByName(@RequestParam String name) {
        return expansionService.findExpansionByName(name);
    }

    @PostMapping
    public ExpansionDto createExpansion(@RequestBody CreateExpansionDto dto) {
        return expansionService.createExpansion(dto);
    }

    @PutMapping("/{id}")
    public ExpansionDto updateExpansion(@PathVariable Integer id, @RequestBody UpdateExpansionDto dto) {
        return expansionService.updateExpansion(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteExpansion(@PathVariable Integer id) {
        expansionService.deleteExpansion(id);
    }
}
