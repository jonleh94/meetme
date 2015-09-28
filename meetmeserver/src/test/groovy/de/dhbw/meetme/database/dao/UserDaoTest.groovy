package de.dhbw.meetme.database.dao

import de.dhbw.meetme.domain.User
import de.dhbw.meetme.domain.UuidId
import spock.lang.Specification

/**
 *
 */
class UserDaoTest extends Specification {

    @Delegate DbTestUtil dbTestUtil = new DbTestUtil() // a special form of inheritance

    /**
     * Helper method
     */
    private User createUser(UserDao dao, String usernname) {
        User u = new User();
        u.username = usernname
        dao.persist(u);
        u
    }

    def testPersistence() {
        setup:
        UserDao dao = new UserDao()

        when:
        dbTestUtil.em.getTransaction().begin(); // need transaction when we change the db
        dao.entityManager = dbTestUtil.em
        User u = createUser(dao, "YOP")
        dbTestUtil.em.getTransaction().commit();

        then:
        dbTestUtil.em.contains(u)  // this validation step will not work with the classic dao. Instead you have to validate with the read operation.
    }

    def testRead() {
        setup:
        UserDao dao = new UserDao()
        dbTestUtil.em.getTransaction().begin(); // need transaction when we change the db
        dao.entityManager = dbTestUtil.em
        User u = createUser(dao, "YOP")
        UuidId id = u.id
        dbTestUtil.em.getTransaction().commit();
        dbTestUtil.em.close()
        dbTestUtil.createEntityManager() // open clean session to avoid side effects
        dao.entityManager = dbTestUtil.em // make sure we use the new one...

        when:
        User foundUser = dao.get(id)

        then:
        foundUser
        foundUser.id == id
        foundUser.username == "YOP"
    }

    def testFindByName() {
        setup:
        UserDao dao = new UserDao()

        dbTestUtil.em.getTransaction().begin(); // need transaction when we change the db
        dao.entityManager = dbTestUtil.em
        createUser(dao, "u1")
        User expectedUser = createUser(dao, "u2")
        createUser(dao, "u3")
        dbTestUtil.em.getTransaction().commit();
        dbTestUtil.em.close()
        dbTestUtil.createEntityManager() // open clean session to avoid side effects
        dao.entityManager = dbTestUtil.em // make sure we use the new one...

        when:
        Collection<User> foundUser = dao.findByName("u2")

        then:
        foundUser
        foundUser.size() == 1
        foundUser[0].id == expectedUser.id
    }

    def testDelete() {
        setup:
        UserDao dao = new UserDao()

        // create user
        dbTestUtil.em.getTransaction().begin();
        dao.entityManager = dbTestUtil.em
        User u = createUser(dao, "YOP")
        UuidId id = u.id
        dbTestUtil.em.getTransaction().commit();
        dbTestUtil.em.close()

        // validate it's there
        dbTestUtil.createEntityManager() // open clean session to avoid side effects
        dao.entityManager = dbTestUtil.em // make sure we use the new one...
        User foundUser = dao.get(id)
        dbTestUtil.em.close()

        // delete it
        dbTestUtil.createEntityManager() // open clean session to avoid side effects
        dbTestUtil.em.getTransaction().begin(); // need transaction when we change the db
        dao.entityManager = dbTestUtil.em // make sure we use the new one...
        dao.delete(foundUser.id)
        dbTestUtil.em.getTransaction().commit();
        dbTestUtil.em.close()

        when:
        dbTestUtil.createEntityManager() // open clean session to avoid side effects
        dao.entityManager = dbTestUtil.em // make sure we use the new one...
        User foundUser2 = dao.get(id)

        then:
        !foundUser2
    }
}
