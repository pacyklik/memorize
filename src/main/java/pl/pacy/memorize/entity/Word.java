package pl.pacy.memorize.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Word {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private Long id;
	private String word;
	private String translate;
	@Column(length = 400)
	private String sentence;
	@Column(length = 400)
	private String sentenceTranslate;
	private Boolean know;
	private Boolean prepared;
	private Long levelLearned;

	@ManyToOne
	@JoinColumn(name = "lesson_id")
	private Lesson lesson;
}
