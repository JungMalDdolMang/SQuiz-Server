package com.jmdm.squiz.dto;

import com.jmdm.squiz.enums.QuizType;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AiQuizGenerateResponse {
    private Long quizId;
    private QuizType quizType;
    private ArrayList<AiProblemDTO> problemList;

}
