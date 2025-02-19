package com.tcgmarketplace.rarity;

import com.tcgmarketplace.rarity.dto.CreateRarityDto;
import com.tcgmarketplace.rarity.dto.RarityDto;
import com.tcgmarketplace.rarity.dto.UpdateRarityDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RarityServiceImpl implements RarityService {

    private final RarityRepository rarityRepository;

    public RarityServiceImpl(RarityRepository rarityRepository) {
        this.rarityRepository = rarityRepository;
    }

    @Override
    public List<RarityDto> getAllRarities() {
        return rarityRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public RarityDto getRarityById(Integer id) {
        Rarity rarity = rarityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rarity not found"));
        return toDto(rarity);
    }

    @Override
    public RarityDto createRarity(CreateRarityDto dto) {
        if (rarityRepository.existsByCode(dto.code())) {
            throw new RuntimeException("Rarity with the same code already exists");
        }

        Rarity rarity = new Rarity();
        rarity.setName(dto.name());
        rarity.setCode(dto.code());
        rarity.setDescription(dto.description());

        rarityRepository.save(rarity);
        return toDto(rarity);
    }

    @Override
    public RarityDto updateRarity(Integer id, UpdateRarityDto dto) {
        Rarity rarity = rarityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rarity not found"));

        rarity.setName(dto.name());

        if (dto.code() != null && !dto.code().equals(rarity.getCode())) {
            if (rarityRepository.existsByCode(dto.code())) {
                throw new RuntimeException("Rarity with the same code already exists");
            }
            rarity.setCode(dto.code());
        }

        rarity.setDescription(dto.description());
        rarityRepository.save(rarity);

        return toDto(rarity);
    }

    @Override
    public void deleteRarity(Integer id) {
        if (!rarityRepository.existsById(id)) {
            throw new RuntimeException("Rarity not found");
        }
        rarityRepository.deleteById(id);
    }

    private RarityDto toDto(Rarity rarity) {
        return new RarityDto(
                rarity.getId(),
                rarity.getName(),
                rarity.getCode(),
                rarity.getDescription()
        );
    }
}
