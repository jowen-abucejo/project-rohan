package com.yondu.university.project_rohan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.yondu.university.project_rohan.dto.ScoreDto;
import com.yondu.university.project_rohan.entity.Score;
import com.yondu.university.project_rohan.service.ScoreService;

import jakarta.validation.Valid;

@RestController
public class ScoreController {
    private final ScoreService scoreService;

    /**
     * @param scoreService
     */
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping(path = "quizzes/{id}/scores")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ScoreDto scoreQuiz(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable int id,
            @RequestBody @Valid ScoreDto scoreDto) {

        Score score = this.scoreService.saveNewQuizScore(currentUser, id, scoreDto.getEmail(),
                convertToScoreEntity(scoreDto));

        return new ScoreDto(score);
    }

    @PostMapping(path = "exercises/{id}/scores")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ScoreDto scoreExercise(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable int id,
            @RequestBody @Valid ScoreDto scoreDto) {

        Score score = this.scoreService.saveNewExerciseScore(currentUser, id, scoreDto.getEmail(),
                convertToScoreEntity(scoreDto));

        return new ScoreDto(score);
    }

    @PostMapping(path = "courses/{code}/classes/{batch}/project/scores")
    @PreAuthorize("hasRole('SUBJECT_MATTER_EXPERT')")
    public ScoreDto scoreProject(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code,
            @PathVariable int batch,
            @RequestBody @Valid ScoreDto scoreDto) {

        Score score = this.scoreService.saveNewProjectScore(currentUser, code, batch, scoreDto.getEmail(),
                convertToScoreEntity(scoreDto));

        return new ScoreDto(score);
    }

    public static final Score convertToScoreEntity(ScoreDto scoreDto) {
        Score score = new Score();
        score.setScore(scoreDto.getScore());
        return score;
    }

}
