package pl.pacy.memorize.utils;

import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.Optional;

public class QueryBuilder<T> {

	public static class PredicateBuilder {

		private BooleanExpression like;

		public PredicateBuilder like(StringPath path, String value) {
			if (value != null && !value.isEmpty()) {
				like = (like == null) ? path.like("%" + value + "%") : like.and(path.like("%" + value + "%"));
			}
			return this;
		}

		public PredicateBuilder eq(StringPath path, String value) {
			if (value != null && !value.isEmpty()) {
				like = (like == null) ? path.eq(value) : like.and(path.eq(value));
			}
			return this;
		}

		public PredicateBuilder eq(BooleanPath path, Boolean value) {
			if (value != null) {
				like = (like == null) ? path.eq(value) : like.and(path.eq(value));
			}
			return this;
		}

		public PredicateBuilder gt(NumberPath<Long> path, Long value) {
			if (value != null) {
				like = (like == null) ? path.gt(value) : like.and(path.gt(value));
			}
			return this;
		}

		public PredicateBuilder lt(NumberPath<Long> path, Long value) {
			if (value != null) {
				like = (like == null) ? path.lt(value) : like.and(path.lt(value));
			}
			return this;
		}

		public BooleanExpression build() {
			return like;
		}
	}

	private JPQLQuery<T> query;
	private PredicateBuilder predicateBuilder;

	private QueryBuilder(EntityManager entityManager, EntityPathBase base) {
		predicateBuilder = predicateBuilder();
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
		predicateBuilder.like(path, value);
		return this;
	}

	public QueryBuilder<T> eq(StringPath path, String value) {
		predicateBuilder.eq(path, value);
		return this;
	}

	public QueryBuilder<T> eq(BooleanPath path, Boolean value) {
		predicateBuilder.eq(path, value);
		return this;
	}

	public QueryBuilder<T> gt(NumberPath<Long> path, Long value) {
		predicateBuilder.gt(path, value);
		return this;
	}

	public QueryBuilder<T> lt(NumberPath<Long> path, Long value) {
		predicateBuilder.lt(path, value);
		return this;
	}

	public QueryBuilder<T> joinOn(EntityPathBase joiningField, EntityPathBase entityType, BooleanExpression booleanExpression) {
		// better way than simple if? RLY?
		Optional.ofNullable(booleanExpression).ifPresent(e -> {
			query.innerJoin(joiningField, entityType);
			query.on(e);
		});

		//		if (booleanExpression != null) {
		//			query.innerJoin(entityPathBase);
		//			query.on(booleanExpression);
		//		}
		return this;
	}

	public JPQLQuery<T> build() {
		BooleanExpression like = predicateBuilder.build();
		return (like == null) ? query : query.where(like);
	}

}
