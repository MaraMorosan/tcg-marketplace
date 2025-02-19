package com.tcgmarketplace.expansion;

import com.tcgmarketplace.expansion.dto.CreateExpansionDto;
import com.tcgmarketplace.expansion.dto.UpdateExpansionDto;
import com.tcgmarketplace.expansion.dto.ExpansionDto;
import com.tcgmarketplace.tcg.Tcg;
import com.tcgmarketplace.tcg.TcgRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpansionServiceImpl implements ExpansionService {

    private final ExpansionRepository expansionRepository;
    private final TcgRepository tcgRepository;

    public ExpansionServiceImpl(ExpansionRepository expansionRepository, TcgRepository tcgRepository) {
        this.expansionRepository = expansionRepository;
        this.tcgRepository = tcgRepository;
    }

    @Override
    public List<ExpansionDto> getAllExpansions() {
        return expansionRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ExpansionDto getExpansionById(Integer id) {
        Expansion expansion = expansionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expansion not found"));
        return toDto(expansion);
    }

    @Override
    public ExpansionDto findExpansionByName(String name) {
        Expansion expansion = expansionRepository.getExpansionByName(name)
                .orElseThrow(() -> new RuntimeException("Expansion not found"));
        return toDto(expansion);
    }

    @Override
    public ExpansionDto createExpansion(CreateExpansionDto dto) {
        if (expansionRepository.existsByName(dto.name())) {
            throw new RuntimeException("Expansion already exists");
        }

        Tcg tcg = tcgRepository.findById(dto.tcgId())
                .orElseThrow(() -> new RuntimeException("TCG not found with id = " + dto.tcgId()));

        if (dto.code() != null) {
            Optional<Expansion> existing = expansionRepository.findByTcgAndCode(tcg, dto.code());
            if (existing.isPresent()) {
                throw new RuntimeException("Code " + dto.code() + " already exists in TCG " + tcg.getName());
            }
        }

        Expansion expansion = new Expansion();
        expansion.setTcg(tcg);
        expansion.setName(dto.name());
        expansion.setCode(dto.code());
        expansion.setReleaseDate(dto.releaseDate());

        expansionRepository.save(expansion);

        return toDto(expansion);
    }

    @Override
    public ExpansionDto updateExpansion(Integer id, UpdateExpansionDto dto) {
        Expansion expansion = expansionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expansion not found"));

        if (dto.code() != null && !dto.code().equals(expansion.getCode())) {
            Optional<Expansion> existing = expansionRepository.findByTcgAndCode(expansion.getTcg(), dto.code());

            if (existing.isPresent()) {
                throw new RuntimeException("Code " + dto.code() + " already exists in TCG " + expansion.getTcg().getName());
            }
        }

        if (dto.name() != null && !dto.name().equals(expansion.getName())) {
            if (tcgRepository.existsByName(dto.name())) {
                throw new RuntimeException("TCG already exists");
            }
            expansion.setName(dto.name());
        }

        expansion.setCode(dto.code());
        expansion.setReleaseDate(dto.releaseDate());

        expansionRepository.save(expansion);
        return toDto(expansion);
    }

    @Override
    public void deleteExpansion(Integer id) {
        if (!expansionRepository.existsById(id)) {
            throw new RuntimeException("Expansion not found");
        }
        expansionRepository.deleteById(id);
    }

    private ExpansionDto toDto(Expansion expansion) {
        return new ExpansionDto(
                expansion.getId(),
                expansion.getTcg().getId(),
                expansion.getName(),
                expansion.getCode(),
                expansion.getReleaseDate()
        );
    }
}
