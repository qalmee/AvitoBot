package ru.test.avito.dao;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import ru.test.avito.domain.Advert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class AdvertDao {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public AdvertDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void initializeHibernateSearch() {
        entityManager = entityManagerFactory.createEntityManager();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getAdvert() {
        QueryBuilder queryBuilder = getQueryBuilder();
        Query query = queryBuilder
                .keyword()
                .onField("text")
                .matching("one")
                .createQuery();

        List<Advert> adverts = getJpaQuery(query).getResultList();
        System.out.println(adverts.size());
        for (int i = 0; i < Math.min(adverts.size(), 3); i++) {
            System.out.println(adverts.get(i).getText());
        }

    }

    public void saveSome() {

    }

    public List<Advert> searchAdverts(String searchQuery) {
        QueryBuilder queryBuilder = getQueryBuilder();
        Query query = queryBuilder
                .phrase()
                .withSlop(1000)
                .onField("text")
                .sentence(searchQuery)
                .createQuery();

        return getJpaQuery(query).getResultList();
    }

    public List<Advert> searchAdverts1(String searchQuery) {
        QueryBuilder queryBuilder = getQueryBuilder();
        Query query = queryBuilder
                .simpleQueryString()
                .onField("text")
                .matching("\"" + searchQuery + "\"~1000")
                .createQuery();
        return getJpaQuery(query).getResultList();
    }

    private FullTextQuery getJpaQuery(Query query) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(query, Advert.class);
    }

    private QueryBuilder getQueryBuilder() {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Advert.class)
                .get();
    }

}
