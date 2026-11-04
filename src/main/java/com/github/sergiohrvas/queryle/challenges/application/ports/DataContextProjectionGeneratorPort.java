package com.github.sergiohrvas.queryle.challenges.application.ports;

import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

public interface DataContextProjectionGeneratorPort {
    DataContextProjection generate(DataContext dataContext);
}
