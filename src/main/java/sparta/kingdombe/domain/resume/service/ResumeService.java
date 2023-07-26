package sparta.kingdombe.domain.resume.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.resume.dto.ResumeRequestDto;
import sparta.kingdombe.domain.resume.dto.ResumeResponseDto;
import sparta.kingdombe.domain.resume.entity.Resume;
import sparta.kingdombe.domain.resume.repository.ResumeRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.exception.InvalidConditionException;
import sparta.kingdombe.global.stringCode.ErrorCodeEnum;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;

    // 전제 조회
    @Transactional(readOnly = true)
    public Page<ResumeResponseDto> findAllResume(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Resume> resumeList = resumeRepository.findAll(pageable);

        List<ResumeResponseDto> result = resumeList.stream()
                .map(ResumeResponseDto::new)
                .collect(Collectors.toList());
        long total = resumeList.getTotalElements();

        // 전체 페이지 수, 전체 데이터 수와 같은 페이징 정보
        return new PageImpl<>(result, pageable, total);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ResumeResponseDto getSelectedResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new InvalidConditionException(ErrorCodeEnum.POST_NOT_EXIST));
        return new ResumeResponseDto(resume);
    }

    // 인재 정보 생성
    public ResumeResponseDto createResume(ResumeRequestDto resumeRequestDto, User user) {
        Resume resume = new Resume(resumeRequestDto, user);
        resumeRepository.save(resume);
        return new ResumeResponseDto(resume);
    }

    // 인재 정보 수정
    public ResumeResponseDto updateResume(Long resumeId, ResumeRequestDto resumeRequestDto, User user) {
        Resume resume = findResume(resumeId);
        checkUsername(resumeId, user);
        resume.update(resumeRequestDto);
        return new ResumeResponseDto(resume);
    }

    // 인재 정보 삭제
    public String deleteResume(Long resumeId, User user) {
        Resume resume = findResume(resumeId);
        checkUsername(resumeId, user);
        resumeRepository.delete(resume);
        return "삭제 완료";
    }

    private Resume findResume(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() ->
                new InvalidConditionException(ErrorCodeEnum.POST_NOT_EXIST));
    }

    private void checkUsername(Long resumeId, User user) {
        Resume resume = findResume(resumeId);
        if (!(resume.getUser().getId().equals(user.getId()))) {
            throw new InvalidConditionException(ErrorCodeEnum.USER_NOT_MATCH);
        }
    }

    public List<ResumeResponseDto> findResumeByCareer(String career) {
        return resumeRepository.findByCareer(career)
                .stream()
                .map(ResumeResponseDto::new)
                .collect(Collectors.toList());
    }
}
