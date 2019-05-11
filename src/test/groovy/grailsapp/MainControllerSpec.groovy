package grailsapp

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class MainControllerSpec extends Specification implements ControllerUnitTest<MainController> {

    def setup() {
    }

    def cleanup() {
    }

    void 'test the index action'() {
        when:
            controller.index()
        then:
            model.size() == 0
            model.isEmpty()
    }
}
