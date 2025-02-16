package com.tcgmarketplace.tcg;

import com.tcgmarketplace.tcg.dto.CreateTcgDto;
import com.tcgmarketplace.tcg.dto.TcgDto;
import com.tcgmarketplace.tcg.dto.UpdateTcgDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcgServiceImpl implements TcgService {

    private final TcgRepository tcgRepository;

    public TcgServiceImpl(TcgRepository tcgRepository) {
        this.tcgRepository = tcgRepository;
    }

    @Override
    public List<TcgDto> getAllTcg() {
        return tcgRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public TcgDto getTcgById(Integer id) {
        Tcg tcg = tcgRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TCG not found"));
        return toDto(tcg);
    }

    @Override
    public TcgDto findTcgByName(String name) {
        Tcg tcg = tcgRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("TCG not found"));
        return toDto(tcg);
    }


    @Override
    public TcgDto createTcg(CreateTcgDto dto) {
        if (tcgRepository.existsByName(dto.name())) {
            throw new RuntimeException("TCG already exists");
        }
        Tcg tcg = new Tcg();
        tcg.setName(dto.name());
        tcg.setDescription(dto.description());

        tcgRepository.save(tcg);
        return toDto(tcg);
    }

    @Override
    public TcgDto updateTcg(Integer id, UpdateTcgDto dto) {
        Tcg tcg = tcgRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TCG not found"));

        if (dto.name() != null && !dto.name().equals(tcg.getName())) {
            if (tcgRepository.existsByName(dto.name())) {
                throw new RuntimeException("TCG already exists");
            }
            tcg.setName(dto.name());
        }
        if (dto.description() != null) {
            tcg.setDescription(dto.description());
        }

        tcgRepository.save(tcg);
        return toDto(tcg);
    }

    @Override
    public void deleteTcg(Integer id) {
        if (!tcgRepository.existsById(id)) {
            throw new RuntimeException("TCG not found");
        }
        tcgRepository.deleteById(id);
    }

    private TcgDto toDto(Tcg tcg) {
        return new TcgDto(
                tcg.getId(),
                tcg.getName(),
                tcg.getDescription()
        );
    }
}
