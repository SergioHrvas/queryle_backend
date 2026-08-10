package com.github.sergiohrvas.queryle.challenges.domain.ports;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

public interface QueryEvaluatorPort {
    Feedback evaluate(Query query, Query solutionQuery, DataContext dataContext);
}
