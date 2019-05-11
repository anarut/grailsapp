package grailsapp

import com.github.javafaker.Faker
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

import java.util.concurrent.TimeUnit

@Integration(applicationClass = Application.class)
@Rollback
class PersonServiceSpec extends Specification {

    PersonService personService
    SessionFactory sessionFactory

    private Closure personInit = { p ->
        Faker faker = new Faker()
        p.firstName = faker.name().firstName()
        p.lastName = faker.name().lastName()
        p.email = faker.internet().emailAddress()
        p.birthday = faker.date().past(365*70, TimeUnit.DAYS)
        p
    }

    private Long setupData() {

        (1..5).each {
            Person person = new Person().with(personInit)
            person.save(flush: true, failOnError: true)
        }

        Person.list()[0].id
    }



    void "test get"() {
        setupData()

        expect:
        personService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Person> personList = personService.list(max: 2, offset: 2)

        then:
        personList.size() == 2

        //ids 6 7 8 9 10
        personList[0].id == 8
        personList[1].id == 9
    }

    void "test count"() {
        setupData()

        expect:
        personService.count() == 5
    }

    void "test delete"() {
        Long personId = setupData()

        expect:
        personService.count() == 5

        when:
        personService.delete(personId)
        sessionFactory.currentSession.flush()

        then:
        personService.count() == 4
    }

    void "test save"() {
        when:
        Person person = new Person().with(personInit)
        personService.save(person)

        then:
        person.id != null
    }
}
