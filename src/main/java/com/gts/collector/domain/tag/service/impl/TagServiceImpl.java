package com.gts.collector.domain.tag.service.impl;

import com.gts.collector.domain.tag.repository.TagRepository;
import com.gts.collector.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<String> getAllTagNames() {
        return tagRepository.findAll().stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toList());
    }
}
