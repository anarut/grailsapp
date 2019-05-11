package grailsapp.services

import grailsapp.Person
import grailsapp.PersonService

class PersonServiceImpl implements PersonService {

    @Override
    Person get(Serializable id) {
        Person.get(id)
    }

    @Override
    List<Person> list(Map args) {
        Person.list(args)
    }

    @Override
    Long count() {
        Person.count()
    }

    @Override
    void delete(Serializable id) {
        Person.get(id).delete()
    }

    @Override
    Person save(Person person) {
        person.save()
    }
}
