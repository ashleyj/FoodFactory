package org.example.recipe.step;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository  extends CrudRepository<Step, StepId> {
}
