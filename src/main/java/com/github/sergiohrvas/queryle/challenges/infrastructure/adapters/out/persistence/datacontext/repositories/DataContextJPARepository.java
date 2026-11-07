package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.entities.DataContextEntity;

@Repository
public interface DataContextJPARepository extends JpaRepository<DataContextEntity, UUID> {
}