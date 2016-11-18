package pl.pacy.memorize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pacy.memorize.entity.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
