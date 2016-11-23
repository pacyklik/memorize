package pl.pacy.memorize.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.Optional;

public class QueryBuilder<T> {

	public static class PredicateBuilder {

		private BooleanExpression like;

		public PredicateBuilder like(StringPath path, String value) {
			like = (like == null) ? path.like("%" + value + "%") : like.and(path.like("%" + value + "%"));
			return this;
		}

		public BooleanExpression build() {
			return like;
		}
	}

	private JPQLQuery<T> query;
	private BooleanExpression like;

	private QueryBuilder(EntityManager entityManager, EntityPathBase base) {
		query = new JPAQuery(entityManager);
		query.from(base);
	}

	public static QueryBuilder builder(EntityManager entityManager, EntityPathBase base) {
		return new QueryBuilder(entityManager, base);
	}

	public static PredicateBuilder predicateBuilder() {
		return new PredicateBuilder();
	}

	public QueryBuilder<T> like(StringPath path, String value) {
		like = (like == null) ? path.like("%" + value + "%") : like.and(path.like("%" + value + "%"));
		return this;
	}

	public QueryBuilder<T> joinOn(EntityPathBase entityPathBase, BooleanExpression booleanExpression) {
		// better way than simple if? RLY?
		Optional.ofNullable(booleanExpression).ifPresent(e -> {
			query.innerJoin(entityPathBase);
			query.on(e);
		});

		//		if (booleanExpression != null) {
		//			query.innerJoin(entityPathBase);
		//			query.on(booleanExpression);
		//		}
		return this;
	}

	public JPQLQuery<T> build() {
		return (like == null) ? query : query.where(like);
	}

}
