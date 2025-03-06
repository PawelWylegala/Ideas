package pl.wylegala.ideas.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wylegala.ideas.common.dto.StatisticsDto;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.domein.repository.QuestionRepository;
import pl.wylegala.ideas.question.dto.QuestionDto;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questionMapper.mapDtoList(questions);

    }


    @Transactional(readOnly = true)
    public QuestionDto getQuestion(UUID id) {
        Question question = questionRepository.getReferenceById(id);
        return questionMapper.mapDto(question);
    }


    @Transactional
    public QuestionDto createQuestion(QuestionDto questionRequestDto) {
        Question question = questionMapper.mapEntity(questionRequestDto);

        Question savedQuestion = questionRepository.save(question);

        return questionMapper.mapDto(savedQuestion);
    }


    @Transactional
    public QuestionDto updateQuestion(UUID id, QuestionDto questionRequestDto) {
        Question existingQuestion = questionMapper.mapEntity(questionRequestDto);
        existingQuestion.setName(questionRequestDto.getName());
        Question savedQuestion = questionRepository.save(existingQuestion);
        return questionMapper.mapDto(savedQuestion);
    }

    @Transactional
    public void deleteQuestion(UUID id) {
        questionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {
        return questionRepository.findAllByCategoryId(id, Pageable.unpaged());
    }

    @Transactional(readOnly = true)
    public Page<Question> findHot(Pageable pageable) {
        return questionRepository.findHot(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> findUnanswered(Pageable pageable) {
        return questionRepository.findUnanswered(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> findByQuery(String query, Pageable pageable) {
        return questionRepository.findByQuery(query, pageable);
    }
    @Transactional(readOnly = true)
    public List<QuestionDto> findTop(int limit) {
        return questionRepository.findAll(PageRequest.of(0, limit))
                .get()
                .map(questionMapper::mapDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findTop(UUID categoryId, int limit) {
        return questionRepository.findAllByCategoryId(categoryId, PageRequest.of(0, limit))
                .stream()
                .map(questionMapper::mapDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findRandom(int limit) {
        return questionRepository.findRandomQuestions(limit)
                .stream()
                .map(questionMapper::mapDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StatisticsDto statistics() {
        return questionRepository.statistics();
    }


}