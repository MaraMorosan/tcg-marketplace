package com.tcgmarketplace.tcg.rarity;

import com.tcgmarketplace.rarity.Rarity;
import com.tcgmarketplace.rarity.RarityRepository;
import com.tcgmarketplace.tcg.Tcg;
import com.tcgmarketplace.tcg.TcgRepository;
import com.tcgmarketplace.tcg.rarity.dto.CreateTcgRarityDto;
import com.tcgmarketplace.tcg.rarity.dto.TcgRarityDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TcgRarityServiceImpl implements TcgRarityService {

    private final TcgRarityRepository tcgRarityRepository;
    private final TcgRepository tcgRepository;
    private final RarityRepository rarityRepository;

    public TcgRarityServiceImpl(TcgRarityRepository tcgRarityRepository,
                                TcgRepository tcgRepository,
                                RarityRepository rarityRepository) {
        this.tcgRarityRepository = tcgRarityRepository;
        this.tcgRepository = tcgRepository;
        this.rarityRepository = rarityRepository;
    }

    @Override
    public List<TcgRarityDto> getAllTcgRarities() {
        return tcgRarityRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public TcgRarityDto getTcgRarityById(Integer id) {
        TcgRarity tcgRarity = tcgRarityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TcgRarity not found"));
        return toDto(tcgRarity);
    }

    @Override
    public TcgRarityDto createTcgRarity(CreateTcgRarityDto dto) {
        Tcg tcg = tcgRepository.findById(dto.tcgId())
                .orElseThrow(() -> new RuntimeException("TCG not found"));
        Rarity rarity = rarityRepository.findById(dto.rarityId())
                .orElseThrow(() -> new RuntimeException("Rarity not found"));

        TcgRarity tcgRarity = new TcgRarity();
        tcgRarity.setTcg(tcg);
        tcgRarity.setRarity(rarity);

        tcgRarityRepository.save(tcgRarity);
        return toDto(tcgRarity);
    }

    @Override
    public void deleteTcgRarity(Integer id) {
        if (!tcgRarityRepository.existsById(id)) {
            throw new RuntimeException("TcgRarity not found");
        }
        tcgRarityRepository.deleteById(id);
    }

    private TcgRarityDto toDto(TcgRarity tcgRarity) {
        return new TcgRarityDto(
                tcgRarity.getId(),
                tcgRarity.getTcg().getId(),
                tcgRarity.getRarity().getId()
        );
    }
}
